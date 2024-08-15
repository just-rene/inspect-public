package com.example.demo.data_processing;

import com.example.demo.NamedEntityRecognitionType;
import com.example.demo.entities.general.Topic;
import com.example.demo.entities.nlp.computed.NamedEntityRecognitionDayAccumulated;
import com.example.demo.repos.NamedEntityRecognitionDayAccumulatedRepository;
import com.example.demo.repos.TopicRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TopicCollector implements IAccumulator{

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private NamedEntityRecognitionDayAccumulatedRepository namedEntityRecognitionDayAccumulatedRepository;


    @Override
    @Scheduled(fixedRate = 24 * 3_600_000)
    public void execute() {


        Set<Topic> allTopics  = new HashSet<>();

        List<NamedEntityRecognitionDayAccumulated> allNerDayAccList = namedEntityRecognitionDayAccumulatedRepository.findAll();

        ObjectMapper mapper = new ObjectMapper();
        try {

            //var locations = mapper.readTree(allNerDayAccList.get(0).getLoc());
            for (var nerDayAcc : allNerDayAccList){

                //LOCATIONS
                var locations = mapper.readTree(nerDayAcc.getLoc()).fieldNames();
                locations.forEachRemaining(s -> {allTopics.add(new Topic(s,NamedEntityRecognitionType.LOC));});

                //MISC
                var misc = mapper.readTree(nerDayAcc.getMisc()).fieldNames();
                misc.forEachRemaining(s -> {allTopics.add(new Topic(s,NamedEntityRecognitionType.MISC));});

                //ORGANISATION
                var org = mapper.readTree(nerDayAcc.getOrg()).fieldNames();
                org.forEachRemaining(s -> {allTopics.add(new Topic(s,NamedEntityRecognitionType.ORG));});

                //PERSON
                var per = mapper.readTree(nerDayAcc.getPer()).fieldNames();
                per.forEachRemaining(s -> {allTopics.add(new Topic(s,NamedEntityRecognitionType.PER));});
            }

            //saveAll quits completely process when error
            int i = 0;
            for(Topic t : allTopics){
                try{
                    topicRepository.save(t);
                }catch (Exception ignored){}
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
