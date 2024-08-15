package com.example.demo.data_processing.followed_topics;

import com.example.demo.data_processing.IAccumulator;
import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.entities.general.Job;
import com.example.demo.entities.nlp.computed.followed_topics.FollowedTopicSentimentByDay;
import com.example.demo.repos.FollowedTopicSentimentByDayRepository;
import com.example.demo.repos.JobsRepository;
import com.example.demo.services.FollowedTopicsService;
import com.example.demo.services.ScrapService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.CollectionFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FollowedTopicSentimentByDayAccumulator implements IAccumulator {


    @Autowired
    private FollowedTopicsService followedTopicsService;

    @Autowired
    private FollowedTopicSentimentByDayRepository followedTopicSentimentByDayRepository;

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private JobsRepository jobsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(fixedRate = 24 * 3_600_000)
    @Override
    public void execute() {
        ObjectMapper objectMapper = new ObjectMapper();


        List<FollowedTopic> followedTopicList = followedTopicsService.findAll();
        for (var followedTopic : followedTopicList){

            //find all jobs with specified topics
            Set<Long> scrapIds = new HashSet<>();
            for(var topic : followedTopic.topics ){

                String nerType = topic.getNamedEntityRecognitionType();
                String name = topic.getName();

                List<Long> ids = followedTopicsService.findTopicInScrapsAndGetIds(name);
                scrapIds.addAll(ids);
            }

            var allJobsContainingSpecifiedTopic = jobsRepository.getByScrapIds(scrapIds.stream().toList());

            //i love this function :)
            Map<String,List<Job>> jobsGroupedByDate = allJobsContainingSpecifiedTopic
                    .stream().collect(Collectors.groupingBy(w -> new Date(w.getArticleTimestamp().getTime()).toString()));


            int count = 0;
            String lastKey = "";

            for (var key : jobsGroupedByDate.keySet()){
                FollowedTopicSentimentByDay followedTopicSentimentByDay = new FollowedTopicSentimentByDay();

                count = 0;

                for(var jobByDate : jobsGroupedByDate.get(key)){
                    count++;

                    if (!key.equals(lastKey)){
                        lastKey = key;
                        followedTopicSentimentByDay.setDate(new Date(jobByDate.getArticleTimestamp().getTime()));
                        followedTopicSentimentByDay.setTopic(followedTopic);
                    }

                    try {
                        for (var o : objectMapper.readTree(jobByDate.getScrap().getSentiment().getResult()).get(0)){
                            if (o.get("label").asText().equals("positive")){
                                followedTopicSentimentByDay.positive = followedTopicSentimentByDay.positive + o.get("score").floatValue();
                            } else if (o.get("label").asText().equals("negative")){
                                followedTopicSentimentByDay.negative = followedTopicSentimentByDay.negative + o.get("score").floatValue();
                            }else if (o.get("label").asText().equals("neutral")){
                                followedTopicSentimentByDay.neutral = followedTopicSentimentByDay.neutral + o.get("score").floatValue();
                            }else{
                                System.err.println("unk type");
                            }
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }

                followedTopicSentimentByDay.positive = followedTopicSentimentByDay.positive / (float)count;
                followedTopicSentimentByDay.negative = followedTopicSentimentByDay.negative/ (float)count;
                followedTopicSentimentByDay.neutral = followedTopicSentimentByDay.neutral / (float)count;

                followedTopic.addFollowedTopicSentimentByDay(followedTopicSentimentByDay) ;

            }

            followedTopicsService.create(followedTopic);

        }

    }
}
