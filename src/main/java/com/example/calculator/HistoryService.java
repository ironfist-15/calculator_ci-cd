package com.example.calculator;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    private List<String> history = new ArrayList<>();

    public void addRecord(String record) {
        history.add(record);
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    public void clearHistory() {
        history.clear();
    }
}
