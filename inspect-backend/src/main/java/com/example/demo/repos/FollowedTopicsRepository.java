package com.example.demo.repos;

import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.entities.general.Job;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryRewriter;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface FollowedTopicsRepository extends JpaRepository<FollowedTopic, Long> {

    @Query(nativeQuery = true, value = "select * from (select scrap.id,  scrap.headline,  JSON_CONTAINS(JSON_EXTRACT(result, '$[*].word'),'\"?1\"', '$') as json_data from scrap left join named_entity_recognition on scrap.named_entity_recognition_id = named_entity_recognition.id) as overview_table where json_data = 1")
    public List<String> getScrapsByTopic(String topicName);

    @Query(nativeQuery = true, value = "select * from scrap where scrap.headline = ?1")
    public List<String> asdf(String topicName);

}

