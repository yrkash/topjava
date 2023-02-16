package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealComparator;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal->save(meal, SecurityUtil.authUserId()));
    }

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);


    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            log.info("save {}", meal);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (meal.getUserId() == userId)
        {
            log.info("update {}", meal);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

        }
        else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() != userId)
            return false;
        else {
            return repository.remove(id) != null;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId() != userId)
            return null;
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {

        return repository.values().stream()
                .filter(meal -> (meal.getUserId() == userId))
                .sorted(new MealComparator())
                .collect(Collectors.toList());
    }

    public Collection<Meal> getAllWithDateFilter(int userId, LocalDateTime dateFrom, LocalDateTime dateTo) {

        return repository.values().stream()
                .filter(meal -> (meal.getUserId() == userId))
                .filter(meal -> (!dateFrom.isAfter(meal.getDateTime())) && (!dateTo.isBefore(meal.getDateTime())))
                .sorted(new MealComparator())
                .collect(Collectors.toList());
    }
}

