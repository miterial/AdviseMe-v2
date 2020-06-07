package com.lanagj.adviseme.recommender.nlp;

import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.WeightMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.WordOccurrenceMatrix;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class NaturalRanguageProcessing {

    MovieToNLPConverter movieToNlpConverter;

    DocumentStatsToArrayConverter weightStructureConverter;

    WordOccurrenceMatrix wordOccurrenceMatrix;
    WeightMeasure weightMeasureService;

    protected NaturalRanguageProcessing(MovieToNLPConverter movieToNlpConverter, DocumentStatsToArrayConverter weightStructureConverter, WordOccurrenceMatrix wordOccurrenceMatrix, WeightMeasure weightMeasureService) {

        this.movieToNlpConverter = movieToNlpConverter;
        this.weightStructureConverter = weightStructureConverter;
        this.wordOccurrenceMatrix = wordOccurrenceMatrix;
        this.weightMeasureService = weightMeasureService;
    }

    public abstract CompletableFuture<Set<CompareResult>> run();
}
