package com.danhramtsov.java.essentials;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BmiTaskTest {
    private PrintStream originalOut;
    private InputStream originalIn;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        originalIn = System.in;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testBmiFormat() {
        String input = "70\n1.75\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        BmiTask.main(new String[]{});
        String output = outContent.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("Введите ваш вес (в кг):"), "Нет приглашения к вводу веса или оно не соответствует ожидаемому формату");
        assertTrue(output.contains("Введите ваш рост (в метрах):"), "Нет приглашения к вводу роста или оно не соответствует ожидаемому формату");
        assertTrue(output.contains("Ваш индекс массы тела (ИМТ):"), "Нет строки вывода с ИМТ или он не соответствует ожидаемому формату");

        double bmi = extractBmi(output);
        double expected = 70 / (1.75 * 1.75);
        assertEquals(expected, bmi, 0.01, "Неверный расчёт ИМТ");
    }

    @Test
    void testBmiCalculationForWholeNumbers() {
        simulateInput("70\n1.75\n");
        BmiTask.main(new String[]{});

        double bmi = extractBmi(outContent.toString(StandardCharsets.UTF_8));
        double expected = 70 / (1.75 * 1.75);
        assertEquals(expected, bmi, 0.01, "Неверный расчёт для целых чисел");
    }

    @Test
    void testBmiCalculationForDecimalValues() {
        simulateInput("68.5\n1.72\n");
        assertDoesNotThrow(() -> BmiTask.main(new String[]{}),
                "Программа не должна ломаться при вводе дробных значений");

        double bmi = extractBmi(outContent.toString(StandardCharsets.UTF_8));
        double expected = 68.5 / (1.72 * 1.72);
        assertEquals(expected, bmi, 0.01, "Неверный расчёт для дробных чисел");
    }

    @Test
    void testBmiPrecisionWithSmallAndLargeValues() {
        simulateInput("45.2\n1.60\n");
        BmiTask.main(new String[]{});
        double bmi1 = extractBmi(outContent.toString(StandardCharsets.UTF_8));
        double expected1 = 45.2 / (1.6 * 1.6);
        assertEquals(expected1, bmi1, 0.01, "Ошибка при маленьких значениях");

        outContent.reset();

        simulateInput("120.75\n2.05\n");
        BmiTask.main(new String[]{});
        double bmi2 = extractBmi(outContent.toString(StandardCharsets.UTF_8));
        double expected2 = 120.75 / (2.05 * 2.05);
        assertEquals(expected2, bmi2, 0.01, "Ошибка при больших значениях");
    }

    private void simulateInput(String input) {
        outContent.reset();
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    private double extractBmi(String output) {
        String numberPart = output.replaceAll("[^0-9.,]", "").replace(',', '.');
        return Double.parseDouble(numberPart);
    }
}
