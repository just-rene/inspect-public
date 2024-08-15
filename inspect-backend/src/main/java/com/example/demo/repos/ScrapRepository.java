package com.example.demo.repos;

import com.example.demo.entities.general.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Long> {

    @Query("SELECT s FROM Scrap s WHERE s.emotionMultilabel is NULL")
    List<Scrap> getAllWithoutEmotionMultilabelAnalysis();

    @Query("SELECT s FROM Scrap s WHERE s.emotionMultilabel is not NULL")
    List<Scrap> getAllWithEmotionMultilabelAnalysis();

    @Query("SELECT s FROM Scrap s WHERE s.sentiment is NULL")
    List<Scrap> getAllWithoutSentimentAnalysis();

    @Query("SELECT s FROM Scrap s WHERE s.sentiment is not NULL")
    List<Scrap> getAllWithSentimentAnalysis();

    @Query("SELECT s FROM Scrap s WHERE s.namedEntityRecognition is NULL")
    List<Scrap> getAllWithoutNamedEntityRecognition();

    @Query("SELECT s FROM Scrap s WHERE s.namedEntityRecognition is not NULL")
    List<Scrap> getAllWithNamedEntityRecognition();

}
