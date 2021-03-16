package com.example.integration.demointegration.dao.impl;

import com.example.integration.demointegration.dao.UserDAO;
import com.example.integration.demointegration.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getList() {
        return this.jdbcTemplate.query(SQL_GET, new UserDAO.UserRowMapper());
    }

    @Override
    public int insert(String name) {
        return this.jdbcTemplate.update(SQL_INSERT, name);
    }

    @Override
    public int update(int id, String name) {
        return this.jdbcTemplate.update(SQL_UPDATE, name, id);
    }

    @Override
    public int delete(int id) {
        return this.jdbcTemplate.update(SQL_DELETE, id);
    }
}
