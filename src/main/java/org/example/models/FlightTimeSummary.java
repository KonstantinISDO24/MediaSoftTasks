package org.example.models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FlightTimeSummary {
    private long totalFlightTime;
    private boolean exceeds80Hours;
    private boolean exceeds36HoursInAWeek;
    private boolean exceeds8HoursInADay;

    private final Map<LocalDate, Long> dailyFlightHours = new HashMap<>();
    private final Map<Integer, Long> weeklyFlightHours = new HashMap<>();

    public long getTotalFlightTime() {
        return totalFlightTime;
    }

    public void setTotalFlightTime(long totalFlightTime) {
        this.totalFlightTime = totalFlightTime;
    }

    public boolean isExceeds80Hours() {
        return exceeds80Hours;
    }

    public void setExceeds80Hours(boolean exceeds80Hours) {
        this.exceeds80Hours = exceeds80Hours;
    }

    public boolean isExceeds36HoursInAWeek() {
        return exceeds36HoursInAWeek;
    }

    public void setExceeds36HoursInAWeek(boolean exceeds36HoursInAWeek) {
        this.exceeds36HoursInAWeek = exceeds36HoursInAWeek;
    }

    public boolean isExceeds8HoursInADay() {
        return exceeds8HoursInADay;
    }

    public void setExceeds8HoursInADay(boolean exceeds8HoursInADay) {
        this.exceeds8HoursInADay = exceeds8HoursInADay;
    }

    public Map<LocalDate, Long> getDailyFlightHours() {
        return dailyFlightHours;
    }

    public Map<Integer, Long> getWeeklyFlightHours() {
        return weeklyFlightHours;
    }
}