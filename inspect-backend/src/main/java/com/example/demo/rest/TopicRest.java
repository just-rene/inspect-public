package com.example.demo.rest;

import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.entities.general.Topic;
import com.example.demo.entities.nlp.NamedEntityRecognition;
import com.example.demo.repos.FollowedTopicsRepository;
import com.example.demo.repos.TopicRepository;
import com.example.demo.services.FollowedTopicsService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class TopicRest {



    @Autowired
    private FollowedTopicsService followedTopicsService;

    @Autowired
    private FollowedTopicsRepository followedTopicsRepository;

    @Autowired
    private TopicRepository topicRepository;


    @GetMapping("/api/topic/like/{name}")
    public List<Topic> findByName(@PathVariable @NotNull String name){
        if(!name.isBlank() ){
            //todo: use service layer
            return topicRepository.searchByNameLike(name);
        }
        return Collections.emptyList();

    }



    @GetMapping("/api/followedTopic/addTopic/{followedTopicId}/{topicId}")
    public ResponseEntity<String> addTopicToFollowedTopic(@PathVariable @NotNull long followedTopicId,@PathVariable @NotNull long topicId){

        return new ResponseEntity<>("success", HttpStatus.OK);

    }


    @GetMapping("/api/followedTopic/getAll")
    public ResponseEntity<List<FollowedTopic>> getAllAllFollowedTopics(){
        return new ResponseEntity<>(followedTopicsService.findAll(), HttpStatus.OK);
    }


    @PostMapping("/api/followedTopic/create")
    public ResponseEntity<FollowedTopic> create(@RequestBody FollowedTopic followedTopic){

        FollowedTopic ft = followedTopicsService.create(followedTopic);
        return new ResponseEntity<>(ft, HttpStatus.OK);

    }

}
