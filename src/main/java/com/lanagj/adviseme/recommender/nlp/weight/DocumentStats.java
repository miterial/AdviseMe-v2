package com.lanagj.adviseme.recommender.nlp.weight;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Describes the cell of the word-document matrix
 */
@Getter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentStats extends MatrixCell {

    String word;

    Integer documentId;

    public DocumentStats(String word, Integer documentId, Double value) {

        super(value);
        this.word = word;
        this.documentId = documentId;
    }
}
