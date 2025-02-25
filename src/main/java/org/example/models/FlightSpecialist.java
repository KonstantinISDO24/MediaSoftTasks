package org.example.models;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class FlightSpecialist {
    private String name;
    private Map<YearMonth, FlightTimeSummary> flightTimeSummaryMap = new HashMap<>();

    public FlightSpecialist() {
    }

    public FlightSpecialist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<YearMonth, FlightTimeSummary> getFlightTimeSummaryMap() {
        return flightTimeSummaryMap;
    }

}
