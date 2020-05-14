package com.lanagj.adviseme.recommender.nlp.weight.co_occurrence_matrix;

import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BagOfWords implements WordOccurrenceMatrix<BagOfWords.WordFrequency> {

    public Map<String, List<WordFrequency>> get(Map<Long, List<String>> documentWordsMap) {

        Map<String, List<WordFrequency>> result = new HashMap<>();

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
        Long numOfOccurrences;

        public WordFrequency(Long documentId, Long numOfOccurrences) {

            this.documentId = documentId;
            this.numOfOccurrences = numOfOccurrences;
        }

        public Long getDocumentId() {

            return documentId;
        }

        public Long getNumOfOccurrences() {

            return numOfOccurrences;
        }

    }

}
