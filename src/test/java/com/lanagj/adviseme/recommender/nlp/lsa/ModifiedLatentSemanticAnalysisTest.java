package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.AdviseMeApplicationTests;
import com.lanagj.adviseme.entity.movie.Movie;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModifiedLatentSemanticAnalysisTest extends AdviseMeApplicationTests {

    @Autowired
    ModifiedLatentSemanticAnalysis service;

    @BeforeEach
    void setUp() {
        this.mongoTemplate.insertAll(MovieDataProvider.provideArguments());
    }

    @AfterEach
    void tearDown() {
        this.mongoTemplate.remove(new Query(), Movie.class);
    }

    @Test
    void run() {

        Set<CompareResult> results = this.service.run();

        assertEquals(3, results.size());

    }
}