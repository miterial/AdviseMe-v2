package com.lanagj.adviseme.lsa.weight;

import com.lanagj.adviseme.lsa.weight.bag_of_words.BagOfWords;

import java.util.*;

public class TfIdf implements WeightMeasure {

    /**
     * @param bagOfWords representation of the test collection where key - document ID, value - calculated bagOfWords
     * @return set of rows of the word-document matrix
     */
    public List<WordDocument> calculateWeight(Map<Long, List<BagOfWords.WordFrequency>> bagOfWords) {

        // matrix of size document * words
        List<WordDocument> result = new ArrayList<>();

        // term (word) frequency
        Double tf;
        Double idf;
        WordDocument wordDocument;
        Map<String, Double> wordVector;

        // key - word, value - number of documents with this word todo: create converters
        Map<String, Long> countWordInDocuments = this.countWordInDocuments(bagOfWords);

        // for each document
        for (Map.Entry<Long, List<BagOfWords.WordFrequency>> documentEntry : bagOfWords.entrySet()) {

            wordVector = new HashMap<>();
            // for each word in this document
            for (BagOfWords.WordFrequency wordEntry : documentEntry.getValue()) {
                tf = (double) wordEntry.getNumOfOccurrences() / documentEntry.getValue().size(); //todo why +1?
                idf = Math.log((double) bagOfWords.size() / countWordInDocuments.get(wordEntry.getWord()));
                wordVector.put(wordEntry.getWord(), tf * idf);
            }

            wordDocument = new WordDocument(documentEntry.getKey(), wordVector);
            result.add(wordDocument);
        }

        return result;

    }

    private Map<String, Long> countWordInDocuments(Map<Long, List<BagOfWords.WordFrequency>> bagOfWords) {

        Map<String, Long> wordFrequency = new HashMap<>();
        List<BagOfWords.WordFrequency> wordsInDocument;

        // for all documents
        for (Map.Entry<Long, List<BagOfWords.WordFrequency>> documentEntry : bagOfWords.entrySet()) {
            wordsInDocument = documentEntry.getValue();

            // for all words in this document
            for (BagOfWords.WordFrequency wordEntry : wordsInDocument) {
                Long currentNumber = wordFrequency.getOrDefault(wordEntry.getWord(), 0L);
                wordFrequency.put(wordEntry.getWord(), currentNumber + 1);
            }
        }

        return wordFrequency;

    }

}
