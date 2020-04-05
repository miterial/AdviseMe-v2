package com.lanagj.adviseme.lsa.weight.bag_of_words;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BagOfWordsDataProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Map<Long, List<BagOfWords.WordFrequency>> wordFrequency = new HashMap<>();
        List<BagOfWords.WordFrequency> list1 = new ArrayList<>();
        list1.add(new BagOfWords.WordFrequency("word1", 1L));
        list1.add(new BagOfWords.WordFrequency("word2", 2L));
        list1.add(new BagOfWords.WordFrequency("word3", 1L));
        wordFrequency.put(1L, list1);

        List<BagOfWords.WordFrequency> list2 = new ArrayList<>();
        list2.add(new BagOfWords.WordFrequency("word1", 1L));
        list2.add(new BagOfWords.WordFrequency("word3", 1L));
        list2.add(new BagOfWords.WordFrequency("word4", 1L));
        list2.add(new BagOfWords.WordFrequency("word5", 1L));
        wordFrequency.put(2L, list2);

        List<BagOfWords.WordFrequency> list3 = new ArrayList<>();
        list3.add(new BagOfWords.WordFrequency("word2", 1L));
        wordFrequency.put(3L, list3);
        return Stream.of(
            Arguments.of(
                    wordFrequency
            )
        );
    }
}