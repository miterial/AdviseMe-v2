package com.lanagj.adviseme.lsa.weight;

import com.lanagj.adviseme.lsa.weight.bag_of_words.BagOfWords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TfIdfTest {

    TfIdf service;

    @BeforeEach
    void setUp() {
        this.service = new TfIdf();
    }

    @Test
    void calculateWeight() {
        BagOfWords bagOfWordsService = new BagOfWords();
        Map<Long, List<String>> documentWordsMap = new HashMap<>();
        documentWordsMap.put(1L, Arrays.asList("word1", "word2", "word3", "word2"));
        documentWordsMap.put(2L, Arrays.asList("word1", "word3", "word4", "word5"));
        documentWordsMap.put(3L, Arrays.asList("word2"));

        Map<Long, List<BagOfWords.WordFrequency>> input = bagOfWordsService.get(documentWordsMap);

        List<WordDocument> actual = this.service.calculateWeight(input);

        assertEquals(documentWordsMap.size(), actual.size());

        // check tf-idf for each document
        WordDocument actual_1L = actual.stream().filter(wd -> wd.getDocumentId() == 1L).findFirst().get();
        assertEquals(input.get(1L).size(), actual_1L.getValues().size());
        assertEquals((double)1/3*Math.log((double)3/2), actual_1L.getValues().get("word1"));
        assertEquals((double)2/3*Math.log((double)3/2), actual_1L.getValues().get("word2"));

        WordDocument actual_2L = actual.stream().filter(wd -> wd.getDocumentId() == 2L).findFirst().get();
        assertEquals(input.get(2L).size(), actual_2L.getValues().size());
        assertEquals((double)1/4*Math.log((double)3/2), actual_2L.getValues().get("word1"));
        assertNull(actual_2L.getValues().get("word2"));

        WordDocument actual_3L = actual.stream().filter(wd -> wd.getDocumentId() == 3L).findFirst().get();
        assertEquals(input.get(3L).size(), actual_3L.getValues().size());
        assertEquals((double)1/1*Math.log((double)3/2), actual_3L.getValues().get("word2"));
    }
}