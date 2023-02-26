package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        if (meal.isNew()) {
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            if (meal.getUser().getId() == userId) {
                return em.merge(meal);
            }
            return null;
            /*List<Meal> meals = em.createNamedQuery(Meal.UPDATE, Meal.class)
                    .setParameter("id",meal.getId())
                    .setParameter(1, meal.getDescription())
                    .setParameter(2, meal.getDateTime())
                    .setParameter(3, meal.getCalories())
                    .setParameter(4, ref)
                    .getResultList();
            return DataAccessUtils.singleResult(meals);*/
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User ref = em.getReference(User.class,userId);

        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user=:user");
        return query.setParameter("id", id)
                .setParameter("user", ref)
                .executeUpdate() != 0;
        /*User ref = em.getReference(User.class,userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("1",ref)
                .executeUpdate() != 0;*/
    }

    @Override
    public Meal get(int id, int userId) {
        User ref = em.getReference(User.class,userId);
//        return em.find(Meal.class, id);
        List<Meal> meals = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id",id)
                .setParameter(1, ref)
                .getResultList();
        return DataAccessUtils.singleResult(meals);


    }

    @Override
    public List<Meal> getAll(int userId) {
        User ref = em.getReference(User.class,userId);

        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(1, ref)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        User ref = em.getReference(User.class,userId);

        return em.createNamedQuery(Meal.ALL_SORTED_TIME, Meal.class)
                .setParameter(1, ref)
                .setParameter(2, startDateTime)
                .setParameter(3, endDateTime)
                .getResultList();
    }
}