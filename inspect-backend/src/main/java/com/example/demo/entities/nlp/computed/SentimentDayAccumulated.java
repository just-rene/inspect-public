package com.example.demo.entities.nlp.computed;

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

public class SentimentDayAccumulated {

    @Id
    public Date date;

    public float positive;

    public float negative;

    public float neutral;

}
