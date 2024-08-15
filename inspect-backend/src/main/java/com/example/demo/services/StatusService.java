package com.example.demo.services;

import com.example.demo.entities.general.Status;
import com.example.demo.repos.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public void saveStatus(Status status) {
        statusRepository.save(status);
    }
}
