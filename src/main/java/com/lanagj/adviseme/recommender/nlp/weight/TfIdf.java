package com.lanagj.adviseme.recommender.nlp.weight;

import com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix.BagOfWords;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TfIdf implements WeightMeasure<BagOfWords.WordFrequency> {

    /**
     * @param bagOfWords representation of the test collection where key - word, value - amount of entries in each document for this word
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
        Map<Integer, Integer> wordsInDocumentCount = this.countWordsInDocuments(bagOfWords);

        int documentsCount = wordsInDocumentCount.size();

        // for each word
        for (Map.Entry<String, List<BagOfWords.WordFrequency>> documentEntry : bagOfWords.entrySet()) {

            // for each document that contains this word
            for (BagOfWords.WordFrequency wordFrequency : documentEntry.getValue()) {
                Long qqq = wordFrequency.getNumOfOccurrences();
                Integer documentId = wordFrequency.getDocumentId();
                Integer www = wordsInDocumentCount.get(documentId);
                tf = (double) qqq / (double) www; //todo why +1?
                idf = Math.log((double) documentsCount / (double)documentsWithWordCount.get(documentEntry.getKey()));
                documentStats = new DocumentStats(documentEntry.getKey(), Math.toIntExact(documentId), tf * idf);
                result.add(documentStats);
            }

        }

        return result;

    }

    private Map<Integer, Integer> countWordsInDocuments(Map<String, List<BagOfWords.WordFrequency>> bagOfWords) {

        Map<Integer, Integer> result = new HashMap<>();

        for (Map.Entry<String, List<BagOfWords.WordFrequency>> entry : bagOfWords.entrySet()) {
            for (BagOfWords.WordFrequency wordFrequency : entry.getValue()) {
                if (wordFrequency.getNumOfOccurrences() != 0) {
                    Integer documentId = wordFrequency.getDocumentId();
                    result.putIfAbsent(documentId, 0);
                    Integer currentWordCount = result.get(documentId);
                    currentWordCount += wordFrequency.getNumOfOccurrences().intValue();
                    result.put(documentId, currentWordCount);
                }
            }
        }

        return result;
    }

    private Map<String, Integer> countDocumentsByWord(Map<String, List<BagOfWords.WordFrequency>> bagOfWords) {

        Map<String, Set<Integer>> wordsInDocuments = new HashMap<>();

        for (Map.Entry<String, List<BagOfWords.WordFrequency>> entry : bagOfWords.entrySet()) {
            wordsInDocuments.putIfAbsent(entry.getKey(), new HashSet<>());
            for (BagOfWords.WordFrequency wordFrequency : entry.getValue()) {
                if (wordFrequency.getNumOfOccurrences() != 0) {
                    wordsInDocuments.get(entry.getKey()).add(wordFrequency.getDocumentId());
                }
            }
        }

        Map<String, Integer> result = new HashMap<>();

        wordsInDocuments.forEach((key, value) -> result.put(key, value.size()));

        return result;

    }

}
