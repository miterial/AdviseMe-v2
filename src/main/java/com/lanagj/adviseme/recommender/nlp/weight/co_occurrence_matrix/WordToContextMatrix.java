package com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix;


import com.lanagj.adviseme.recommender.shingles.Windows;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

public class WordToContextMatrix {


    /**
     * Calculate co-occurrence by context (almost the same as in bag-of-words but search inside n-grams instead of full documents)
     *
     * @param documentContextMatrix key - document ID, value - list of words (with duplicates) from document
     * @return word vector: key - word, value - list of words with co-occurrences
     */
    public List<CooccurrenceFrequency> get(Map<Long, List<String>> documentContextMatrix) {

        int n = 4;     // window size

        // split documents to n-grams
        List<Windows> windows = new ArrayList<>();
        for (Map.Entry<Long, List<String>> document : documentContextMatrix.entrySet()) {
            // temporary: window size=document size
            windows.add(new Windows(document.getKey(), document.getValue(), document.getValue().size()));
        }

        // calculate how many times each word was found in each n-gram
        Set<String> vocabulary = documentContextMatrix.values().stream().flatMap(List::stream).collect(Collectors.toSet());

        List<CooccurrenceFrequency> cooccurrenceFrequency = new ArrayList<>();
        Map<String, List<WordFrequency>> wordFrequency = new HashMap<>();
        for (Iterator<String> iteratorByWord1 = vocabulary.iterator(); iteratorByWord1.hasNext(); ) {

            String word1 = iteratorByWord1.next();

            wordFrequency.put(word1, new ArrayList<>());

            for (String word2 : vocabulary) {
                if(!word2.equals(word1)) {
                    for (Windows document : windows) {

                        int frequencyWord1 = Collections.frequency(document.getWindows(), word1);
                        int frequencyWord2 = Collections.frequency(document.getWindows(), word2);

                        wordFrequency.compute(word1, (key, value) -> {
                            value.add(new WordFrequency(Math.toIntExact(document.getDocumentId()), (long) frequencyWord1, word1));
                            return value;
                        });

                        cooccurrenceFrequency.add(new CooccurrenceFrequency(word1, word2, 1));
                    }

                    // update number of occurrences for this word
                }
            }
        }

        // create map from wordFrequency, key - word
        // create list of CooccurrenceFrequency using this map
        // if map.get(word1).getDocId == map.get(word2).getDocId && word1 !=word2 then new CooccurrenceFrequency()

        return new ArrayList<>();
    }

    @ToString
    @AllArgsConstructor
    @Getter
    static class WordFrequency {

        Integer documentId;
        Long numOfOccurrences;
        String word;
    }

    @ToString
    @AllArgsConstructor
    @Getter
    public static class CooccurrenceFrequency {

        String word1;
        String word2;
        Integer numOfOccurrences;
    }
}
