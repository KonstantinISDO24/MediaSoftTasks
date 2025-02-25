package org.example.service;

import org.example.models.FlightSpecialist;
import org.example.models.FlightTimeSummary;

import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class FileService {
    public FileService() {
    }

    public void writeToJsonFile(List<FlightSpecialist> specialists, String filePath) throws IOException {
        String json = serializeSpecialists(specialists);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    private String serializeSpecialists(List<FlightSpecialist> specialists) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < specialists.size(); i++) {
            FlightSpecialist specialist = specialists.get(i);
            jsonBuilder.append("  {")
                    .append("\n    \"name\":\"").append(specialist.getName()).append("\",")
                    .append("\n    \"flightTimeSummaryMap\":{");

            Map<YearMonth, FlightTimeSummary> summaryMap = specialist.getFlightTimeSummaryMap();
            int mapIndex = 0;
            for (Map.Entry<YearMonth, FlightTimeSummary> entry : summaryMap.entrySet()) {
                YearMonth month = entry.getKey();
                FlightTimeSummary summary = entry.getValue();

                jsonBuilder.append("\n      \"").append(month).append("\":{")
                        .append("\n        \"totalFlightTime\":").append(summary.getTotalFlightTime()).append(",")
                        .append("\n        \"exceeds80Hours\":").append(summary.isExceeds80Hours()).append(",")
                        .append("\n        \"exceeds36HoursInAWeek\":").append(summary.isExceeds36HoursInAWeek()).append(",")
                        .append("\n        \"exceeds8HoursInADay\":").append(summary.isExceeds8HoursInADay())
                        .append("\n      }");

                if (mapIndex < summaryMap.size() - 1) jsonBuilder.append(",");
                mapIndex++;
            }

            jsonBuilder.append("\n    }");
            jsonBuilder.append("\n  }");

            if (i < specialists.size() - 1) jsonBuilder.append(",");
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}

