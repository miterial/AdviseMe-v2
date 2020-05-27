package com.lanagj.adviseme.recommender.evaluation;

import com.lanagj.adviseme.AdviseMeApplicationTests;
import com.lanagj.adviseme.converter.GeneralConverterService;
import com.lanagj.adviseme.data_import.tmdb.TmdbImportService;
import com.lanagj.adviseme.entity.movie.MovieRepository;
import com.lanagj.adviseme.recommender.nlp.lsa.MovieDataProvider;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class EvaluationTest extends AdviseMeApplicationTests {

    @Autowired
    Evaluation evaluation;

    @Autowired
    TmdbImportService tmdbImportService;
    @Autowired
    GeneralConverterService movieConverter;
    @Autowired
    MovieRepository movieRepository;

    @BeforeEach
    void setUp() {

        this.mongoTemplate.insertAll(MovieDataProvider.provideArguments());
    }

    @AfterEach
    void tearDown() {

        //this.mongoTemplate.remove(new Query(), Movie.class);
    }

    @Test
    void mlsaDifference() {


        List<Integer> movieIds = Arrays.asList(
                176403, 74643, 8329, 276624
                , 212162
                , 255962
                , 8386
                , 394568
                , 400642
                , 11547
        );
        List<BaseMovie> movies = this.tmdbImportService.getMovies(new HashSet<>(movieIds));
        List<com.lanagj.adviseme.entity.movie.Movie> movieEntities = this.movieConverter.convertList(movies, BaseMovie.class, com.lanagj.adviseme.entity.movie.Movie.class);
        this.movieRepository.saveAll(movieEntities);

        this.tmdbImportService.importMovies(3, 2000, 2017);

        this.evaluation.mlsaDifference();
    }
}