package com.example.demo.controller;

import com.example.demo.scrapper.IScrapper;
import com.example.demo.services.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String serveMain(Model model){
        return "index";
    }
}
