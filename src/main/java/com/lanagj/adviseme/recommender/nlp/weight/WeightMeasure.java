package com.lanagj.adviseme.recommender.nlp.weight;

import java.util.List;
import java.util.Map;

public interface WeightMeasure<T> {

    List<MatrixCell> calculateWeight(Map<String, List<T>> bagOfWords);
}
