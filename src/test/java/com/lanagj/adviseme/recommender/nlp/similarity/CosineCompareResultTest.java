package com.lanagj.adviseme.recommender.nlp.similarity;

import com.lanagj.adviseme.recommender.nlp.weight.TfIdfDataProvider;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CosineCompareResultTest {

    CosineSimilarity cosineSimilarityService;

    @BeforeEach
    void setUp() {
        this.cosineSimilarityService = new CosineSimilarity();
    }

    @ParameterizedTest
    @ArgumentsSource(TfIdfDataProvider.class)
    void findSimilarity(List<List<DocumentStats>> input) {

        assertEquals(3, input.size());  // 3 - number of documents

        Double similarityDoc1Doc2 = this.cosineSimilarityService.findSimilarity(input.get(0), input.get(1), new double[0]);
        Double similarityDoc1Doc3 = this.cosineSimilarityService.findSimilarity(input.get(0), input.get(2), new double[0]);
        Double similarityDoc2Doc3 = this.cosineSimilarityService.findSimilarity(input.get(1), input.get(2), new double[0]);


        Double similarityDoc2Doc1 = this.cosineSimilarityService.findSimilarity(input.get(1), input.get(0), new double[0]);
        Double similarityDoc3Doc1 = this.cosineSimilarityService.findSimilarity(input.get(2), input.get(0), new double[0]);
        Double similarityDoc3Doc2 = this.cosineSimilarityService.findSimilarity(input.get(2), input.get(1), new double[0]);

        assertTrue(similarityDoc1Doc2 < similarityDoc1Doc3);
        assertTrue(similarityDoc1Doc2 > similarityDoc2Doc3);
        assertTrue(similarityDoc1Doc3 > similarityDoc2Doc3);

        assertEquals(similarityDoc1Doc2, similarityDoc2Doc1);
        assertEquals(similarityDoc1Doc3, similarityDoc3Doc1);
        assertEquals(similarityDoc2Doc3, similarityDoc3Doc2);
    }
}