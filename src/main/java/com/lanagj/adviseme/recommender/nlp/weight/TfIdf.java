package com.lanagj.adviseme.recommender.nlp.weight;

import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.BagOfWords;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TfIdf implements WeightMeasure<BagOfWords.WordFrequency> {

    /**
     * @param bagOfWords representation of the test collection where key - document ID, value - calculated bagOfWords
     * @return set of rows of the word-document matrix
     */
    public List<MatrixCell> calculateWeight(Map<String, List<BagOfWords.WordFrequency>> bagOfWords) {

        // matrix of size document * words
        List<MatrixCell> result = new ArrayList<>();

        // term (word) frequency
        Double tf;
        Double idf;
        DocumentStats documentStats;

        // key - word, value - number of documents with this word todo: create converters
        Map<String, Integer> documentsWithWordCount = this.countDocumentsByWord(bagOfWords);
        // key docID, value - amount of words in this document
        Map<Long, Integer> wordsInDocumentCount = this.countWordsInDocuments(bagOfWords);

        int documentsCount = wordsInDocumentCount.size();

        // for each word
        for (Map.Entry<String, List<BagOfWords.WordFrequency>> documentEntry : bagOfWords.entrySet()) {

            // for each document that contains this word
            for (BagOfWords.WordFrequency wordFrequency : documentEntry.getValue()) {
                tf = (double) wordFrequency.getNumOfOccurrences() / (double)wordsInDocumentCount.get(wordFrequency.getDocumentId()); //todo why +1?
                idf = Math.log((double) documentsCount / (double)documentsWithWordCount.get(documentEntry.getKey()));
                documentStats = new DocumentStats(documentEntry.getKey(), Math.toIntExact(wordFrequency.getDocumentId()), tf * idf);
                result.add(documentStats);
            }

        }

        return result;

    }

    private Map<Long, Integer> countWordsInDocuments(Map<String, List<BagOfWords.WordFrequency>> bagOfWords) {

        Map<Long, Set<String>> wordsInDocuments = new HashMap<>();

        for (Map.Entry<String, List<BagOfWords.WordFrequency>> entry : bagOfWords.entrySet()) {
            for (BagOfWords.WordFrequency wordFrequency : entry.getValue()) {
                if (wordFrequency.getNumOfOccurrences() != 0) {
                    wordsInDocuments.compute(wordFrequency.getDocumentId(),
                            (key, value) -> {
                                if (value == null) {
                                    value = new HashSet<>();
                                }
                                value.add(entry.getKey());
                                return value;
                            });
                }
            }
        }

        Map<Long, Integer> result = new HashMap<>();

        wordsInDocuments.forEach((key, value) -> result.put(key, value.size()));

        return result;
    }

    private Map<String, Integer> countDocumentsByWord(Map<String, List<BagOfWords.WordFrequency>> bagOfWords) {

        Map<String, Set<Long>> wordsInDocuments = new HashMap<>();

        for (Map.Entry<String, List<BagOfWords.WordFrequency>> entry : bagOfWords.entrySet()) {
            for (BagOfWords.WordFrequency wordFrequency : entry.getValue()) {
                if (wordFrequency.getNumOfOccurrences() != 0) {
                    wordsInDocuments.compute(entry.getKey(),
                            (key, value) -> {
                                if (value == null) {
                                    value = new HashSet<>();
                                }
                                value.add(wordFrequency.getDocumentId());
                                return value;
                            });
                }
            }
        }

        Map<String, Integer> result = new HashMap<>();

        wordsInDocuments.forEach((key, value) -> result.put(key, value.size()));

        return result;

    }

}
