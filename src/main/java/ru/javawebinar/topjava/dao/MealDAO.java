package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.concurrent.CopyOnWriteArrayList;

public interface MealDAO {
    void addMeal(Meal meal);
    void deleteMeal(int mealId);
    void updateMeal(Meal meal, int mealId);
    CopyOnWriteArrayList<MealTo> getAllMeals();
    Meal getMealById(int mealId);

}
