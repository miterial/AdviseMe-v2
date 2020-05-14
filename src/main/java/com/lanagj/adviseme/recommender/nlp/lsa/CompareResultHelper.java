package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.entity.similarity.CompareResult;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Data
public class CompareResultHelper {

    private final Integer movieId_1;
    private final Integer movieId_2;
    private final Double result_lsa;
}
