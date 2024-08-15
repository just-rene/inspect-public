package com.example.demo.nlp;

import com.example.demo.entities.nlp.EmotionMultilabel;
import com.example.demo.entities.general.Scrap;
import com.example.demo.entities.nlp.NamedEntityRecognition;
import com.example.demo.entities.nlp.Sentiment;
import com.example.demo.services.JobsService;
import com.example.demo.services.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SentimentScheduler {

        @Value("${huggingface.bearer_token}")
        private String bearerToken;

        private final boolean emoOn = true;

        @Autowired
        private EmotionMultilabelAnalysis emotionMultilabelAnalysis;

        @Autowired
        private SentimentAnalysis sentimentAnalysis;

        @Autowired
        private NamedEntityRecognitionAnalysis namedEntityRecognitionAnalysis;


        @Autowired
        private JobsService jobsService;

        @Autowired
        private ScrapService scrapService;

        @Scheduled(fixedRate = 60_000)
        private void execute(){

                //EMOTION MULTILABEL
                List<Scrap> notAnalysedScrapsEmotionMultilabel =  scrapService.getAllScrapsWithoutEmotionMultilabelAnalysis();

                for(Scrap scrap : notAnalysedScrapsEmotionMultilabel){
                        String jsonResult = emotionMultilabelAnalysis.getEmotionAnalysis(scrap.getHeadline());
                        EmotionMultilabel emotionMultilabel = new EmotionMultilabel();
                        emotionMultilabel.setResult(jsonResult);
                        scrap.setEmotionMultilabel(emotionMultilabel);
                        scrapService.save(scrap);
                }

                //SENTIMENT
                List<Scrap> notAnalysedScrapsSentiment =  scrapService.getAllScrapsWithoutSentimentAnalysis();

                for(Scrap scrap : notAnalysedScrapsSentiment){
                        String jsonResult = sentimentAnalysis.getAnalysis(scrap.getHeadline());
                        Sentiment sentiment = new Sentiment();
                        sentiment.setResult(jsonResult);
                        scrap.setSentiment(sentiment);
                        scrapService.save(scrap);
                }

                List<Scrap> notAnalysedScrapsNer =  scrapService.getAllScrapsWithoutNamedEntityRecognition();

                for(Scrap scrap : notAnalysedScrapsNer){
                        String jsonResult = namedEntityRecognitionAnalysis.getAnalysis(scrap.getContent());
                        NamedEntityRecognition ner = new NamedEntityRecognition();
                        ner.setResult(jsonResult);
                        scrap.setNamedEntityRecognition(ner);
                        scrapService.save(scrap);
                }
        }
}
