package com.example.demo.repos;

import com.example.demo.entities.general.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}