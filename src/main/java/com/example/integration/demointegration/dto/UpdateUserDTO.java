package com.example.integration.demointegration.dto;

import java.io.Serializable;

public class UpdateUserDTO implements Serializable {
    private String name;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
