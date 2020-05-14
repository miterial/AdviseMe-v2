package com.lanagj.adviseme.recommender.nlp.similarity;

import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SimilarityMeasure {

    Double findSimilarity(List<DocumentStats> documentVector1, List<DocumentStats> documentVector2);
}
