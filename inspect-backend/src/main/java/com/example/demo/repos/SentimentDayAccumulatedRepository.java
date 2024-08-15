package com.example.demo.repos;

import com.example.demo.entities.nlp.computed.SentimentDayAccumulated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

//todo remove day
@Repository
public interface  SentimentDayAccumulatedRepository extends JpaRepository<SentimentDayAccumulated, Long> {

    @Query("SELECT s FROM SentimentDayAccumulated s WHERE date = ?1")
    public SentimentDayAccumulated getByDate(Date date);

}
