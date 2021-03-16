package com.example.integration.demointegration.dao;

import com.example.integration.demointegration.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    String COLUMN_ID = "id";
    String COLUMN_NAME = "name";

    String SQL_GET = "SELECT * FROM User;";
    String SQL_INSERT = "INSERT INTO User (name) VALUES (?)";
    String SQL_UPDATE = "UPDATE User SET name=? WHERE id=?";
    String SQL_DELETE = "DELETE FROM User WHERE id=?";

    List<User> getList();

    int insert(String name);

    int update(int id, String name);

    int delete(int id);

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(resultSet.getInt(COLUMN_ID), resultSet.getString(COLUMN_NAME));
        }
    }
}
