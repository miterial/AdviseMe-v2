package com.lanagj.adviseme.recommender.evaluation;

import lombok.Data;
import lombok.Getter;

@Data
public class EvaluationResult {

    Double lsa;
    Double mlsa;

    public EvaluationResult(Double f1Score_lsa, Double f1Score_mlsa) {
        this.lsa = f1Score_lsa;
        this.mlsa = f1Score_mlsa;
    }
}
