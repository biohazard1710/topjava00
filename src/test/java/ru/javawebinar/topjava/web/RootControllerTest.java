package ru.javawebinar.topjava.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.util.MealsUtil.*;

class RootControllerTest extends AbstractControllerTest {

    private static final String USERS_URL_TEMPLATE = "/users";
    private static final String MEALS_URL_TEMPLATE = "/meals";
    private static final String USERS_EXPECTED_VIEW = "users";
    private static final String MEALS_EXPECTED_VIEW = "meals";
    private static final String USERS_JSP_FORWARDER_URL = "/WEB-INF/jsp/users.jsp";
    private static final String MEALS_JSP_FORWARDER_URL = "/WEB-INF/jsp/meals.jsp";
    private static final String USERS_MODEL_ATTRIBUTE = "users";
    private static final String MEALS_MODEL_ATTRIBUTE = "meals";

    @Test
    void getUsers() throws Exception {
        perform(get(USERS_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_EXPECTED_VIEW))
                .andExpect(forwardedUrl(USERS_JSP_FORWARDER_URL))
                .andExpect(model().attribute(USERS_MODEL_ATTRIBUTE,
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError {
                                USER_MATCHER.assertMatch(actual, admin, guest, user);
                            }
                        }
                ));
    }

    @Test
    void getMeals() throws Exception {
        perform(get(MEALS_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(MEALS_EXPECTED_VIEW))
                .andExpect(forwardedUrl(MEALS_JSP_FORWARDER_URL))
                .andExpect(model().attribute(MEALS_MODEL_ATTRIBUTE, getTos(meals, user.getCaloriesPerDay())));
    }

}
