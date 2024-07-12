package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int userId);

    // false if meal does not belong to userId
    boolean delete(int id, int userId);

    // null if meal does not belong to userId
    Meal get(int id, int userId);

    // ORDERED dateTime desc
    List<Meal> getAll(int userId);

    // ORDERED dateTime desc
    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    //    4: Если у метода нет реализации, то стандартно бросается UnsupportedOperationException.
    //    Для уменьшения количества кода при реализации Optional (п. 7, только DataJpa)
    //    попробуйте сделать default метод в интерфейсе.
    default Meal getMealWithUser(int id, int userId) {
        throw new UnsupportedOperationException();
    }
}
