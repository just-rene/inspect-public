package com.example.demo.entities.nlp.computed.followed_topics;

import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.entities.general.Topic;
import com.example.demo.entities.general.TopicId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FollowedTopicSentimentByDayId.class)
public class FollowedTopicSentimentByDay {

    @Id
    public Date date;

    @Id
    @JsonIgnore
    @ManyToOne(targetEntity = FollowedTopic.class, fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    public FollowedTopic topic;

    public float positive;

    public float negative;

    public float neutral;

}
