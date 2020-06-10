package com.lanagj.adviseme.recommender.nlp.similarity;

import org.springframework.stereotype.Service;

@Service
public class JaccardSimilarity {

    public Double findSimilarity(int common, int all) {

        if (common == 0.0 || all == 0.0) {
            return 0.0;
        }

        return (double) common / (double) all;
    }
}
