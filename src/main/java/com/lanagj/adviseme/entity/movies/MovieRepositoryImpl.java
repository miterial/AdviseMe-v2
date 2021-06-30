package com.lanagj.adviseme.entity.movies;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
class MovieRepositoryImpl implements MovieRepository {

    MongoTemplate mongoTemplate;

    public MovieRepositoryImpl(MongoTemplate mongoTemplate) {

        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Movie> findAllByTmdbIdIn(Set<Integer> tmdbIds) {

        return null;
    }

    @Override
    public List<Movie> getTopRated() {

        Query query = new Query();
        query.addCriteria(Criteria.where("voteCount").gte(200));
        query.with(Sort.by(Sort.Direction.DESC, "voteAverage"));

        return this.mongoTemplate.find(query, Movie.class);
    }

    @Override
    public void save(List<Movie> movies) {
        this.mongoTemplate.insertAll(movies);
    }

    @Override
    public List<Movie> findAllById(Set<String> movieIds) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(movieIds));
        return this.mongoTemplate.find(query, Movie.class);
    }

    @Override
    public List<Movie> findAll() {
        return this.mongoTemplate.find(new Query(), Movie.class);
    }
}
