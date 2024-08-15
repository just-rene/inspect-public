package com.example.demo.entities.general;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @Getter
    @Setter
    public String url;

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public Timestamp articleTimestamp;

    @ManyToMany(targetEntity = Report.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    public List<Report> reports = new ArrayList<>();

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    public  Scrap scrap;

    public void addReport(Report report){
        this.reports.add(report);
    }



}
