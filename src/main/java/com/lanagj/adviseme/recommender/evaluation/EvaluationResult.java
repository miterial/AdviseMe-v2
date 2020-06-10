package com.lanagj.adviseme.recommender.evaluation;

import lombok.Data;

@Data
public class EvaluationResult {

    Double lsa;
    Double mlsa;
    Double ngram;

    public EvaluationResult(Double f1Score_lsa, Double f1Score_mlsa, Double f1Score_ngram) {
        this.lsa = f1Score_lsa;
        this.mlsa = f1Score_mlsa;
        this.ngram = f1Score_ngram;
    }
}
