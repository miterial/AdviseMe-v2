package com.lanagj.adviseme.controller.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityType) {
        super(entityType + " not found");
    }
}
