package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController{

    private static final String ATTRIBUTE_MEAL = "meal";
    private static final String ATTRIBUTE_MEALS = "meals";
    private static final String JSP_MEAL_FORM = "mealForm";
    private static final String JSP_MEALS = "meals";
    private static final String PARAMETER_CALORIES = "calories";
    private static final String PARAMETER_DATE_TIME = "dateTime";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_START_DATE = "startDate";
    private static final String PARAMETER_START_TIME = "startTime";
    private static final String PARAMETER_END_DATE = "endDate";
    private static final String PARAMETER_END_TIME = "endTime";
    private static final String REDIRECT_MEALS = "redirect:/meals";

    public JspMealController(MealService service) {
        super(service);
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter(PARAMETER_DATE_TIME)),
                request.getParameter(PARAMETER_DESCRIPTION),
                Integer.parseInt(request.getParameter(PARAMETER_CALORIES)));

        if (StringUtils.hasLength(request.getParameter(PARAMETER_ID))) {
            super.update(meal, getId(request));
        } else {
            super.create(meal);
        }
        return REDIRECT_MEALS;
    }

    @GetMapping("delete")
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        super.delete(id);
        return REDIRECT_MEALS;
    }

    @GetMapping("create")
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute(ATTRIBUTE_MEAL, meal);
        return JSP_MEAL_FORM;
    }

    @GetMapping("update")
    public String update(HttpServletRequest request, Model model) {
        final Meal meal = super.get(getId(request));
        model.addAttribute(ATTRIBUTE_MEAL, meal);
        return JSP_MEAL_FORM;
    }

    @GetMapping("filter")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter(PARAMETER_START_DATE));
        LocalDate endDate = parseLocalDate(request.getParameter(PARAMETER_END_DATE));
        LocalTime startTime = parseLocalTime(request.getParameter(PARAMETER_START_TIME));
        LocalTime endTime = parseLocalTime(request.getParameter(PARAMETER_END_TIME));
        model.addAttribute(ATTRIBUTE_MEALS, super.getBetween(startDate, startTime, endDate, endTime));
        return JSP_MEALS;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute(ATTRIBUTE_MEALS, super.getAll());
        return JSP_MEALS;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter(PARAMETER_ID));
        return Integer.parseInt(paramId);
    }

}
