package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class CalculatorServiceTest {

    HistoryService historyService = mock(HistoryService.class);
    CalculatorService calculatorService = new CalculatorService(historyService);

    @Test
    void testAdd() {
        assertEquals(7, calculatorService.add(3, 4));
        assertEquals(-1, calculatorService.add(3, -4));
    }

    @Test
    void testSubtract() {
        assertEquals(5, calculatorService.subtract(10, 5));
        assertEquals(-1, calculatorService.subtract(4, 5));
    }

    @Test
    void testMultiply() {
        assertEquals(20, calculatorService.multiply(4, 5));
        assertEquals(-12, calculatorService.multiply(3, -4));
    }

    @Test
    void testDivide() {
        assertEquals(2.0, calculatorService.divide(10, 5));
        assertEquals(2.5, calculatorService.divide(5, 2));

        // test division by zero
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.divide(10, 0);
        });
        assertEquals("Cannot divide by zero", exception.getMessage());
    }
}
