package com.example.demo.services;

import com.example.demo.entities.general.Scrap;
import com.example.demo.repos.ScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapService  {

    @Autowired
    private ScrapRepository scrapRepository;

    public List<Scrap> findAllById(List<Long> ids){
        return scrapRepository.findAllById(ids);
    }

    public void save(Scrap scrap){
        scrapRepository.save(scrap);
    }

    public List<Scrap> getAllScrapsWithoutEmotionMultilabelAnalysis(){
        return scrapRepository.getAllWithoutEmotionMultilabelAnalysis();
    }

    public List<Scrap> getAllScrapsWithEmotionMultilabelAnalysis(){
        return scrapRepository.getAllWithEmotionMultilabelAnalysis();
    }

    public List<Scrap> getAllScrapsWithoutSentimentAnalysis(){
        return scrapRepository.getAllWithoutSentimentAnalysis();
    }

    public List<Scrap> getAllScrapsWithSentimentAnalysis(){
        return scrapRepository.getAllWithSentimentAnalysis();
    }

    public List<Scrap> getAllScrapsWithoutNamedEntityRecognition(){
        return scrapRepository.getAllWithoutNamedEntityRecognition();
    }
    public List<Scrap> getAllScrapsWithNamedEntityRecognition(){
        return scrapRepository.getAllWithNamedEntityRecognition();
    }


}
