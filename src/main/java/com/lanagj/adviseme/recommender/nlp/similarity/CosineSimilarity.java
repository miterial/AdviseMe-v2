package com.lanagj.adviseme.recommender.nlp.similarity;

import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @see <a href="https://commons.apache.org/sandbox/commons-text/jacoco/org.apache.commons.text.similarity/CosineSimilarity.java.html">
 */
@Service
public class CosineSimilarity {

    public Double findSimilarity(List<DocumentStats> document1, List<DocumentStats> document2) {

        Set<String> wordsDocument1 = document1.stream().map(DocumentStats::getWord).collect(Collectors.toSet());
        Set<String> wordsDocument2 = document2.stream().map(DocumentStats::getWord).collect(Collectors.toSet());
        Set<String> commonWords = this.getCommonWords(
                wordsDocument1,
                wordsDocument2
                );

        final double dotProduct = dot(
                document1.stream().collect(Collectors.toMap(DocumentStats::getWord, DocumentStats::getValue)),
                document2.stream().collect(Collectors.toMap(DocumentStats::getWord, DocumentStats::getValue)),
                commonWords);

        List<Double> valuesDocument1 = document1.stream().map(DocumentStats::getValue).collect(Collectors.toList());
        List<Double> valuesDocument2 = document2.stream().map(DocumentStats::getValue).collect(Collectors.toList());

        double d1 = 0.0d;
        for (final Double value : valuesDocument1) {
            d1 += Math.pow(value, 2);
        }
        double d2 = 0.0d;
        for (final Double value : valuesDocument2) {
            d2 += Math.pow(value, 2);
        }
        Double cosineSimilarity;
        if (d1 <= 0.0 || d2 <= 0.0) {
            cosineSimilarity = 0.0;
        } else {
            cosineSimilarity = dotProduct / (Math.sqrt(d1) * Math.sqrt(d2));
        }
        BigDecimal bd = new BigDecimal(cosineSimilarity).setScale(8, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
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
