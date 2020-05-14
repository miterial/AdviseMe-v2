package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.recommender.nlp.NaturalRanguageProcessing;
import com.lanagj.adviseme.recommender.nlp.lsa.svd.SVD;
import com.lanagj.adviseme.recommender.nlp.similarity.SimilarityMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.WeightMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.BagOfWords;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.WordOccurrenceMatrix;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class LatentSemanticAnalysis extends NaturalRanguageProcessing {

    SVD svdService;

    protected LatentSemanticAnalysis(MovieToNLPConverter movieToNlpConverter, DocumentStatsToArrayConverter weightStructureConverter, WordOccurrenceMatrix wordOccurrenceMatrix, WeightMeasure weightMeasureService, SimilarityMeasure similarityMeasureService) {

        super(movieToNlpConverter, weightStructureConverter, wordOccurrenceMatrix, weightMeasureService, similarityMeasureService);
        this.svdService = new SVD();
    }

    //todo: make async
    @Override
    public Set<CompareResult> run() {

        AtomicLong timer = new AtomicLong(new Date().getTime());

        // get preprocessed words
        Map<Long, List<String>> stemmedWords = this.movieToNlpConverter.transform();

        Map<String, List<BagOfWords.WordFrequency>> bagOfWords = wordOccurrenceMatrix.get(stemmedWords);

        // calculate tf-idf matrix for words-documents
        List<DocumentStats> wordDocumentMatrix = this.weightMeasureService.calculateWeight(bagOfWords);

        // sort by docID
        wordDocumentMatrix.sort(Comparator.comparingInt(DocumentStats::getDocumentId));

        // group by word => create word vectors
        Map<String, List<DocumentStats>> groupByWord = wordDocumentMatrix.stream().collect(Collectors.groupingBy(DocumentStats::getWord));

        System.out.println("Step1 -- " + (new Date().getTime() - timer.get()));
        timer.set(new Date().getTime());
        // perform svd

        double[][] tfIdfArray = this.weightStructureConverter.convert(groupByWord);

        int rank = Math.min(tfIdfArray[0].length / 2, 50); // rank cannot be bigger than amount of documents

        double[][] solved = svdService.solve(tfIdfArray, rank);

        System.out.println("Step2 -- " + (new Date().getTime() - timer.get()));
        timer.set(new Date().getTime());

        // convert again to get values for corresponding documents
        List<List<DocumentStats>> matrix = new ArrayList<>(groupByWord.values());
        for (int i = 0; i < groupByWord.size(); i++) {    // word vector
            List<DocumentStats> wordVector = matrix.get(i);
            for (int j = 0; j < wordVector.size(); j++) {
                wordVector.get(j).setValue(solved[i][j]);
            }
        }

        // calculate similarity matrix with cosine measure
        Map<Integer, List<DocumentStats>> groupByDocument = wordDocumentMatrix.stream().collect(Collectors.groupingBy(DocumentStats::getDocumentId));
        ArrayList<List<DocumentStats>> documents = new ArrayList<>(groupByDocument.values());

        List<DocumentStats> documentVector1;
        List<CompletableFuture<List<CompareResultHelper>>> compareResult = new ArrayList<>();
        for (int i = 0; i < documents.size(); i++) {
            documentVector1 = documents.get(i);
            compareResult.add(this.getCompareResultsForDocument(documentVector1, documents, i));
        }
        Set<CompareResult> results = compareResult.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .map(crh -> new CompareResult(crh.getMovieId_1(), crh.getMovieId_2(), crh.getResult_lsa(), null, null))
                .collect(Collectors.toSet());

        System.out.println("Step3 -- " + (new Date().getTime() - timer.get()));

        return results;
    }

    private CompletableFuture<List<CompareResultHelper>> getCompareResultsForDocument(List<DocumentStats> documentVector1, ArrayList<List<DocumentStats>> documents, int i) {

        return CompletableFuture.supplyAsync(() -> {
            List<CompareResultHelper> result = new ArrayList<>();
            List<DocumentStats> documentVector2;
            Integer documentId2;
            Integer documentId1 = documentVector1.get(0).getDocumentId();

            for (int j = 0; j < documents.size(); j++) {
                if (i != j) {
                    documentVector2 = documents.get(j);
                    documentId2 = documentVector2.get(0).getDocumentId();
                    Double sim = this.similarityMeasureService.findSimilarity(documentVector1, documentVector2);

                    result.add(new CompareResultHelper(documentId1, documentId2, sim));
                }
            }
            return result;
        });
    }
}
