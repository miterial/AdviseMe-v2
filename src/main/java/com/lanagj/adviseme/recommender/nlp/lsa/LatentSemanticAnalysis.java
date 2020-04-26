package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.recommender.nlp.lsa.svd.SVD;
import com.lanagj.adviseme.recommender.nlp.lsa.weight.TfIdf;
import com.lanagj.adviseme.recommender.nlp.lsa.weight.DocumentStats;
import com.lanagj.adviseme.recommender.nlp.lsa.weight.TfIdfToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.lsa.weight.bag_of_words.BagOfWords;
import com.lanagj.adviseme.recommender.nlp.similarity.CosineSimilarity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LatentSemanticAnalysis implements NaturalRanguageProcessing {

    MovieToNLPConverter movieToNlpConverter;

    TfIdfToArrayConverter tfIdfToArrayConverter;

    BagOfWords bagOfWordsService;
    TfIdf tfIdfService;
    SVD svdService;
    CosineSimilarity cosineSimilarityService;

    @Override
    public void run() {

        // get preprocessed words
        Map<Long, List<String>> stemmedWords = this.movieToNlpConverter.convert();

        Map<String, List<BagOfWords.WordFrequency>> bagOfWords = bagOfWordsService.get(stemmedWords);

        // calculate tf-idf matrix for words-documents
        List<DocumentStats> wordDocumentMatrix = this.tfIdfService.calculateWeight(bagOfWords);

        // perform svd
        double[][] tfIdfArray = this.tfIdfToArrayConverter.convert(wordDocumentMatrix);
        svdService.solve(tfIdfArray, 300);

        // calculate similarity matrix with cosine measure

    }
}
