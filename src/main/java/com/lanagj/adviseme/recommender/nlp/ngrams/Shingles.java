package com.lanagj.adviseme.recommender.nlp.ngrams;

import com.lanagj.adviseme.configuration.AlgorithmType;
import com.lanagj.adviseme.entity.movies.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.entity.similarity.CompareResultRepository;
import com.lanagj.adviseme.recommender.nlp.NaturalLanguageProcessing;
import com.lanagj.adviseme.recommender.nlp.similarity.JaccardSimilarity;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.WeightMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.WordOccurrenceMatrix;
import ngrams.Ngrams;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class Shingles extends NaturalLanguageProcessing {

    private final int NGRAM_SIZE = 2;

    JaccardSimilarity jaccardSimilarity;

    protected Shingles(MovieToNLPConverter movieToNlpConverter, DocumentStatsToArrayConverter weightStructureConverter, WordOccurrenceMatrix wordOccurrenceMatrix, WeightMeasure weightMeasureService, CompareResultRepository compareResultRepository, JaccardSimilarity jaccardSimilarity) {

        super(movieToNlpConverter, weightStructureConverter, wordOccurrenceMatrix, weightMeasureService, compareResultRepository);
        this.jaccardSimilarity = jaccardSimilarity;
    }

    @Async("algorithmsThreadPool")
    @Override
    public CompletableFuture<Set<CompareResult>> run() {

        if (this.stemmedWords == null || stemmedWords.isEmpty()) {
            this.init(new HashSet<>());
        }

        Map<Integer, List<String>> documentsByNgrams = new HashMap<>();

        for (Map.Entry<Integer, List<String>> document : this.stemmedWords.entrySet()) {

            ArrayList<String> ngrams = Ngrams.ngrams(new ArrayList<>(document.getValue()), NGRAM_SIZE);
            documentsByNgrams.put(document.getKey(), ngrams);
        }
        this.stemmedWords.clear();

        Set<CompareResult> results = this.compareDocuments(documentsByNgrams);

        return CompletableFuture.completedFuture(results);
    }

    private Set<CompareResult> compareDocuments(Map<Integer, List<String>> documentsByNgrams) {

        Set<Map.Entry<Integer, List<String>>> entries = documentsByNgrams.entrySet();

        Map<CompareResult.CompareId, CompareResult> compareResults = new HashMap<>();

        for (Map.Entry<Integer, List<String>> entry1 : entries) {
            Integer documentId = entry1.getKey();
            for (Map.Entry<Integer, List<String>> entry2 : entries) {
                if (entry1 != entry2) {
                    CompareResult.CompareId compareId = new CompareResult.CompareId(documentId, entry2.getKey());
                    if (!compareResults.containsKey(compareId)) {
                        double sim = this.getSimilarity(entry1.getValue(), entry2.getValue());
                        Map<AlgorithmType, Double> result = new HashMap<>();
                        result.put(AlgorithmType.NGRAM, sim);
                        compareResults.put(compareId, new CompareResult(compareId, result));
                    }
                }
            }
        }

        return new HashSet<>(compareResults.values());
    }

    private double getSimilarity(final List<String> document1, final List<String> document2) {

        List<String> tempDoc1 = new ArrayList<>(document1);
        List<String> tempDoc2 = new ArrayList<>(document2);

        int all = tempDoc1.size() + tempDoc2.size();

        tempDoc1.retainAll(tempDoc2);

        int common = tempDoc1.size();

        return this.jaccardSimilarity.findSimilarity(common, all);
    }
}
