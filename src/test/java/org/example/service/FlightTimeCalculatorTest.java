package org.example.service;

import org.example.models.Flight;
import org.example.models.FlightSpecialist;
import org.example.models.FlightTimeSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FlightTimeCalculatorTest {

    private FlightTimeCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new FlightTimeCalculator();
    }

    @Test
    void testProcessFlights() {
        Flight flight1 = new Flight();
        flight1.setDepartureTime(LocalDateTime.of(2023, 10, 1, 10, 0));
        flight1.setArrivalTime(LocalDateTime.of(2023, 10, 1, 12, 0));
        flight1.setCrewMembers(Arrays.asList("John Doe", "Jane Doe"));

        Flight flight2 = new Flight();
        flight2.setDepartureTime(LocalDateTime.of(2023, 10, 1, 14, 0));
        flight2.setArrivalTime(LocalDateTime.of(2023, 10, 1, 18, 0));
        flight2.setCrewMembers(Arrays.asList("John Doe"));

        List<Flight> flights = Arrays.asList(flight1, flight2);

        Map<String, FlightSpecialist> specialists = calculator.processFlights(flights);

        assertNotNull(specialists);
        assertEquals(2, specialists.size());

        FlightSpecialist johnDoe = specialists.get("John Doe");
        assertNotNull(johnDoe);

        FlightTimeSummary summary = johnDoe.getFlightTimeSummaryMap().get(YearMonth.of(2023, 10));
        assertNotNull(summary);
        assertEquals(6, summary.getTotalFlightTime());
        assertFalse(summary.isExceeds8HoursInADay());
    }
}