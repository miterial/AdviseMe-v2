package com.lanagj.adviseme.controller.user;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityType) {
        super(entityType + " not found");
    }
}
