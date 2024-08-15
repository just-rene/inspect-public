package com.example.demo.data_processing;

import com.example.demo.entities.general.Job;
import com.example.demo.entities.nlp.computed.NamedEntityRecognitionDayAccumulated;
import com.example.demo.repos.NamedEntityRecognitionDayAccumulatedRepository;
import com.example.demo.services.JobsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * desc algo
 * find occurrence of loc,per,misc,org in a article (once counted)
 * accumulate all occurrences over all article
 * save to db
 */

@Component
public class NamedEntityRecognitionAccumulator implements IAccumulator {

    @Autowired
    private JobsService jobsService;

    @Autowired
    private NamedEntityRecognitionDayAccumulatedRepository nerAccRepository;

    @Scheduled(fixedRate = 24 * 3_600_000)
    public void execute() {
        LocalDateTime end = LocalDate.now().atTime(0, 0).plusDays(1); //change back to 1
        LocalDateTime start = end.minusDays(1); // yesterday
        Timestamp t_start = Timestamp.valueOf(start);
        Timestamp t_end = Timestamp.valueOf(end);
        List<Job> jobsByDay = jobsService.getJobsBetween(t_start, t_end);

        ObjectMapper mapper = new ObjectMapper();

        //1) collect all loc,misc,...
        List<String> loc = new ArrayList<>();
        List<String> per = new ArrayList<>();
        List<String> misc = new ArrayList<>();
        List<String> org = new ArrayList<>();

        for (Job job : jobsByDay) {
            try {
                String nerResult = job.getScrap().getNamedEntityRecognition().getResult();

                Set<String> locArticle = new HashSet<>();
                Set<String> miscArticle = new HashSet<>();
                Set<String> perArticle = new HashSet<>();
                Set<String> orgArticle = new HashSet<>();

                for (var entry : mapper.readTree(nerResult)) {
                    var entityGroup = entry.get("entity_group").asText();

                    //collect word in set (word only once counted per article)
                    switch (entityGroup) {
                        case "LOC" -> locArticle.add(entry.get("word").asText());
                        case "MISC" -> miscArticle.add(entry.get("word").asText());
                        case "PER" -> perArticle.add(entry.get("word").asText());
                        case "ORG" -> orgArticle.add(entry.get("word").asText());
                        default -> System.err.println("unknown type! allowed types MISC, PER, ORG, LOC");
                    }
                }

                //collect article word occurrences
                loc.addAll(locArticle);
                misc.addAll(miscArticle);
                per.addAll(perArticle);
                org.addAll(orgArticle);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //count occurrences
        Map<String, Long> locCounts = loc.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> miscCounts = misc.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> perCounts = per.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> orgCounts = org.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        NamedEntityRecognitionDayAccumulated namedEntityRecognitionDayAccumulated = new NamedEntityRecognitionDayAccumulated();

        try {
            namedEntityRecognitionDayAccumulated.setLoc(mapper.writeValueAsString(locCounts));
            namedEntityRecognitionDayAccumulated.setMisc(mapper.writeValueAsString(miscCounts));
            namedEntityRecognitionDayAccumulated.setPer(mapper.writeValueAsString(perCounts));
            namedEntityRecognitionDayAccumulated.setOrg(mapper.writeValueAsString(orgCounts));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        namedEntityRecognitionDayAccumulated.setDate(Date.valueOf(start.toLocalDate()));

        nerAccRepository.save(namedEntityRecognitionDayAccumulated);
    }


}
