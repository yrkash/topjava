package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealTo {

    private Integer id;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
    }


    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }

    public String getDateTimeWithoutT() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(getDateTime());
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean getExceed() {
        return isExcess();
    }

    public boolean isExcess() {
        return excess;
    }
}
