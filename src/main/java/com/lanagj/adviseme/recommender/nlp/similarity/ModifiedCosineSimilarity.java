package com.lanagj.adviseme.recommender.nlp.similarity;

import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @see <a href="https://commons.apache.org/sandbox/commons-text/jacoco/org.apache.commons.text.similarity/CosineSimilarity.java.html">
 */
@Service
@Slf4j
public class ModifiedCosineSimilarity {

    private final double WEIGHT = 1.5;

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
        double d2 = 0.0d;
        double temp = 0.0;

        double vectorValue1;
        double vectorValue2;

        for (int i = 0; i < valuesDocument1.size(); i++) {
            vectorValue1 = valuesDocument1.get(i);
            vectorValue2 = valuesDocument2.get(i);
            d1 += Math.pow(vectorValue1, 2);
            d2 += Math.pow(vectorValue2, 2);

            if(vectorValue1 > 0 && vectorValue2 > 0) {
                temp += WEIGHT;
            } else {
                temp += 0.5;
            }
        }
        double cosineSimilarity;
        if(d1 <= 0 || d2 <= 0) {
            return 0.0;
        }
        double v = Math.sqrt(d1) * Math.sqrt(d2) + Math.sqrt(1 / temp);
        cosineSimilarity = dotProduct / v;

        if(cosineSimilarity > 1.0) {
            log.info("Ooops, too much");
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
            Double aDouble = valuesFromDocument1.get(commonWord);
            Double aDouble1 = valuesFromDocument2.get(commonWord);
            if (aDouble < 0) {
                aDouble = 0.0;
            }
            if (aDouble1 < 0) {
                aDouble1 = 0.0;
            }
            double weight = 0.5;
            if(aDouble > 0 && aDouble1 > 0) {
                weight = WEIGHT;
            }
            dotProduct += aDouble * aDouble1 * weight;
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
