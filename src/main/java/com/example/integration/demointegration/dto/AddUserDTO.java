package com.example.integration.demointegration.dto;

import java.io.Serializable;

public class AddUserDTO implements Serializable {
    private String name;

    public AddUserDTO() {
    }

    public AddUserDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
