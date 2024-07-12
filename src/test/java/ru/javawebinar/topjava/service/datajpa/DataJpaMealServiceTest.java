package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;

import static org.junit.Assert.assertNotNull;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Autowired
    private MealService dataJpaMealService;

    @Override
    protected MealService getService() {
        return dataJpaMealService;
    }

    @Test
    public void getUserWithMealTest() {
        Meal meal = getService().getMealWithUser(MEAL1_ID, USER_ID);
        assertNotNull(meal);
        assertNotNull(meal.getUser());
    }
}