package com.example.integration.demointegration.controller;

import com.example.integration.demointegration.dto.AddUserDTO;
import com.example.integration.demointegration.dto.RESTResponse;
import com.example.integration.demointegration.dto.UpdateUserDTO;
import com.example.integration.demointegration.model.User;
import com.example.integration.demointegration.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/get/all")
    public RESTResponse<List<User>> getAllUsers() {
        return new RESTResponse(this.userService.getUsers());
    }

    @PostMapping(value = "/add")
    public RESTResponse<Integer> addUser(@RequestBody AddUserDTO addUserDTO) {
        return new RESTResponse<>(this.userService.addUser(addUserDTO));
    }

    @DeleteMapping(value = "/delete/{id}")
    public RESTResponse<Integer> deleteUser(@PathVariable int id) {
        return new RESTResponse<>(this.userService.deleteUser(id));
    }

    @PutMapping(value = "/update/{id}")
    public RESTResponse<Integer> updateUser(@PathVariable int id, @RequestBody UpdateUserDTO updateUserDTO) {
        return new RESTResponse<>(this.userService.updateUser(id, updateUserDTO));
    }
}
