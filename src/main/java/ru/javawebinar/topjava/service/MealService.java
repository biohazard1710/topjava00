package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Meal> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        Integer mealId = getMealId(meal);
        Meal mealFromDb = getMeal(userId, mealId);
        User user = mealFromDb.getUser();
        meal.setUser(user);
        repository.update(meal, userId);
    }

    public Meal create(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        return repository.create(meal, userId);
    }

    private Meal getMeal(int userId, Integer mealId) {
        Meal mealFromDb = repository.get(mealId, userId);
        if (mealFromDb == null) {
            throw new NotFoundException("meal with id " + mealId + " not found for user with id " + userId);
        }
        return mealFromDb;
    }

    private Integer getMealId(Meal meal) {
        Integer mealId = meal.getId();
        Assert.notNull(mealId, "meal id must not be null");
        return mealId;
    }
}