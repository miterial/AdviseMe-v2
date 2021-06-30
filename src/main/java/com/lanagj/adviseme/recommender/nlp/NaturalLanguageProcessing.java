package com.lanagj.adviseme.recommender.nlp;

import com.lanagj.adviseme.entity.movies.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.entity.similarity.CompareResultRepository;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.WeightMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.WordOccurrenceMatrix;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class NaturalLanguageProcessing {

    MovieToNLPConverter movieToNlpConverter;

    DocumentStatsToArrayConverter weightStructureConverter;

    WordOccurrenceMatrix wordOccurrenceMatrix;
    WeightMeasure weightMeasureService;

    CompareResultRepository compareResultRepository;

    Map<Integer, List<String>> stemmedWords;

    protected NaturalLanguageProcessing(MovieToNLPConverter movieToNlpConverter, DocumentStatsToArrayConverter weightStructureConverter, WordOccurrenceMatrix wordOccurrenceMatrix, WeightMeasure weightMeasureService, CompareResultRepository compareResultRepository) {

        this.movieToNlpConverter = movieToNlpConverter;
        this.weightStructureConverter = weightStructureConverter;
        this.wordOccurrenceMatrix = wordOccurrenceMatrix;
        this.weightMeasureService = weightMeasureService;
        this.compareResultRepository = compareResultRepository;
    }

    public abstract CompletableFuture<Set<CompareResult>> run();

    public void init(Set<Integer> tmdbIds) {
        if(this.compareResultRepository.count() == 0L) {
            // get preprocessed words
            // key - TMDB ID, value - list of all words (with duplicates)
            if(tmdbIds.isEmpty()) {
                stemmedWords = this.movieToNlpConverter.transform();
            } else {
                stemmedWords = this.movieToNlpConverter.transform(tmdbIds);
            }
        }
    }
}
