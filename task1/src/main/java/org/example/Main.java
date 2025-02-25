package org.example;

import org.example.models.Flight;
import org.example.models.FlightSpecialist;
import org.example.service.FileService;
import org.example.service.FlightTimeCalculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] values = line.split(",");
                if (values.length != 7) {
                    System.out.println("Некорректный формат строки: " + line);
                    continue;
                }

                Flight flight = getFlight(values, formatter);

                flights.add(flight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FlightTimeCalculator calculator = new FlightTimeCalculator();
        Map<String, FlightSpecialist> specialists = calculator.processFlights(flights);

        FileService fileService = new FileService();
        try {
            fileService.writeToJsonFile(new ArrayList<>(specialists.values()), "output.json");
            System.out.println("Данные успешно записаны в output.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Flight getFlight(String[] values, DateTimeFormatter formatter) {
        Flight flight = new Flight();
        flight.setAircraftType(values[0].trim());
        flight.setAircraftNumber(values[1].trim());
        flight.setDepartureTime(LocalDateTime.parse(values[2].trim(), formatter));
        flight.setArrivalTime(LocalDateTime.parse(values[3].trim(), formatter));
        flight.setDepartureAirport(values[4].trim());
        flight.setArrivalAirport(values[5].trim());
        flight.setCrewMembers(Arrays.asList(values[6].trim().split("\\s*,\\s*")));
        return flight;
    }
}
