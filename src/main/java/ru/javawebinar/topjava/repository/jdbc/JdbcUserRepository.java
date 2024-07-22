package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final String GET_ID = "id";
    private static final String GET_NAME = "name";
    private static final String GET_EMAIL = "email";
    private static final String GET_PASSWORD = "password";
    private static final String GET_REGISTERED = "registered";
    private static final String GET_ENABLED = "enabled";
    private static final String GET_CALORIES_PER_DAY = "calories_per_day";
    private static final String GET_ROLE = "role";

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        } else {
            jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.getId());
            insertRoles(user);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        String sql = """
                SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day,
                       r.role as role 
                FROM users u 
                LEFT JOIN user_role r ON u.id = r.user_id
                WHERE u.id = ?
            """;
        return jdbcTemplate.query(sql, this::extractUser, id);

    }

    @Override
    public User getByEmail(String email) {
        String sql = """
                SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day,
                       r.role as role 
                FROM users u 
                LEFT JOIN user_role r ON u.id = r.user_id
                WHERE u.email = ?
            """;
        return jdbcTemplate.query(sql, this::extractUser, email);

    }

    @Override
    public List<User> getAll() {
        String sql = """
                SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day,
                       r.role as role 
                FROM users u 
                LEFT JOIN user_role r ON u.id = r.user_id
                ORDER BY u.name, u.email
            """;
        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, User> userMap = new LinkedHashMap<>();
            while (rs.next()) {
                int userId = rs.getInt(GET_ID);
                User user = userMap.computeIfAbsent(userId, id -> {
                    try {
                        return createUser(rs);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                String role = rs.getString(GET_ROLE);
                if (role != null) {
                    user.getRoles().add(Role.valueOf(role));
                }
            }
            return new ArrayList<>(userMap.values());
        });

    }

    private void insertRoles(User user) {
        if (!user.getRoles().isEmpty()) {
            List<Object[]> batchArgs = user.getRoles().stream()
                    .map(role -> new Object[]{user.getId(), role.name()})
                    .collect(Collectors.toList());
            jdbcTemplate.batchUpdate("INSERT INTO user_role (user_id, role) VALUES (?, ?)", batchArgs);
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        if (rs.next()) {
            User user = createUser(rs);
            do {
                String role = rs.getString(GET_ROLE);
                if (role != null) {
                    user.getRoles().add(Role.valueOf(role));
                }
            } while (rs.next());
            return user;
        }
        return null;
    }

    private User createUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(GET_ID));
        user.setName(rs.getString(GET_NAME));
        user.setEmail(rs.getString(GET_EMAIL));
        user.setPassword(rs.getString(GET_PASSWORD));
        user.setRegistered(rs.getTimestamp(GET_REGISTERED));
        user.setEnabled(rs.getBoolean(GET_ENABLED));
        user.setCaloriesPerDay(rs.getInt(GET_CALORIES_PER_DAY));
        user.setRoles(new HashSet<>());
        return user;
    }

}
