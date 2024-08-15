package com.example.demo.scrapper;

import com.example.demo.entities.general.Job;
import com.example.demo.entities.general.Status;
import com.example.demo.repos.StatusRepository;
import com.example.demo.services.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@EnableScheduling
@Component
public class ScrapperScheduler {

    private static final boolean SCRAP = true;
    private static boolean INIT = true;

    @Autowired
    private IScrapper yourScrapper1;

    @Autowired
    private IScrapper yourScrapper2;

    @Autowired
    private JobsService jobsService;

    @Autowired
    private StatusRepository statusRepository;

    //pro 1h
    @Scheduled(fixedRate = 3_600_000)
    private void execute() {
        System.out.println("execute");

        //init check remove later
        if(INIT){
            statusRepository.save(new Status(1, "success"));
            statusRepository.save(new Status(2, "fail"));
            INIT =false;
        }

       if (SCRAP){
           yourScrapper1.execute();
           yourScrapper1.execute();
       }


    }
}
