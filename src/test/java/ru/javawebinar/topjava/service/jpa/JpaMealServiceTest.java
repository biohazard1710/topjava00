package ru.javawebinar.topjava.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(Profiles.JPA)
public class JpaMealServiceTest extends MealServiceTest {

    @Autowired
    private MealService jpaMealService;

    @Override
    protected MealService getService() {
        return jpaMealService;
    }
}