package com.example.integration.demointegration.service.impl;

import com.example.integration.demointegration.dao.UserDAO;
import com.example.integration.demointegration.dto.AddUserDTO;
import com.example.integration.demointegration.dto.UpdateUserDTO;
import com.example.integration.demointegration.model.User;
import com.example.integration.demointegration.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getUsers() {
        return this.userDAO.getList();
    }

    @Override
    public int addUser(AddUserDTO addUserDTO) {
        return this.userDAO.insert(addUserDTO.getName());
    }

    @Override
    public int deleteUser(int id) {
        return this.userDAO.delete(id);
    }

    @Override
    public int updateUser(int id, UpdateUserDTO updateUserDTO) {
        return this.userDAO.update(id, updateUserDTO.getName());
    }
}
