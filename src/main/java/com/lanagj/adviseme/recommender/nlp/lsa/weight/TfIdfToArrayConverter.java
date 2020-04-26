package com.lanagj.adviseme.recommender.nlp.lsa.weight;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TfIdfToArrayConverter {

    public double[][] convert(List<DocumentStats> documentStats) {

        double[][] result = new double[documentStats.size()][];

        for (int i = 0; i < documentStats.size(); i++) {
            DocumentStats document = documentStats.get(i);
        }
        return null;
    }
}
