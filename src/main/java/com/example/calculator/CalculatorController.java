package com.example.calculator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calc")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private HistoryService historyService;

    public CalculatorController(CalculatorService calculatorService, HistoryService historyService) {
        this.calculatorService = calculatorService;
        this.historyService = historyService;
    }



    @GetMapping("/add")
    public int add(@RequestParam int a, @RequestParam int b){
        return calculatorService.add(a, b);
    }

    @GetMapping("/subtract")
    public int subtract(@RequestParam int a, @RequestParam int b){
        return calculatorService.subtract(a, b);
    }

    @GetMapping("/multiply")
    public int multiply(@RequestParam int a, @RequestParam int b){
        return calculatorService.multiply(a, b);
    }

    @GetMapping("/divide")
    public double divide(@RequestParam int a, @RequestParam int b){
        return calculatorService.divide(a, b);
    }

    @GetMapping("/history")
    public List<String> getHistory() {
        return historyService.getHistory();
    }

    @DeleteMapping("/history")
    public void clearHistory() {
        calculatorService.clearHistory();
    }
}
