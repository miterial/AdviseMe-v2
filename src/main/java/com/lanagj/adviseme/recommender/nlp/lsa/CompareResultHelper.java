package com.lanagj.adviseme.recommender.nlp.lsa;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CompareResultHelper {

    private final Integer movieId_1;
    private final Integer movieId_2;
    private final Double result_lsa;
    private final Double result_mlsa;
}
