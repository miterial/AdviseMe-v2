package com.lanagj.adviseme.recommender.nlp.esa;

import com.lanagj.adviseme.data_import.wiki.WikipediaDataprovider;
import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.recommender.nlp.NaturalRanguageProcessing;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.TfIdf;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.BagOfWords;
import com.lanagj.adviseme.recommender.nlp.similarity.CosineSimilarity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExplicitSemanticAnalysis extends NaturalRanguageProcessing {

    WikipediaDataprovider wikipediaDataProvider;

    public ExplicitSemanticAnalysis(MovieToNLPConverter movieToNLPConverter, DocumentStatsToArrayConverter tfIdfStructureConverter, WikipediaDataprovider wikipediaDataProvider) {

        super(movieToNLPConverter, tfIdfStructureConverter,
                new BagOfWords(), new TfIdf(), new CosineSimilarity());
        this.wikipediaDataProvider = wikipediaDataProvider;
    }

    //todo: make async
    @Override
    public Set<CompareResult> run() {

        return null;
    }
}