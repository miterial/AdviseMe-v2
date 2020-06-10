package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.entity.similarity.CompareResultRepository;
import com.lanagj.adviseme.recommender.nlp.NaturalLanguageProcessing;
import com.lanagj.adviseme.recommender.nlp.lsa.svd.SVD;
import com.lanagj.adviseme.recommender.nlp.similarity.CosineSimilarity;
import com.lanagj.adviseme.recommender.nlp.similarity.ModifiedCosineSimilarity;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.WeightMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.BagOfWords;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LatentSemanticAnalysis extends NaturalLanguageProcessing {

    SVD svdService;

    ThreadPoolTaskExecutor threadPoolExecutor;

    private final CosineSimilarity cosineSimilarity;
    private final ModifiedCosineSimilarity modifiedCosineSimilarity;

    protected LatentSemanticAnalysis(MovieToNLPConverter movieToNlpConverter, DocumentStatsToArrayConverter weightStructureConverter, BagOfWords wordOccurrenceMatrix, WeightMeasure weightMeasureService, @Qualifier("lsaCalculationsThreadPool") ThreadPoolTaskExecutor threadPoolExecutor, CosineSimilarity cosineSimilarity, ModifiedCosineSimilarity modifiedCosineSimilarity, CompareResultRepository compareResultRepository) {

        super(movieToNlpConverter, weightStructureConverter, wordOccurrenceMatrix, weightMeasureService, compareResultRepository);
        this.threadPoolExecutor = threadPoolExecutor;
        this.cosineSimilarity = cosineSimilarity;
        this.modifiedCosineSimilarity = modifiedCosineSimilarity;
        this.svdService = new SVD();
    }

    @Async("algorithmsThreadPool")
    @Override
    public CompletableFuture<Set<CompareResult>> run() {

        if(stemmedWords.isEmpty()) {
            this.init(new HashSet<>());
        }

        AtomicLong timer = new AtomicLong(new Date().getTime());

        Map<String, List<BagOfWords.WordFrequency>> bagOfWords = wordOccurrenceMatrix.get(stemmedWords);

        stemmedWords.clear();

        // calculate tf-idf matrix for words-documents
        List<DocumentStats> wordDocumentMatrix = this.weightMeasureService.calculateWeight(bagOfWords);

        bagOfWords.clear();

        // sort by docID
        wordDocumentMatrix.sort(Comparator.comparingInt(DocumentStats::getDocumentId));

        // group by word => create word vectors
        Map<String, List<DocumentStats>> groupByWord = wordDocumentMatrix.stream().collect(Collectors.groupingBy(DocumentStats::getWord));

        log.info("Step1 -- " + (new Date().getTime() - timer.get()));
        timer.set(new Date().getTime());
        // perform svd

        double[][] tfIdfArray = this.weightStructureConverter.convert(groupByWord);

        int rank = Math.min(tfIdfArray[0].length, 55); // rank cannot be bigger than amount of documents
        rank = Math.min(rank, tfIdfArray.length);

        double[][] solved = svdService.solve(tfIdfArray, rank);

        log.info("Step2 -- " + (new Date().getTime() - timer.get()));
        timer.set(new Date().getTime());

        // convert again to get values for corresponding documents
        List<DocumentStats> wordVector;
        List<List<DocumentStats>> matrix = new ArrayList<>(groupByWord.values());
        for (int i = 0; i < groupByWord.size(); i++) {    // word vector
            wordVector = matrix.get(i);
            for (int j = 0; j < wordVector.size(); j++) {
                wordVector.get(j).setValue(solved[i][j]);
            }
        }

        groupByWord.clear();

        // calculate similarity matrix with cosine measure
        Map<Integer, List<DocumentStats>> groupByDocument = matrix.stream().flatMap(List::stream).collect(Collectors.groupingBy(DocumentStats::getDocumentId));
        ArrayList<List<DocumentStats>> documents = new ArrayList<>(groupByDocument.values());

        matrix.clear();
        wordDocumentMatrix.clear();
        groupByDocument.clear();

        List<DocumentStats> documentVector1;
        List<CompletableFuture<List<CompareResultHelper>>> compareResult = new ArrayList<>();
        for (int i = 0; i < documents.size(); i++) {
            documentVector1 = documents.get(i);
            compareResult.add(this.getCompareResultsForDocument(documentVector1, documents, i));
        }
        Set<CompareResult> results = compareResult.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .map(crh -> new CompareResult(crh.getMovieId_1(), crh.getMovieId_2(), crh.getResult_lsa(), crh.getResult_mlsa(), null))
                .collect(Collectors.toSet());

        log.info("Step3 -- " + (new Date().getTime() - timer.get()));

        compareResult.clear();

        return CompletableFuture.completedFuture(results);
    }

    private CompletableFuture<List<CompareResultHelper>> getCompareResultsForDocument(List<DocumentStats> documentVector1, ArrayList<List<DocumentStats>> documents, int i) {

        //log.debug("document iteration " + i);

        return CompletableFuture.supplyAsync(() -> {
            List<CompareResultHelper> result = new ArrayList<>();
            List<DocumentStats> documentVector2;
            Integer documentId2;
            Integer documentId1 = documentVector1.get(0).getDocumentId();

            //double[] presenceVector = new double[presenceMatrix.length];

            for (int j = 0; j < documents.size(); j++) {
                if (i != j) {
                    documentVector2 = documents.get(j);
                    documentId2 = documentVector2.get(0).getDocumentId();

                    Double simLsa = this.cosineSimilarity.findSimilarity(documentVector1, documentVector2);
                    Double simMlsa = this.modifiedCosineSimilarity.findSimilarity(documentVector1, documentVector2);

                    result.add(new CompareResultHelper(documentId1, documentId2, simLsa, simMlsa));
                }
            }
            return result;
        }, threadPoolExecutor);
    }
}
