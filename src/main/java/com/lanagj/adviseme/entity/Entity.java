package com.lanagj.adviseme.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

public abstract class Entity {

    @Id
    @Getter
    protected String id;

}
