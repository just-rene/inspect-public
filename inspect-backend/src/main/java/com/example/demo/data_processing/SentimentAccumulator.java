package com.example.demo.data_processing;

import com.example.demo.entities.general.Job;
import com.example.demo.entities.nlp.computed.SentimentDayAccumulated;
import com.example.demo.services.JobsService;
import com.example.demo.services.SentimentDayAccumulatedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SentimentAccumulator implements IAccumulator{


    //if true -> already computed days will be recomputed
    private final boolean recomputeDays = false;

    @Autowired
    private JobsService jobsService;

    @Autowired
    private SentimentDayAccumulatedService sentimentDayAccumulatedService;

    @Scheduled(fixedRate = 24 * 3_600_000)
    public void execute() {
        //TODO: implement for every day
        LocalDateTime end = LocalDate.now().atTime(0,0).plusDays(1); //today
        LocalDateTime start = end.minusDays(1); // yesterday
        Timestamp t_start = Timestamp.valueOf(start);
        Timestamp t_end = Timestamp.valueOf(end);
        List<Job> jobsByDay = jobsService.getJobsBetween(t_start, t_end );


        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SentimentDayAccumulated sentimentDayAccumulated = new SentimentDayAccumulated();

        for (Job job : jobsByDay){

            String sentimentResult = job.getScrap().getSentiment().getResult();

            for (int i = 0; i < 3; i++ ){

                try {
                    String label = mapper.readTree(sentimentResult).get(0).get(i).get("label").asText();
                    float score  = mapper.readTree(sentimentResult).get(0).get(i).get("score").floatValue();

                    switch (label) {
                        case "positive" -> sentimentDayAccumulated.positive += score;
                        case "negative" -> sentimentDayAccumulated.negative += score;
                        case "neutral" -> sentimentDayAccumulated.neutral += score;
                        default -> System.err.println("SentimentAccumulator: error unknown label");
                    }
                } catch (JsonProcessingException e) {
                    System.err.println("SentimentAccumulator: couldn't convert JSON to Java Object");
                }
            }
        }

        sentimentDayAccumulated.positive = sentimentDayAccumulated.positive/jobsByDay.size();
        sentimentDayAccumulated.negative = sentimentDayAccumulated.negative/jobsByDay.size();
        sentimentDayAccumulated.neutral = sentimentDayAccumulated.neutral/jobsByDay.size();

        sentimentDayAccumulated.setDate(Date.valueOf(start.toLocalDate()));


        sentimentDayAccumulatedService.save(sentimentDayAccumulated);

        System.out.println("sentiment day saved");

    }




}
