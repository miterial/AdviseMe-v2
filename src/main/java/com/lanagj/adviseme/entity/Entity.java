package com.lanagj.adviseme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
public abstract class Entity {

    @Id
    @Getter
    Integer id;

}
