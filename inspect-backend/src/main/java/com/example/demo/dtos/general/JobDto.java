package com.example.demo.dtos.general;

import com.example.demo.entities.general.Report;
import com.example.demo.entities.general.Scrap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
        public String url;
        public String name;
        public Timestamp articleTimestamp;
        public List<Report> reports;
        public Scrap scrap;
}
