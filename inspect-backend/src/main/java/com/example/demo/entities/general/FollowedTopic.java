package com.example.demo.entities.general;

import com.example.demo.entities.nlp.computed.followed_topics.FollowedTopicSentimentByDay;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FollowedTopic {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    private String name;


    @Getter
    @Setter
    @ManyToMany(targetEntity = Topic.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Topic> topics = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(targetEntity = FollowedTopicSentimentByDay.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FollowedTopicSentimentByDay> followedTopicSentimentByDay;

    public void addFollowedTopicSentimentByDay(FollowedTopicSentimentByDay followedTopicSentimentByDay ){
        if (this.followedTopicSentimentByDay.stream().noneMatch(x -> x.getDate().toString().equals(followedTopicSentimentByDay.getDate().toString())) ){
            this.followedTopicSentimentByDay.add(followedTopicSentimentByDay);
        }
    }

    public void addTopic(Topic topic){
        this.topics.add(topic);
    }
}
