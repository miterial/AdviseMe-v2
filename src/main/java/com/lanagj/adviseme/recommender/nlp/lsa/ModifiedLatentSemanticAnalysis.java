package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.recommender.nlp.NaturalRanguageProcessing;
import com.lanagj.adviseme.recommender.nlp.lsa.svd.SVD;
import com.lanagj.adviseme.recommender.nlp.similarity.ModifiedCosineSimilarity;
import com.lanagj.adviseme.recommender.nlp.similarity.SimilarityMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.TfIdf;
import com.lanagj.adviseme.recommender.nlp.weight.WeightMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.BagOfWords;
import com.lanagj.adviseme.recommender.nlp.similarity.CosineSimilarity;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.WordOccurrenceMatrix;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ModifiedLatentSemanticAnalysis extends LatentSemanticAnalysis {

    protected ModifiedLatentSemanticAnalysis(MovieToNLPConverter movieToNlpConverter, DocumentStatsToArrayConverter weightStructureConverter, WordOccurrenceMatrix wordOccurrenceMatrix, WeightMeasure weightMeasureService, ModifiedCosineSimilarity similarityMeasureService) {

        super(movieToNlpConverter, weightStructureConverter, wordOccurrenceMatrix, weightMeasureService, similarityMeasureService);
    }
}
