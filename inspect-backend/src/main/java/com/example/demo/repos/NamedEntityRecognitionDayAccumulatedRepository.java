package com.example.demo.repos;


import com.example.demo.entities.nlp.computed.NamedEntityRecognitionDayAccumulated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface NamedEntityRecognitionDayAccumulatedRepository extends JpaRepository<NamedEntityRecognitionDayAccumulated,Long> {

    @Query(value = "SELECT n FROM NamedEntityRecognitionDayAccumulated n WHERE (n.date = ?1) ")
    NamedEntityRecognitionDayAccumulated findNamedEntityRecognitionDayAccumulatedForDay(Date date);

}
