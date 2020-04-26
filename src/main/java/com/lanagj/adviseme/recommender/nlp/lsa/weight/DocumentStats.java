package com.lanagj.adviseme.recommender.nlp.lsa.weight;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * Describes the cell of the word-document matrix
 */
@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentStats {

    String word;

    Integer documentId;

    Double value;
}
