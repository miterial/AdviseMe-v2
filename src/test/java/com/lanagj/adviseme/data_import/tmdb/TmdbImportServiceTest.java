package com.lanagj.adviseme.data_import.tmdb;

import com.lanagj.adviseme.AdviseMeApplicationTests;
import com.lanagj.adviseme.entity.movie.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
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

    @Test
    void importMovies() {

        this.tmdbImportService.importMovies(1, 2000, 2010);

        List<Movie> movies = this.mongoTemplate.find(new Query(), Movie.class);
        assertEquals(101, movies.size());
    }
}