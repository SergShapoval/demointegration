package com.example.integration.demointegration.dto;

import java.io.Serializable;
import java.util.Map;

public class RESTResponse<T> implements Serializable {
    private T response;
    private Map<String, String> errors;

    public RESTResponse() {
    }

    public RESTResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
