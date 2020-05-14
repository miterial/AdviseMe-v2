package com.lanagj.adviseme.recommender.nlp;

import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.WeightMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.WordOccurrenceMatrix;
import com.lanagj.adviseme.recommender.nlp.similarity.SimilarityMeasure;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class NaturalRanguageProcessing {

    MovieToNLPConverter movieToNlpConverter;

    DocumentStatsToArrayConverter weightStructureConverter;

    WordOccurrenceMatrix wordOccurrenceMatrix;
    WeightMeasure weightMeasureService;
    SimilarityMeasure similarityMeasureService;

    protected NaturalRanguageProcessing(MovieToNLPConverter movieToNlpConverter, DocumentStatsToArrayConverter weightStructureConverter, WordOccurrenceMatrix wordOccurrenceMatrix, WeightMeasure weightMeasureService, SimilarityMeasure similarityMeasureService) {

        this.movieToNlpConverter = movieToNlpConverter;
        this.weightStructureConverter = weightStructureConverter;
        this.wordOccurrenceMatrix = wordOccurrenceMatrix;
        this.weightMeasureService = weightMeasureService;
        this.similarityMeasureService = similarityMeasureService;
    }

    public abstract Set<CompareResult> run();
}
