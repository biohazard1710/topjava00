package ru.javawebinar.topjava.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(Profiles.JPA)
public class JpaUserServiceTest extends UserServiceTest {

    @Autowired
    private UserService jpaUserService;

    @Override
    protected UserService getService() {
        return jpaUserService;
    }
}
