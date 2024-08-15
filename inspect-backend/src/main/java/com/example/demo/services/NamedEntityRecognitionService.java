package com.example.demo.services;

import com.example.demo.entities.nlp.NamedEntityRecognition;
import com.example.demo.repos.NamedEntityRecognitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NamedEntityRecognitionService {

    @Autowired
    private NamedEntityRecognitionRepository namedEntityRecognitionRepository;

    public List<NamedEntityRecognition> findAll(){
        return namedEntityRecognitionRepository.findAll();
    }
}
