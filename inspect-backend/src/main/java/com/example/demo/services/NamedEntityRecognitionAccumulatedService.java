package com.example.demo.services;

import com.example.demo.entities.nlp.computed.NamedEntityRecognitionDayAccumulated;
import com.example.demo.repos.NamedEntityRecognitionDayAccumulatedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static com.example.demo.NamedEntityRecognitionType.*;
import static com.example.demo.NamedEntityRecognitionType.ORG;

@Service
public class NamedEntityRecognitionAccumulatedService {

    @Autowired
    private NamedEntityRecognitionDayAccumulatedRepository nerAccumulatedRepository;

    public String findTypeForDay(String type,Date date){

        String result;
        switch (type){
            case LOC -> result = nerAccumulatedRepository.findNamedEntityRecognitionDayAccumulatedForDay(date).getLoc();
            case MISC -> result= nerAccumulatedRepository.findNamedEntityRecognitionDayAccumulatedForDay(date).getMisc();
            case PER -> result = nerAccumulatedRepository.findNamedEntityRecognitionDayAccumulatedForDay(date).getPer();
            case ORG -> result = nerAccumulatedRepository.findNamedEntityRecognitionDayAccumulatedForDay(date).getOrg();
            default -> throw new IllegalArgumentException();
        }
        return result;
    }

    //find all
    public List<NamedEntityRecognitionDayAccumulated> findAll(){
        return nerAccumulatedRepository.findAll();
    }

    public List<String> findAllLocations(){
        return this.findAll().stream().map(NamedEntityRecognitionDayAccumulated::getLoc).toList();
    }

    public List<String> findAllMisc(){
        return this.findAll().stream().map(NamedEntityRecognitionDayAccumulated::getMisc).toList();
    }

    public List<String> findAllPersons(){
        return this.findAll().stream().map(NamedEntityRecognitionDayAccumulated::getPer).toList();
    }
    public List<String> findAllOrganisations(){
        return this.findAll().stream().map(NamedEntityRecognitionDayAccumulated::getOrg).toList();
    }



}
