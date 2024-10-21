package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class EntityValidation<T> {
    private final T validatedEntity;
    private final List<String> errors = new ArrayList<>();

    public EntityValidation(T validatedEntity) {
        this.validatedEntity = validatedEntity;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public T getValidatedEntity() {
        return validatedEntity;
    }

    public List<String> getErrors() {
        return errors;
    }
}
