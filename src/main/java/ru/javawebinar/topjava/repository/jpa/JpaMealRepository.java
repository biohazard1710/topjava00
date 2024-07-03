package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_USER_ID = "userId";
    private static final String PARAMETER_START_DATE_TIME = "startDateTime";
    private static final String PARAMETER_END_DATE_TIME = "endDateTime";

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if(meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter(PARAMETER_ID, id)
                .setParameter(PARAMETER_USER_ID, userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            return em.createNamedQuery(Meal.BY_MEAL_ID_AND_USER_ID, Meal.class)
                    .setParameter(PARAMETER_ID, id)
                    .setParameter(PARAMETER_USER_ID, userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(PARAMETER_USER_ID, userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN_HALF_OPEN, Meal.class)
                .setParameter(PARAMETER_USER_ID, userId)
                .setParameter(PARAMETER_START_DATE_TIME, startDateTime)
                .setParameter(PARAMETER_END_DATE_TIME, endDateTime)
                .getResultList();
    }
}