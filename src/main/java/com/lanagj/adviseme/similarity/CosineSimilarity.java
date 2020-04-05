package com.lanagj.adviseme.similarity;

import com.lanagj.adviseme.lsa.weight.WordDocument;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @see <a href="https://commons.apache.org/sandbox/commons-text/jacoco/org.apache.commons.text.similarity/CosineSimilarity.java.html">
 */
public class CosineSimilarity implements SimilarityMeasure {

    public Double findSimilarity(WordDocument document1, WordDocument document2) {

        Set<String> commonWords = this.getCommonWords(document1.getValues().keySet(), document2.getValues().keySet());

        final double dotProduct = dot(document1.getValues(), document2.getValues(), commonWords);

        double d1 = 0.0d;
        for (final Double value : document1.getValues().values()) {
            d1 += Math.pow(value, 2);
        }
        double d2 = 0.0d;
        for (final Double value : document2.getValues().values()) {
            d2 += Math.pow(value, 2);
        }
        double cosineSimilarity;
        if (d1 <= 0.0 || d2 <= 0.0) {
            cosineSimilarity = 0.0;
        } else {
            cosineSimilarity = dotProduct / Math.sqrt(d1) * Math.sqrt(d2);
        }
        return cosineSimilarity;
    }

    /**
     * Computes the dot product of two vectors. It ignores remaining elements. It means
     * that if a vector is longer than other, then a smaller part of it will be used to compute
     * the dot product.
     */
    private double dot(Map<String, Double> valuesFromDocument1,
                       Map<String, Double> valuesFromDocument2,
                       Set<String> commonWords) {

        double dotProduct = 0.0;
        for (String commonWord : commonWords) {
            dotProduct += valuesFromDocument1.get(commonWord) * valuesFromDocument2.get(commonWord);
        }

        return dotProduct;
    }

    private Set<String> getCommonWords(Set<String> wordsFromDocument1,
                                       Set<String> wordsFromDocument2) {

        return wordsFromDocument1.stream()
                .distinct()
                .filter(wordsFromDocument2::contains)
                .collect(Collectors.toSet());
    }

}
