package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Meal result = meal;
        User user = new User();
        user.setId(userId);
        result.setUser(user);
//        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) {
            return crudRepository.save(result);
        }
        return get(meal.id(), userId) == null ? null : crudRepository.save(meal);

    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id,userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Optional<Meal> meal = crudRepository.findById(id);
        if (meal.isPresent() && meal.get().getUser().getId() == userId) return meal.get();
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {

        return crudRepository.getMealsByAllSortedContainingAndUser(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getMealsByAllSortedContainingAndDateTime(startDateTime, endDateTime, userId);
    }
}
