package com.example.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CalculatorHomeController {


    @GetMapping("/")
    public String home(){
        return "calculator";
    }
}
