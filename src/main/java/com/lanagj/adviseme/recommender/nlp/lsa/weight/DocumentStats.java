package com.lanagj.adviseme.recommender.nlp.lsa.weight;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @Setter
    Double value;
}
