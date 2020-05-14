package com.lanagj.adviseme.recommender.nlp.weight;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DocumentStatsToArrayConverter {

    /**
     * @param documentStats word vectors of N*N size sorted by document ID
     * @return
     */
    public double[][] convert(Map<String, List<DocumentStats>> documentStats) {

        // word vectors
        double[][] result = new double[documentStats.size()][];

        List<List<DocumentStats>> matrix = new ArrayList<>(documentStats.values());
        for (int i = 0; i < documentStats.size(); i++) {    // amount of word vectors

            List<DocumentStats> wordVector = matrix.get(i);
            result[i] = new double[wordVector.size()];

            for (int j = 0; j < wordVector.size(); j++) {   // amount of vector values
                result[i][j] = wordVector.get(j).getValue();
            }
        }
        return result;
    }
}
