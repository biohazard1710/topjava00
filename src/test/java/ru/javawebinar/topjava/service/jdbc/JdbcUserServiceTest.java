package ru.javawebinar.topjava.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(Profiles.JDBC)
public class JdbcUserServiceTest extends UserServiceTest {

    @Autowired
    private UserService jdbcUserService;

    @Override
    protected UserService getService() {
        return jdbcUserService;
    }
}
