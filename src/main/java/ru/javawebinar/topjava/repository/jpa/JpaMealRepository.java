package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class,userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User ref = em.getReference(User.class,userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("1",ref)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter(1, userId)
                .getResultList();
        return DataAccessUtils.singleResult(meals);


    }

    @Override
    public List<Meal> getAll(int userId) {

        List<Meal> meals = em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(1, userId)
                .getResultList();
        return meals;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(1, userId)
                .setParameter(2,startDateTime)
                .setParameter(3, endDateTime)
                .getResultList();
        return meals;
    }
}