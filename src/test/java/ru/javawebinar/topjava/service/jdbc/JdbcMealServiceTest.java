package ru.javawebinar.topjava.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(Profiles.JDBC)
public class JdbcMealServiceTest extends MealServiceTest {

    @Autowired
    private MealService jdbcMealService;

    @Override
    protected MealService getService() {
        return jdbcMealService;
    }
}