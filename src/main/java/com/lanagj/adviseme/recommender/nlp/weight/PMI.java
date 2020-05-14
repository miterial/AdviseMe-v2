package com.lanagj.adviseme.recommender.nlp.weight;

import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.WordToContextMatrix;

import java.util.List;
import java.util.Map;

public class PMI implements WeightMeasure<WordToContextMatrix.CooccurrenceFrequency> {

    @Override
    public List<MatrixCell> calculateWeight(Map<String, List<WordToContextMatrix.CooccurrenceFrequency>> bagOfWords) {

        // вероятность появления обоих слов = количество документов, в которых встречаются оба слова, делить на общее количество документов
        // вероятность появления одного слова = количество документов, в которых есть слово,  делить на общее количество документов


        return null;
    }
}
