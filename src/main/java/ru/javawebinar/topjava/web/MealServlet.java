package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final String CONFIG_LOCATION = "spring/spring-app.xml";
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_DATE_TIME = "dateTime";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_CALORIES = "calories";
    private static final String PARAMETER_ACTION = "action";
    private static final String MEAL = "meal";
    private static final String MEALS = "meals";
    private static final String MEAL_FORM_JSP = "/mealForm.jsp";
    private static final String MEALS_JSP = "/meals.jsp";

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext springContext;
    private MealRestController mealRestController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
        mealRestController = springContext.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(CHARACTER_ENCODING);
        String id = request.getParameter(PARAMETER_ID);

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter(PARAMETER_DATE_TIME)),
                request.getParameter(PARAMETER_DESCRIPTION),
                Integer.parseInt(request.getParameter(PARAMETER_CALORIES)));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, meal.getId());
        }
        response.sendRedirect(MEALS);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionParam = request.getParameter(PARAMETER_ACTION);
        Action action = Action.fromString(actionParam);

        switch (action == null ? Action.ALL : action) {
            case DELETE:
                int id = getId(request);
                log.info("Delete id={}", id);
                mealRestController.delete(id);
                response.sendRedirect(MEALS);
                break;
            case CREATE:
            case UPDATE:
                final Meal meal = Action.CREATE.equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute(MEAL, meal);
                request.getRequestDispatcher(MEAL_FORM_JSP).forward(request, response);
                break;
            case ALL:
            default:
                log.info("getAll");
                List<MealTo> meals = mealRestController.getAll();
                request.setAttribute(MEALS, meals);
                request.getRequestDispatcher(MEALS_JSP).forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter(PARAMETER_ID));
        return Integer.parseInt(paramId);
    }
}