package com.example.demo.repos;


import com.example.demo.entities.nlp.NamedEntityRecognition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NamedEntityRecognitionRepository extends JpaRepository<NamedEntityRecognition,Long> {
}
