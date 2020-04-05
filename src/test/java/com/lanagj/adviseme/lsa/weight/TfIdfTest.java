package com.lanagj.adviseme.lsa.weight;

import com.lanagj.adviseme.lsa.weight.bag_of_words.BagOfWords;
import com.lanagj.adviseme.lsa.weight.bag_of_words.BagOfWordsDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

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

    @ParameterizedTest
    @ArgumentsSource(BagOfWordsDataProvider.class)
    void calculateWeight(Map<Long, List<BagOfWords.WordFrequency>> wordFrequency) {

        List<WordDocument> actual = this.service.calculateWeight(wordFrequency);

        assertEquals(wordFrequency.size(), actual.size());

        // check tf-idf for each document
        WordDocument actual_1L = actual.stream().filter(wd -> wd.getDocumentId() == 1L).findFirst().get();
        assertEquals(wordFrequency.get(1L).size(), actual_1L.getValues().size());
        assertEquals((double)1/3*Math.log((double)3/2), actual_1L.getValues().get("word1"));
        assertEquals((double)2/3*Math.log((double)3/2), actual_1L.getValues().get("word2"));

        WordDocument actual_2L = actual.stream().filter(wd -> wd.getDocumentId() == 2L).findFirst().get();
        assertEquals(wordFrequency.get(2L).size(), actual_2L.getValues().size());
        assertEquals((double)1/4*Math.log((double)3/2), actual_2L.getValues().get("word1"));
        assertNull(actual_2L.getValues().get("word2"));

        WordDocument actual_3L = actual.stream().filter(wd -> wd.getDocumentId() == 3L).findFirst().get();
        assertEquals(wordFrequency.get(3L).size(), actual_3L.getValues().size());
        assertEquals((double)1/1*Math.log((double)3/2), actual_3L.getValues().get("word2"));
    }
}