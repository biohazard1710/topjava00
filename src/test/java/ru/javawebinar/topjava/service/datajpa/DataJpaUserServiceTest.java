package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

import static org.junit.Assert.assertNotNull;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    private UserService dataJpaUserService;

    @Override
    protected UserService getService() {
        return dataJpaUserService;
    }

    @Test
    public void getMealWithUserTest() {
        User user = getService().getUserWithMeal(USER_ID);
        assertNotNull(user);
        assertNotNull(user.getMeals());
    }
}