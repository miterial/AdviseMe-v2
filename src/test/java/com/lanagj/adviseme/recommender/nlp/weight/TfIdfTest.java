package com.lanagj.adviseme.recommender.nlp.weight;

import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.BagOfWords;
import com.lanagj.adviseme.recommender.nlp.weight.bag_of_words.BagOfWordsDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TfIdfTest {

    TfIdf service;

    @BeforeEach
    void setUp() {
        this.service = new TfIdf();
    }

    @ParameterizedTest
    @ArgumentsSource(BagOfWordsDataProvider.class)
    void calculateWeight(Map<String, List<BagOfWords.WordFrequency>> wordFrequency) {

        List<MatrixCell> actual = this.service.calculateWeight(wordFrequency);

        assertEquals(5 * 3, actual.size()); // 5 words * 3 documents = matrix of size 5x3

        // check tf-idf for each document
        List<MatrixCell> actual_1 = actual.stream().filter(wd -> ((DocumentStats)wd).getWord().equals("word1")).collect(Collectors.toList());
        assertEquals(wordFrequency.get("word1").size(), actual_1.size());
        assertEquals((double)1/3*Math.log((double)3/2), actual_1.get(0).getValue());   //doc1
        assertEquals((double)1/4*Math.log((double)3/2), actual_1.get(1).getValue());   //doc2
        assertEquals((double)0/1*Math.log((double)3/2), actual_1.get(2).getValue());   //doc3

        List<MatrixCell> actual_2 = actual.stream().filter(wd -> ((DocumentStats)wd).getWord().equals("word2")).collect(Collectors.toList());
        assertEquals(wordFrequency.get("word2").size(), actual_2.size());
        assertEquals((double)2/3*Math.log((double)3/2), actual_2.get(0).getValue());   //doc1
        assertEquals((double)0/4*Math.log((double)3/2), actual_2.get(1).getValue());   //doc2
        assertEquals((double)1/1*Math.log((double)3/2), actual_2.get(2).getValue());   //doc3

        List<MatrixCell> actual_3 = actual.stream().filter(wd -> ((DocumentStats)wd).getWord().equals("word3")).collect(Collectors.toList());
        assertEquals(wordFrequency.get("word3").size(), actual_3.size());
        assertEquals((double)1/3*Math.log((double)3/2), actual_3.get(0).getValue());   //doc1
        assertEquals((double)1/4*Math.log((double)3/2), actual_3.get(1).getValue());   //doc2
        assertEquals((double)0/1*Math.log((double)3/2), actual_3.get(2).getValue());   //doc3

        List<MatrixCell> actual_4 = actual.stream().filter(wd -> ((DocumentStats)wd).getWord().equals("word4")).collect(Collectors.toList());
        assertEquals(wordFrequency.get("word4").size(), actual_4.size());
        assertEquals((double)0/3*Math.log((double)3/1), actual_4.get(0).getValue());   //doc1
        assertEquals((double)1/4*Math.log((double)3/1), actual_4.get(1).getValue());   //doc2
        assertEquals((double)0/1*Math.log((double)3/1), actual_4.get(2).getValue());   //doc3

        List<MatrixCell> actual_5 = actual.stream().filter(wd -> ((DocumentStats)wd).getWord().equals("word5")).collect(Collectors.toList());
        assertEquals(wordFrequency.get("word5").size(), actual_5.size());
        assertEquals((double)0/3*Math.log((double)3/1), actual_5.get(0).getValue());   //doc1
        assertEquals((double)1/4*Math.log((double)3/1), actual_5.get(1).getValue());   //doc2
        assertEquals((double)0/1*Math.log((double)3/1), actual_5.get(2).getValue());   //doc3
    }
}