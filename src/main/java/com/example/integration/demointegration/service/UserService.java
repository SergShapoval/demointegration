package com.example.integration.demointegration.service;

import com.example.integration.demointegration.dto.AddUserDTO;
import com.example.integration.demointegration.dto.UpdateUserDTO;
import com.example.integration.demointegration.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    int addUser(AddUserDTO addUserDTO);

    int deleteUser(int id);

    int updateUser(int id, UpdateUserDTO updateUserDTO);
}
