package com.lanagj.adviseme.entity.similarity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString
public class Similarity {

    private final Integer documentId1;
    private final Integer documentId2;
    private final Double similarity;

    public Similarity(Integer documentId1, Integer documentId2, Double similarity) {

        this.documentId1 = documentId1;
        this.documentId2 = documentId2;
        this.similarity = similarity;
    }
}