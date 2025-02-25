package org.example.service;

import org.example.models.FlightSpecialist;
import org.example.models.FlightTimeSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @Test
    void testWriteToJsonFile() throws IOException {
        FlightTimeSummary summary = new FlightTimeSummary();
        summary.setTotalFlightTime(10);
        summary.setExceeds80Hours(false);
        summary.setExceeds36HoursInAWeek(false);
        summary.setExceeds8HoursInADay(true);

        FlightSpecialist specialist = new FlightSpecialist("John Doe");
        specialist.getFlightTimeSummaryMap().put(YearMonth.of(2023, 10), summary);

        List<FlightSpecialist> specialists = Collections.singletonList(specialist);

        String filePath = "test_output.json";
        fileService.writeToJsonFile(specialists, filePath);

        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        assertTrue(jsonContent.contains("\"name\":\"John Doe\""));
        assertTrue(jsonContent.contains("\"totalFlightTime\":10"));
        assertTrue(jsonContent.contains("\"exceeds8HoursInADay\":true"));

        Files.deleteIfExists(Paths.get(filePath));
    }
}