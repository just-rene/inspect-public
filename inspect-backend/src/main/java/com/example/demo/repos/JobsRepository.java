package com.example.demo.repos;

import com.example.demo.entities.general.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface JobsRepository extends JpaRepository<Job, Long> {

    @Query(value = "SELECT j FROM Job j left join j.reports r left join r.status s WHERE (j.url = ?1) AND (s.id = 1)")
    Job doesSuccessfulJobExists(String url);

    @Query(value = "SELECT j FROM Job j left join j.reports r left join r.status s WHERE (j.url = ?1) AND (s.id = 2)")
    Job doesFailedJobExists(String url);

    @Query(value = "SELECT j FROM Job j left join j.reports r left join r.status s WHERE (j.url = ?1)")
    Job getByUrl(String url);

    @Query(value = "SELECT j FROM Job j WHERE (j.articleTimestamp < ?1)")
    List<Job> getJobsForTimeBefore(Timestamp timestamp);

    @Query(value = "SELECT j FROM Job j WHERE (j.articleTimestamp > ?1) and  (j.articleTimestamp < ?2) order by j.articleTimestamp")
    List<Job> getJobsBetween(Timestamp timestampStart,Timestamp timestampEnd);

    @Query(value = "SELECT j FROM Job j WHERE j.scrap.id = ?1")
    Job getByScrapId(Long fid);

    @Query(value = "SELECT j FROM Job j WHERE j.scrap.id IN ?1")
    List<Job> getByScrapIds(List<Long> fids);


}
