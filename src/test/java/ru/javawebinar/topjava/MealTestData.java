package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = START_SEQ;

    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final Meal MEAL_1 = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY,30, 10,00,00),"Завтрак",500);
    public static final Meal MEAL_2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY,30, 13,00,00),"Обед",1000);
    public static final Meal MEAL_3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY,30, 20,00,00),"Ужин",500);
    public static final Meal MEAL_4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY,31, 00,00,00),"Еда на граничное значение",100);
    public static final Meal MEAL_5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY,31, 10,00,00),"Завтрак",500);
    public static final Meal MEAL_6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY,31, 13,00,00),"Обед",500);
    public static final Meal MEAL_7 = new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY,31, 20,00,00),"Ужин",410);
//    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
//    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest");

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY,30, 00,00,00), "newMeal", 1001);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal();
        updated.setId(MEAL_ID);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("UpdatedDescription");
        updated.setCalories(1234);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}
