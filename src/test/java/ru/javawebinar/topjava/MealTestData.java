package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDateTime.of;

public class MealTestData {
    public static final int FIRST_USER_MEAL_ID = START_SEQ + 3;
    public static final int FIRST_ADMIN_MEAL_ID = START_SEQ + 10;
    public static final int NOT_FOUND = 10;

    public static final Meal firstUserMeal = new Meal(FIRST_USER_MEAL_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal secondUserMeal = new Meal(FIRST_USER_MEAL_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal thirdUserMeal = new Meal (FIRST_USER_MEAL_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal fourthUserMeal = new Meal(FIRST_USER_MEAL_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal fifthUserMeal = new Meal(FIRST_USER_MEAL_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal sixthUserMeal = new Meal(FIRST_USER_MEAL_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal seventhUserMeal = new Meal(FIRST_USER_MEAL_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal firstAdminMeal = new Meal(FIRST_ADMIN_MEAL_ID, of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal secondAdminMeal = new Meal(FIRST_ADMIN_MEAL_ID + 1, of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);


    public static final List<Meal> userMeals = Arrays.asList(seventhUserMeal, sixthUserMeal, fifthUserMeal, fourthUserMeal, thirdUserMeal, secondUserMeal, firstUserMeal);

    public static Meal getNew() {
        return new Meal(null, of(2020, Month.JANUARY, 31, 23, 0), "Новая еда", 200);
    }

    public static Meal getUpdated() {
        return new Meal(FIRST_USER_MEAL_ID, firstUserMeal.getDateTime(), "Обновленный завтрак", 1000);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}