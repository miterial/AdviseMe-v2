package com.lanagj.adviseme.recommender.nlp.similarity;

import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;

import java.util.List;

public interface SimilarityMeasure {

    Double findSimilarity(List<DocumentStats> documentVector1, List<DocumentStats> documentVector2);
}
