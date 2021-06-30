package com.lanagj.adviseme.data_import.tmdb;

import com.lanagj.adviseme.AdviseMeApplicationTests;
import com.lanagj.adviseme.entity.movies.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class TmdbImportServiceTest extends AdviseMeApplicationTests {

    @Autowired
    TmdbImportService tmdbImportService;

    @AfterEach
    void tearDown() {
        this.mongoTemplate.remove(new Query(), Movie.class);
    }

    @Disabled("Changing structure")
    @Test
    void importMovies() {

        //this.tmdbImportService.importMovies(1, 1, 100);

        List<Movie> movies = this.mongoTemplate.find(new Query(), Movie.class);
        assertEquals(100, movies.size());
    }
}