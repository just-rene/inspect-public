package com.example.demo.entities.general;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Getter
    @Setter
    public Timestamp timestamp;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Status.class, fetch = FetchType.EAGER)
    public Status status;




}
