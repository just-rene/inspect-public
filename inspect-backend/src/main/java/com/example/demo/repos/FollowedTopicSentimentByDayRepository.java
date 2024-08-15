package com.example.demo.repos;


import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.entities.nlp.computed.followed_topics.FollowedTopicSentimentByDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowedTopicSentimentByDayRepository extends JpaRepository<FollowedTopicSentimentByDay, Long> {
}
