package com.lanagj.adviseme.lsa.weight.bag_of_words;

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

        Map<Long, List<BagOfWords.WordFrequency>> actual = this.service.get(documentWordsMap);

        assertAll("Check results of conversion",
                () -> assertEquals(2, actual.size()),
                () -> assertEquals(3, actual.get(1L).size()),
                () -> assertEquals(4, actual.get(2L).size()),

                () -> assertTrue(actual.get(1L).stream().filter(wf -> wf.getWord().equals("word2")).allMatch(wf -> wf.getNumOfOccurrences() == 2)),

                () -> assertTrue(actual.get(2L).stream().allMatch(wf -> wf.getNumOfOccurrences() == 1))
        );
    }
}