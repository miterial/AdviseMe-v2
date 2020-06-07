package com.lanagj.adviseme.entity.similarity;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class CompareResultRepository {

    private final MongoTemplate mongoTemplate;

    public List<CompareResult> findByMovieIds(Set<Integer> movieId) {
        Query query = new Query(new Criteria().orOperator(
                Criteria.where("idPair.movieId1").in(movieId),
                Criteria.where("idPair.movieId2").in(movieId)
        ));

        return this.mongoTemplate.find(query, CompareResult.class);
    }

    public long count() {

        return this.mongoTemplate.count(new Query(), CompareResult.class);
    }

    public void saveAll(Set<CompareResult> compareResults) {
        this.mongoTemplate.insertAll(compareResults);

    }
}
