package com.example.demo.data_processing.followed_topics;

import com.example.demo.data_processing.IAccumulator;
import org.springframework.scheduling.annotation.Scheduled;

public class MentionsByDay  implements IAccumulator {

    @Scheduled(fixedRate = 24 * 3_600_000)
    @Override
    public void execute() {
        //TODO
    }
}
