package com.lanagj.adviseme.recommender.nlp.lsa.weight.bag_of_words;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BagOfWordsTest {

    BagOfWords service;

    @BeforeEach
    void setUp() {

        this.service = new BagOfWords();
    }

    @Test
    void get() {

        Map<Long, List<String>> documentWordsMap = new HashMap<>();
        documentWordsMap.put(1L, Arrays.asList("word1", "word2", "word3", "word2"));
        documentWordsMap.put(2L, Arrays.asList("word1", "word3", "word4", "word5"));
        documentWordsMap.put(3L, Arrays.asList("word2"));

        Map<String, List<BagOfWords.WordFrequency>> actual = this.service.get(documentWordsMap);

        assertAll("Check results of conversion",
                () -> assertEquals(5, actual.size()),       // 5 unique words
                () -> assertEquals(3, actual.get("word1").size()), // 3 values for each of 3 documents
                () -> assertEquals(3, actual.get("word2").size()),
                () -> assertEquals(3, actual.get("word3").size()),
                () -> assertEquals(3, actual.get("word4").size()),
                () -> assertEquals(3, actual.get("word5").size()),

                // in document with ID '1L' we have 1 occurrence of 'word1`
                () -> assertTrue(actual.get("word1").stream().filter(wf -> wf.getDocumentId().equals(1L)).allMatch(wf -> wf.getNumOfOccurrences() == 1)),

                () -> assertTrue(actual.get("word2").stream().filter(wf -> wf.getDocumentId().equals(1L)).allMatch(wf -> wf.getNumOfOccurrences() == 2)),

                () -> assertTrue(actual.get("word2").stream().filter(wf -> wf.getDocumentId().equals(3L)).allMatch(wf -> wf.getNumOfOccurrences() == 1))
        );
    }
}