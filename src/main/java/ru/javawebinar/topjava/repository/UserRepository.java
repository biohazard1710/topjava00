package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    //    4: Если у метода нет реализации, то стандартно бросается UnsupportedOperationException.
    //    Для уменьшения количества кода при реализации Optional (п. 7, только DataJpa)
    //    попробуйте сделать default метод в интерфейсе.
    default User getUserWithMeal(int id) {
        throw new UnsupportedOperationException();
    }
}