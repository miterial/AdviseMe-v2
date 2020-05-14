package com.lanagj.adviseme.entity.similarity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
@Getter
@ToString
public class CompareResult {

    private final CompareId id_pair;
    private final Double result_lsa;
    private final Double result_esa;
    private final Double result_ngram;

    public CompareResult(Integer id1, Integer id2, Double result_lsa, Double result_esa, Double result_ngram) {

        this.id_pair = new CompareId(id1, id2);
        this.result_lsa = result_lsa;
        this.result_esa = result_esa;
        this.result_ngram = result_ngram;
    }

    @AllArgsConstructor
    @Data
    public static class CompareId {
        private final Integer movieId_1;
        private final Integer movieId_2;

        @Override
        public String toString(){
            return movieId_1 + " " + movieId_2;
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompareResult that = (CompareResult) o;
        return (Objects.equals(this.id_pair.movieId_1, that.id_pair.movieId_1) &&
                Objects.equals(this.id_pair.movieId_2, that.id_pair.movieId_2)) ||
                (Objects.equals(this.id_pair.movieId_1, that.id_pair.movieId_2) &&
                Objects.equals(this.id_pair.movieId_2, that.id_pair.movieId_1)) &&
                        Objects.equals(result_lsa, that.result_lsa) &&
                        Objects.equals(result_esa, that.result_esa)&&
                        Objects.equals(result_ngram, that.result_ngram);
    }

    @Override
    public int hashCode() {

        return Objects.hash(result_lsa, result_esa, result_ngram);
    }
}