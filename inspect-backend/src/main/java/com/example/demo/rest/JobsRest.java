package com.example.demo.rest;

import com.example.demo.entities.general.Job;
import com.example.demo.repos.FollowedTopicsRepository;
import com.example.demo.repos.JobsRepository;
import com.example.demo.services.JobsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class JobsRest {

    @Autowired
    private JobsService jobsService;


    @GetMapping("/api/jobs")
    public List<Job> getAllJobs(){
        return jobsService.list();
    }


    @GetMapping(value="/api/jobs/from/{timestampStart}/to/{timestampEnd}")
    public List<Job> getJobsFromTo(@PathVariable @NotNull Timestamp timestampStart, @PathVariable @NotNull Timestamp timestampEnd){
        return jobsService.getJobsBetween(timestampStart, timestampEnd);
    }


    @DeleteMapping(value="/api/job/{jobId}")
    public String removeJob(@PathVariable @NotNull long jobId){
        jobsService.removeJob(jobId);
        return "success";
    }


}
