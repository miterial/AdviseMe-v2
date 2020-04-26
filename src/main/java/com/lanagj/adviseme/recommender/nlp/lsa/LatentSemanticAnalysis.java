package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.entity.movie.MovieToNLPConverter;
import com.lanagj.adviseme.entity.similarity.Similarity;
import com.lanagj.adviseme.recommender.nlp.lsa.svd.SVD;
import com.lanagj.adviseme.recommender.nlp.lsa.weight.DocumentStats;
import com.lanagj.adviseme.recommender.nlp.lsa.weight.TfIdf;
import com.lanagj.adviseme.recommender.nlp.lsa.weight.TfIdfToArrayConverter;
import com.lanagj.adviseme.recommender.nlp.lsa.weight.bag_of_words.BagOfWords;
import com.lanagj.adviseme.recommender.nlp.similarity.CosineSimilarity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LatentSemanticAnalysis implements NaturalRanguageProcessing {

    MovieToNLPConverter movieToNlpConverter;

    TfIdfToArrayConverter tfIdfStructureConverter;

    BagOfWords bagOfWordsService;
    TfIdf tfIdfService;
    SVD svdService;
    CosineSimilarity cosineSimilarityService;

    public LatentSemanticAnalysis(MovieToNLPConverter movieToNlpConverter, TfIdfToArrayConverter tfIdfStructureConverter) {

        this.movieToNlpConverter = movieToNlpConverter;
        this.tfIdfStructureConverter = tfIdfStructureConverter;
        this.bagOfWordsService = new BagOfWords();
        this.tfIdfService = new TfIdf();
        this.svdService = new SVD();
        this.cosineSimilarityService = new CosineSimilarity();
    }

    //todo: make async
    @Override
    public Set<Similarity> run() {

        // get preprocessed words
        Map<Long, List<String>> stemmedWords = this.movieToNlpConverter.transform();

        Map<String, List<BagOfWords.WordFrequency>> bagOfWords = bagOfWordsService.get(stemmedWords);

        // calculate tf-idf matrix for words-documents
        List<DocumentStats> wordDocumentMatrix = this.tfIdfService.calculateWeight(bagOfWords);

        // sort by docID
        wordDocumentMatrix.sort(Comparator.comparingInt(DocumentStats::getDocumentId));

        // group by word => create word vectors
        Map<String, List<DocumentStats>> groupByWord = wordDocumentMatrix.stream().collect(Collectors.groupingBy(DocumentStats::getWord));

        // perform svd
        double[][] tfIdfArray = this.tfIdfStructureConverter.convert(groupByWord);
        double[][] solved = svdService.solve(tfIdfArray, 3);

        // convert again to get values for corresponding documents
        List<List<DocumentStats>> matrix = new ArrayList<>(groupByWord.values());
        for (int i = 0; i < groupByWord.size(); i++) {    // word vector
            List<DocumentStats> wordVector = matrix.get(i);
            for (int j = 0; j < wordVector.size(); j++) {
                wordVector.get(j).setValue(solved[i][j]);
            }
        }

        // calculate similarity matrix with cosine measure
        Set<Similarity> result = new HashSet<>();
        Map<Integer, List<DocumentStats>> groupByDocument = wordDocumentMatrix.stream().collect(Collectors.groupingBy(DocumentStats::getDocumentId));
        ArrayList<List<DocumentStats>> documents = new ArrayList<>(groupByDocument.values());
        for (int i = 0; i < groupByDocument.values().size(); i++) {
            for (int j = 0; j < groupByDocument.values().size(); j++) {
                if(i != j) {
                    List<DocumentStats> documentVector1 = documents.get(i);
                    List<DocumentStats> documentVector2 = documents.get(j);
                    Double sim = this.cosineSimilarityService.findSimilarity(documentVector1, documentVector2);
                    Integer documentId1 = documentVector1.get(0).getDocumentId();
                    Integer documentId2 = documentVector2.get(0).getDocumentId();
                    result.add(new Similarity(documentId1, documentId2, sim));
                }
            }
        }

        return result;
    }
}
