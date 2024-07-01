package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, firstUserMeal.getDateTime(), "duplicate", 500), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(FIRST_USER_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(FIRST_USER_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void deleteNotYourMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(FIRST_ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(FIRST_ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(meal, firstAdminMeal);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotYourMeal() {
        assertThrows(NotFoundException.class, () -> service.get(FIRST_USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal meal = getUpdated();
        service.update(meal, USER_ID);
        assertMatch(service.get(FIRST_USER_MEAL_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotYourMeal() {
        assertThrows(NotFoundException.class, () -> service.update(firstAdminMeal, USER_ID));
        assertMatch(service.get(FIRST_ADMIN_MEAL_ID, ADMIN_ID), firstAdminMeal);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), userMeals);
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 31), LocalDate.of(2020, Month.JANUARY, 31), USER_ID), seventhUserMeal, sixthUserMeal, fifthUserMeal, fourthUserMeal);
    }

    public void getBetweenWithNullDates() {
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), userMeals);
    }
}