package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.recommender.nlp.NaturalRanguageProcessing;
import com.lanagj.adviseme.recommender.nlp.lsa.svd.SVD;
import com.lanagj.adviseme.recommender.nlp.similarity.ModifiedCosineSimilarity;
import com.lanagj.adviseme.recommender.nlp.similarity.SimilarityMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStats;
import com.lanagj.adviseme.recommender.nlp.weight.DocumentStatsToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.weight.TfIdf;
import com.lanagj.adviseme.recommender.nlp.weight.WeightMeasure;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.BagOfWords;
import com.lanagj.adviseme.recommender.nlp.similarity.CosineSimilarity;
import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.WordOccurrenceMatrix;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
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

        // get preprocessed words
        Map<Long, List<String>> stemmedWords = this.movieToNlpConverter.transform();

        Map<String, List<BagOfWords.WordFrequency>> bagOfWords = wordOccurrenceMatrix.get(stemmedWords);

        // calculate tf-idf matrix for words-documents
        List<DocumentStats> wordDocumentMatrix = this.weightMeasureService.calculateWeight(bagOfWords);

        // sort by docID
        wordDocumentMatrix.sort(Comparator.comparingInt(DocumentStats::getDocumentId));

        // group by word => create word vectors
        Map<String, List<DocumentStats>> groupByWord = wordDocumentMatrix.stream().collect(Collectors.groupingBy(DocumentStats::getWord));

        // perform svd

        double[][] tfIdfArray = this.weightStructureConverter.convert(groupByWord);

        int rank = Math.min(tfIdfArray[0].length / 2, 50); // rank cannot be bigger than amount of documents

        double[][] solved = svdService.solve(tfIdfArray, rank);

        // convert again to get values for corresponding documents
        List<List<DocumentStats>> matrix = new ArrayList<>(groupByWord.values());
        for (int i = 0; i < groupByWord.size(); i++) {    // word vector
            List<DocumentStats> wordVector = matrix.get(i);
            for (int j = 0; j < wordVector.size(); j++) {
                wordVector.get(j).setValue(solved[i][j]);
            }
        }

        // calculate similarity matrix with cosine measure
        Set<CompareResult> result = new HashSet<>();
        Map<Integer, List<DocumentStats>> groupByDocument = wordDocumentMatrix.stream().collect(Collectors.groupingBy(DocumentStats::getDocumentId));
        ArrayList<List<DocumentStats>> documents = new ArrayList<>(groupByDocument.values());
        for (int i = 0; i < groupByDocument.values().size(); i++) {
            for (int j = 0; j < groupByDocument.values().size(); j++) {
                if(i != j) {
                    List<DocumentStats> documentVector1 = documents.get(i);
                    List<DocumentStats> documentVector2 = documents.get(j);
                    Double sim = this.similarityMeasureService.findSimilarity(documentVector1, documentVector2);
                    Integer documentId1 = documentVector1.get(0).getDocumentId();
                    Integer documentId2 = documentVector2.get(0).getDocumentId();
                    //todo: add new dto class that will update entity from db
                    result.add(new CompareResult(documentId1, documentId2, sim, null, null));
                }
            }
        }

        return result;
    }
}
