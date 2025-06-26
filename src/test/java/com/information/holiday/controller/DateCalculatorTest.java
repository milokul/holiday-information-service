package com.information.holiday.controller;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DateCalculatorTest {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final DateCalculator dateCalculator = new DateCalculator();

    @Test
    void testAddOneYear_RegularDate() throws Exception {
        // When
        String result = dateCalculator.addOneYear("2025-06-24");

        // Then
        assertEquals("2026-06-24", result);
    }

    @Test
    void testAddOneYear_LeapYearDate() throws Exception {
        // When
        String result = dateCalculator.addOneYear("2024-02-29");

        // Then
        assertEquals("2025-02-28", result);
    }

    @Test
    void testAddOneYear_NullDate() {
        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> dateCalculator.addOneYear(null));
    }
    @Test
    void shouldAddOneDayToRegularDate() {
        // given
        DateCalculator dateCalculator = new DateCalculator();
        String date = "2024-03-15";

        // when
        String result = dateCalculator.addOneDay(date);

        // then
        assertThat(result).isEqualTo("2024-03-16");
    }

}