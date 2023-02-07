package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.concurrent.CopyOnWriteArrayList;

public class MealMockDAOImpl implements MealDAO{
    @Override
    public void addMeal(Meal meal) {
        MealsUtil.meals.add(meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        int indexToDelete = 0;
        for (Meal meal: MealsUtil.meals) {
            if (meal.getId() == mealId) {
                indexToDelete = MealsUtil.meals.indexOf(meal);
                break;
            }
        }
        if (indexToDelete != 0)
            MealsUtil.meals.remove(indexToDelete);
    }

    @Override
    public void updateMeal(Meal mealToUpdate, int mealId) {
        for (Meal meal: MealsUtil.meals) {
            if (meal.getId() == mealId) {
                meal.setDateTime(mealToUpdate.getDateTime());
                meal.setDescription(mealToUpdate.getDescription());
                meal.setCalories(mealToUpdate.getCalories());
                break;
            }
        }
    }

    @Override
    public CopyOnWriteArrayList<MealTo> getAllMeals() {
        CopyOnWriteArrayList<MealTo> result = new CopyOnWriteArrayList<>(
                MealsUtil.filteredByStreamsWithoutFiltration(MealsUtil.meals,MealsUtil.CALORIES_PER_DAY));
        return result;
    }

    @Override
    public Meal getMealById(int mealId) {
        for (Meal meal: MealsUtil.meals) {
            if (meal.getId() == mealId) {
                return meal;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        MealMockDAOImpl dao = new MealMockDAOImpl();
        CopyOnWriteArrayList<MealTo> meals = dao.getAllMeals();
        for (MealTo meal: meals) {
            System.out.println(meal);
        }
    }

}
