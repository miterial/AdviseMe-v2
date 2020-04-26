package com.lanagj.adviseme.entity.movie;

import com.lanagj.adviseme.recommender.nlp.preprocessing.PorterStemming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieToNLPConverter {

    MovieRepository movieRepository;
    PorterStemming porterStemmingService;

    /**
     * Converts movie descriptions from DB
     *
     * @return map of document IDs and preprocessed words that this document contains
     */
    public Map<Long, List<String>> convert() {

        Map<Long, List<String>> result = new HashMap<>();

        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            List<String> words = new ArrayList<>();

            for (String s : movie.getOverview().split("[\\p{Punct}\\s]+")) {
                words.add(this.porterStemmingService.process(s));
            }

            result.put(Long.valueOf(movie.getTmdbId()), words);

        }

        return result;

    }

}
