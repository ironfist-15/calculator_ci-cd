package com.example.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {


    @Autowired
    private HistoryService historyService;

    public CalculatorService(HistoryService historyService) {
        this.historyService = historyService;
    }

    public int add(int a, int b) {
        int result = a + b;
        historyService.addRecord(a + " + " + b + " = " + result);
        return result;
    }

    public int subtract(int a, int b) {
        int result = a - b;
        historyService.addRecord(a + " - " + b + " = " + result);
        return result;
    }

    public int multiply(int a, int b) {
        int result = a * b;
        historyService.addRecord(a + " * " + b + " = " + result);
        return result;
    }

    public double divide(int a, int b) {
        if(0 == b) throw new IllegalArgumentException("Cannot divide by zero");
        double result = (double)a / b;
        historyService.addRecord(a + " / " + b + " = " + result);
        return result;
    }

    public void clearHistory() {
        historyService.clearHistory();
    }
}
