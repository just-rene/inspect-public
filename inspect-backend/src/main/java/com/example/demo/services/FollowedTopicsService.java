package com.example.demo.services;

import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.repos.FollowedTopicsRepository;
import com.example.demo.repos.TopicRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowedTopicsService extends RuntimeException   {

    @Autowired
    private FollowedTopicsRepository followedTopicsRepository;

    @Autowired
    private TopicRepository topicRepository;

    public FollowedTopic create(FollowedTopic followedTopic){

        if(followedTopic.getTopics().isEmpty() || followedTopic.getName().isBlank()){
            throw new IllegalArgumentException("At least 1 Topic is needed! Name must not be blank");
        }
        return followedTopicsRepository.save(followedTopic);
    }


    public List<FollowedTopic> findAll(){
        return followedTopicsRepository.findAll();
    }

    //special area be careful (sql injection risk), only for internal usage
    @PersistenceContext
    private EntityManager entityManager;


    //TODO: search for solution -> store JSON in MongoDB...
    //i know this is evil, (sql injection possible) but it is necessary :/
    public List<Long> findTopicInScrapsAndGetIds(String topicName){
        var x = entityManager.createNativeQuery("select scrap_id from " +
                "(select scrap.id as scrap_id,  scrap.headline, JSON_CONTAINS(JSON_EXTRACT(result, '$[*].word'),'\"" + topicName +"\"', '$') as json_data " +
                "from scrap left join named_entity_recognition on scrap.named_entity_recognition_id = named_entity_recognition.id) as overview_table" +
                " where json_data = 1;", List.class);
            List<Long> resultList = new ArrayList<>();
            for (var res :  x.getResultList()){
                var aaa = (List<Long>)res;
                resultList.add(aaa.get(0));
            }
            return resultList;
    }
}
