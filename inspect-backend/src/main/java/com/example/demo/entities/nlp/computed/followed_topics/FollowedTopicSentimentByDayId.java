package com.example.demo.entities.nlp.computed.followed_topics;

import com.example.demo.entities.general.FollowedTopic;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowedTopicSentimentByDayId implements Serializable {
    public Date date;
    public FollowedTopic topic;
}
