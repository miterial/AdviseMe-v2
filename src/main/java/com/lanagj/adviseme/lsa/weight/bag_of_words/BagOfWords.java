package com.lanagj.adviseme.lsa.weight.bag_of_words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BagOfWords {

    // todo change key to word
    // todo replace list with set
    public Map<Long, List<WordFrequency>> get(Map<Long, List<String>> documentWordsMap) {

        Map<Long, List<WordFrequency>> result = new HashMap<>();
        Map<String, Long> wordFrequency;
        List<WordFrequency> wordFrequencyInDocument;

        // calculate frequency of words in each document
        for (Map.Entry<Long, List<String>> entry : documentWordsMap.entrySet()) {

            wordFrequency = entry.getValue().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            wordFrequencyInDocument = new ArrayList<>();
            for (Map.Entry<String, Long> stringLongEntry : wordFrequency.entrySet()) {
                wordFrequencyInDocument.add(new WordFrequency(stringLongEntry.getKey(), stringLongEntry.getValue()));
            }
            result.put(entry.getKey(), wordFrequencyInDocument);
        }

        return result;
    }

    //todo change word to document id
    public class WordFrequency {
        String word;
        Long namOfOccurrences;

        public WordFrequency(String word, Long namOfOccurrences) {

            this.word = word;
            this.namOfOccurrences = namOfOccurrences;
        }

        public String getWord() {

            return word;
        }

        public Long getNumOfOccurrences() {

            return namOfOccurrences;
        }
    }

}
