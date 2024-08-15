package com.example.demo.rest;


import com.example.demo.entities.nlp.NamedEntityRecognition;
import com.example.demo.entities.nlp.computed.NamedEntityRecognitionDayAccumulated;
import com.example.demo.services.NamedEntityRecognitionAccumulatedService;
import com.example.demo.services.NamedEntityRecognitionService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import static com.example.demo.NamedEntityRecognitionType.*;

@RestController
public class NamedEntityRecognitionRest {

    @Autowired
    private NamedEntityRecognitionService namedEntityRecognitionService;

    @Autowired
    private NamedEntityRecognitionAccumulatedService namedEntityRecognitionAccumulatedService;


    @GetMapping("/api/ner/all")
    public List<NamedEntityRecognition> getAll(){
        return namedEntityRecognitionService.findAll();
    }


    @GetMapping("/api/ner-accumulated/{type}/day/{date}")
    public String getLocationForDay(@PathVariable @NotNull String type, @PathVariable @NotNull Date date){

        String result;
        switch (type){
            case LOC -> result = namedEntityRecognitionAccumulatedService.findTypeForDay(LOC,date);
            case MISC -> result = namedEntityRecognitionAccumulatedService.findTypeForDay(MISC,date);
            case PER -> result = namedEntityRecognitionAccumulatedService.findTypeForDay(PER,date);
            case ORG -> result = namedEntityRecognitionAccumulatedService.findTypeForDay(ORG,date);
            default -> throw new IllegalArgumentException("unknown NER type");
        }

        return result;
    }



}
