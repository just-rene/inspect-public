package com.example.demo.entities.general;

import com.example.demo.entities.nlp.EmotionMultilabel;
import com.example.demo.entities.nlp.NamedEntityRecognition;
import com.example.demo.entities.nlp.Sentiment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

@Entity
public class Scrap {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    public String headline;


    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    public String content;

    @Getter
    @Setter
    @OneToOne(targetEntity = EmotionMultilabel.class,cascade = CascadeType.ALL,optional = true)
    public EmotionMultilabel emotionMultilabel;

    @Getter
    @Setter
    @OneToOne(targetEntity = Sentiment.class,cascade = CascadeType.ALL,optional = true)
    public Sentiment sentiment;

    @Getter
    @Setter
    @OneToOne(targetEntity = NamedEntityRecognition.class,cascade = CascadeType.ALL,optional = true)
    public NamedEntityRecognition namedEntityRecognition;

}
