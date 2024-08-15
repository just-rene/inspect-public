package com.example.demo.entities.nlp.computed;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NamedEntityRecognitionDayAccumulated {

    @Id
    private Date date;

    @Column(columnDefinition = "TEXT")
    private String loc;

    @Column(columnDefinition = "TEXT")
    private String per;

    @Column(columnDefinition = "TEXT")
    private String misc;

    @Column(columnDefinition = "TEXT")
    private String org;

}
