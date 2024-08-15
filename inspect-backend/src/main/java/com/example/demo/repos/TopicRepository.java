package com.example.demo.repos;

import com.example.demo.entities.general.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {


    @Query("SELECT t FROM Topic t WHERE t.name LIKE %:name%")
    List<Topic> searchByNameLike(@Param("name") String title);

}
