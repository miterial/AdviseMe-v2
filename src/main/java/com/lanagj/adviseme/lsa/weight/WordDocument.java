package com.lanagj.adviseme.lsa.weight;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Column of the word-document (context) matrix
 */
@AllArgsConstructor
public class WordDocument {

    /**
     * ID of the document in the collection
     */
    Long documentId;
    /**
     * Weighted values of the word-document matrix
     * key - word, value - tf-idf
     */
    Map<String, Double> values;

    public Long getDocumentId() {

        return documentId;
    }

    public Map<String, Double> getValues() {

        return values;
    }
}
