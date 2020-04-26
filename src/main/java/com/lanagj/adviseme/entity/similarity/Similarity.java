package com.lanagj.adviseme.entity.similarity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Similarity that = (Similarity) o;
        return (Objects.equals(documentId1, that.documentId1) &&
                Objects.equals(documentId2, that.documentId2)) ||
                (Objects.equals(documentId1, that.documentId2) &&
                Objects.equals(documentId2, that.documentId1)) &&
                Objects.equals(similarity, that.similarity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(similarity);
    }
}