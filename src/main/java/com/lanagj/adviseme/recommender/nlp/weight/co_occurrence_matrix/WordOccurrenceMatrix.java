package com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix;

import java.util.List;
import java.util.Map;

public interface WordOccurrenceMatrix<T> {

    /**
     *
     * @param documentContextMatrix key - document ID, value - list of words from document
     * @return key - word, value - calculated word frequency
     */
    Map<String, List<T>> get(Map<Integer, List<String>> documentContextMatrix);
}
