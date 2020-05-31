package com.lanagj.adviseme.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ApplicationSettings {

    @Setter
    private AlgorithmType type;
    private static ApplicationSettings instance;

    private ApplicationSettings() {
        this.type = AlgorithmType.MLSA;
    }

    public static ApplicationSettings getInstance() {
        if(instance == null) {
            instance = new ApplicationSettings();
        }
        return instance;
    }
}
