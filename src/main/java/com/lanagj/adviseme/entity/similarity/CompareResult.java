package com.lanagj.adviseme.entity.similarity;

import com.lanagj.adviseme.configuration.AlgorithmType;
import com.lanagj.adviseme.entity.Entity;
import com.sun.javafx.scene.traversal.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Document
@Getter
@ToString
@AllArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class CompareResult extends Entity {

    private final CompareId idPair;
    private final Map<AlgorithmType, Double> results;

    public CompareResult(Integer id1, Integer id2, Double result_lsa, Double result_mlsa, Double result_ngram) {

        this.idPair = new CompareId(id1, id2);

        this.results = new HashMap<>();
        this.results.put(AlgorithmType.LSA, result_lsa);
        this.results.put(AlgorithmType.MLSA, result_mlsa);
    }

    @AllArgsConstructor
    @Data
    public static class CompareId {
        private final Integer movieId1;
        private final Integer movieId2;

        @Override
        public String toString(){
            return movieId1 + " " + movieId2;
        }

        public boolean containsAny(List<Integer> movieIds) {

            return movieIds.contains(movieId1) && movieIds.contains(movieId2);
        }

        public CompareId switchIds() {

            return new CompareId(movieId2, movieId1);
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompareResult that = (CompareResult) o;
        return (Objects.equals(this.idPair.movieId1, that.idPair.movieId1) &&
                Objects.equals(this.idPair.movieId2, that.idPair.movieId2)) ||
                (Objects.equals(this.idPair.movieId1, that.idPair.movieId2) &&
                Objects.equals(this.idPair.movieId2, that.idPair.movieId1)) &&
                        Objects.equals(results.get(AlgorithmType.LSA), that.results.get(AlgorithmType.LSA)) &&
                        Objects.equals(results.get(AlgorithmType.MLSA), that.results.get(AlgorithmType.MLSA))&&
                        Objects.equals(results.get(AlgorithmType.NGRAM), that.results.get(AlgorithmType.NGRAM));
    }

    @Override
    public int hashCode() {

        return Objects.hash(results.get(AlgorithmType.LSA), results.get(AlgorithmType.MLSA), results.get(AlgorithmType.NGRAM));
    }
}