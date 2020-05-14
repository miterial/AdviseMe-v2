package com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix;

import com.lanagj.adviseme.AdviseMeApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WordToContextMatrixTest extends AdviseMeApplicationTests {

    WordToContextMatrix service;

    @BeforeEach
    void setUp() {
        this.service = new WordToContextMatrix();

    }

    @Test
    void get() {

        Map<Long, List<String>> documentWordsMap = new HashMap<>();
        documentWordsMap.put(1L, Arrays.asList("word1", "word2", "word3", "word6", "word7", "word1", "word8", "word2", "word2"));
        documentWordsMap.put(2L, Arrays.asList("word1", "word3", "word4", "word5", "word7", "word1", "word1"));
        documentWordsMap.put(3L, Arrays.asList("word2", "word1", "word9", "word7", "word1", "word10", "word11", "word12"));

        List<WordToContextMatrix.CooccurrenceFrequency> actual = this.service.get(documentWordsMap);

    }
}