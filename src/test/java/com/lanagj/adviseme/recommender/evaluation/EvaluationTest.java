package com.lanagj.adviseme.recommender.evaluation;

import com.lanagj.adviseme.AdviseMeApplicationTests;
import com.lanagj.adviseme.data_import.tmdb.TmdbImportService;
import com.lanagj.adviseme.entity.movie.Movie;
import com.lanagj.adviseme.recommender.nlp.lsa.MovieDataProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationTest extends AdviseMeApplicationTests {

    @Autowired
    Evaluation evaluation;

    @Autowired
    TmdbImportService tmdbImportService;

    @BeforeEach
    void setUp() {
        //this.mongoTemplate.insertAll(MovieDataProvider.provideArguments());
    }

    @AfterEach
    void tearDown() {

        //this.mongoTemplate.remove(new Query(), Movie.class);
    }

    @Test
    void mlsaDifference() {

        this.tmdbImportService.importMovies(5, 2000, 2017);

        assertDoesNotThrow(() -> this.evaluation.mlsaDifference());
    }
}