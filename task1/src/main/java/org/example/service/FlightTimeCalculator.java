package org.example.service;

import org.example.models.Flight;
import org.example.models.FlightSpecialist;
import org.example.models.FlightTimeSummary;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class FlightTimeCalculator {
    private static final int DAILY_LIMIT = 8;
    private static final int WEEKLY_LIMIT = 36;
    private static final int MONTHLY_LIMIT = 80;

    public Map<String, FlightSpecialist> processFlights(List<Flight> flights) {
        Map<String, FlightSpecialist> specialists = new HashMap<>();

        for (Flight flight : flights) {
            Duration duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
            long hours = duration.toHours();

            for (String crewMember : flight.getCrewMembers()) {
                crewMember = crewMember.trim();

                specialists.putIfAbsent(crewMember, new FlightSpecialist(crewMember));
                FlightSpecialist specialist = specialists.get(crewMember);

                YearMonth ym = YearMonth.from(flight.getDepartureTime());


                FlightTimeSummary summary = specialist.getFlightTimeSummaryMap()
                        .computeIfAbsent(ym, k -> new FlightTimeSummary());

                updateSummary(summary, flight.getDepartureTime().toLocalDate(), hours);

            }
        }
        return specialists;
    }



    private void updateSummary(FlightTimeSummary summary, LocalDate date, long hours) {
        summary.setTotalFlightTime(summary.getTotalFlightTime() + hours);

        Map<LocalDate, Long> dailyHours = summary.getDailyFlightHours();
        Map<Integer, Long> weeklyHours = summary.getWeeklyFlightHours();

        dailyHours.merge(date, hours, Long::sum);

        int weekOfMonth = (date.getDayOfMonth() - 1) / 7;
        weeklyHours.merge(weekOfMonth, hours, Long::sum);

        for (long dailyTotal : dailyHours.values()) {
            if (dailyTotal > DAILY_LIMIT) {
                summary.setExceeds8HoursInADay(true);
            }
        }

        for (long weeklyTotal : weeklyHours.values()) {
            if (weeklyTotal > WEEKLY_LIMIT) {
                summary.setExceeds36HoursInAWeek(true);
            }
        }

        if (summary.getTotalFlightTime() > MONTHLY_LIMIT) {
            summary.setExceeds80Hours(true);
        }
    }
}
