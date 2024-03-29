package com.lanagj.adviseme.entity.movies;

import com.lanagj.adviseme.recommender.nlp.preprocessing.PorterStemming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieToNLPConverter {

    MovieRepository movieRepository;
    PorterStemming porterStemmingService;

    /**
     * Converts movie descriptions from DB
     *
     * @return map of document IDs and preprocessed words that this document contains;
     *         lists of words can contain duplicates; this will be used to calculate word frequency
     */
    public Map<Integer, List<String>> transform() {

        Map<Integer, List<String>> result = new HashMap<>();

        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            List<String> words = this.porterStemmingService.process(movie.getOverview());
            result.put(movie.getTmdbId(), words);
        }

        return result;

    }

    public Map<Integer, List<String>> transform(Set<Integer> tmdbIds) {

        Map<Integer, List<String>> result = new HashMap<>();

        List<Movie> movies = movieRepository.findAllByTmdbIdIn(tmdbIds);
        for (Movie movie : movies) {
            List<String> words = this.porterStemmingService.process(movie.getOverview());
            result.put(movie.getTmdbId(), words);
        }

        return result;

    }

}
