package com.example.demo.services;

import com.example.demo.entities.general.Job;
import com.example.demo.repos.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class JobsService {

    @Autowired
    private JobsRepository jobsRepository;

    public List<Job> list() {
        return jobsRepository.findAll();
    }

    public Job getByUrl(String url){
        return jobsRepository.getByUrl(url);
    }

    public Job doesSuccessfulJobExists(String url){
        return  jobsRepository.doesSuccessfulJobExists(url);
    }

    public Job doesFailedJobExists(String url){
        return  jobsRepository.doesFailedJobExists(url);
    }


    public void saveJob(Job job){
        jobsRepository.save(job);
    }

    public void removeJob(long jobId){
        jobsRepository.deleteById(jobId);
    }

    public List<Job> getJobsForTimeBefore(Timestamp timestamp){
        return jobsRepository.getJobsForTimeBefore(timestamp);
    }

    public List<Job> getJobsBetween(Timestamp timestampStart,Timestamp timestampEnd){
        return jobsRepository.getJobsBetween(timestampStart,timestampEnd);
    }
}
