package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

@Ignore
public abstract class UserServiceTest extends BaseServiceTest {

    protected abstract UserService getService();
    
    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() {
        User created = getService().create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(getService().get(newId), newUser);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                getService().create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    public void delete() {
        getService().delete(USER_ID);
        assertThrows(NotFoundException.class, () -> getService().get(USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> getService().delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = getService().get(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> getService().get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = getService().getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, admin);
    }

    @Test
    public void update() {
        User updated = getUpdated();
        getService().update(updated);
        USER_MATCHER.assertMatch(getService().get(USER_ID), getUpdated());
    }

    @Test
    public void getAll() {
        List<User> all = getService().getAll();
        USER_MATCHER.assertMatch(all, admin, guest, user);
    }
}