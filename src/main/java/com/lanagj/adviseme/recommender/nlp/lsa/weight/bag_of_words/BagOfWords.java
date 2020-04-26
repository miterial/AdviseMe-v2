package com.lanagj.adviseme.recommender.nlp.lsa.weight.bag_of_words;

import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BagOfWords {

    // todo change value with class
    public Map<String, List<WordFrequency>> get(Map<Long, List<String>> documentWordsMap) {

        Map<String, List<WordFrequency>> result = new HashMap<>();
        Map<String, Long> wordFrequency = new HashMap<>();    //key - word, value - num of occurrences in the documents
        List<WordFrequency> q = new ArrayList<>();

        Set<String> allWordsInAllDocuments = documentWordsMap.values().stream().flatMap(List::stream).collect(Collectors.toSet());

        // calculate frequency of words in each document
        for (Map.Entry<Long, List<String>> entry : documentWordsMap.entrySet()) {

            // how many times each word is found in this document
            Map<String, Long> collect = entry.getValue().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // add occurrence count for all words that are found in all documents
            for (String wordInAllDocument : allWordsInAllDocuments) {
                long occurrenceCount = collect.getOrDefault(wordInAllDocument, 0L);
                result.compute(
                        wordInAllDocument,
                        (key, value) -> {
                            if(value == null) {
                                value = new ArrayList<>();
                            }
                            value.add(new WordFrequency(entry.getKey(), occurrenceCount));
                            return value;
                        }
                );
            }
        }

        return result;
    }

    @ToString
    public static class WordFrequency {

        Long documentId;
        Long namOfOccurrences;

        public WordFrequency(Long documentId, Long namOfOccurrences) {

            this.documentId = documentId;
            this.namOfOccurrences = namOfOccurrences;
        }

        public Long getDocumentId() {

            return documentId;
        }

        public Long getNumOfOccurrences() {

            return namOfOccurrences;
        }

    }

}
