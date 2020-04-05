package com.lanagj.adviseme.similarity;

import com.lanagj.adviseme.lsa.weight.TfIdfDataProvider;
import com.lanagj.adviseme.lsa.weight.WordDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CosineSimilarityTest {

    CosineSimilarity cosineSimilarityService;

    @BeforeEach
    void setUp() {
        this.cosineSimilarityService = new CosineSimilarity();
    }

    @ParameterizedTest
    @ArgumentsSource(TfIdfDataProvider.class)
    void findSimilarity(List<WordDocument> input) {

        assertEquals(3, input.size());

        Double similarityDoc1Doc2 = this.cosineSimilarityService.findSimilarity(input.get(0), input.get(1));
        Double similarityDoc1Doc3 = this.cosineSimilarityService.findSimilarity(input.get(0), input.get(2));
        Double similarityDoc2Doc3 = this.cosineSimilarityService.findSimilarity(input.get(1), input.get(2));

        // todo: this was just an assumption, maybe check for sure
        assertTrue(similarityDoc1Doc2 < similarityDoc1Doc3);
        assertTrue(similarityDoc1Doc2 > similarityDoc2Doc3);
        assertTrue(similarityDoc1Doc3 > similarityDoc2Doc3);
    }
}