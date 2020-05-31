package com.lanagj.adviseme.configuration;

public enum AlgorithmType {
    LSA("Latent Semantic Analysis"),
    MLSA("Modified Latent Semantic Analysis");

    String name;

    AlgorithmType(String type) {
        this.name = type;
    }

    public String getName() {
        return this.name;
    }
}