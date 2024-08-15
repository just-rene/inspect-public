package com.example.demo.services;

import com.example.demo.entities.nlp.computed.SentimentDayAccumulated;
import com.example.demo.repos.SentimentDayAccumulatedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SentimentDayAccumulatedService {

    @Autowired
    private SentimentDayAccumulatedRepository sentimentDayAccumulatedRepository;

    public void save(SentimentDayAccumulated s){
        sentimentDayAccumulatedRepository.save(s);
    }

    public SentimentDayAccumulated getForDate(Date date){
       return  sentimentDayAccumulatedRepository.getByDate(date);
    }

    public List<SentimentDayAccumulated> getAll(int limit){
        return sentimentDayAccumulatedRepository.findAll(Pageable.ofSize(limit)).toList();
    }

}
