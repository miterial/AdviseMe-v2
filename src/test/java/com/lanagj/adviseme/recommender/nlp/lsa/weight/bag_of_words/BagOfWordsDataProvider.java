package com.lanagj.adviseme.recommender.nlp.lsa.weight.bag_of_words;

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

        Map<String, List<BagOfWords.WordFrequency>> wordFrequency = new HashMap<>();
        List<BagOfWords.WordFrequency> list1 = new ArrayList<>();
        list1.add(new BagOfWords.WordFrequency(1L, 1L));
        list1.add(new BagOfWords.WordFrequency(2L, 1L));
        list1.add(new BagOfWords.WordFrequency(3L, 0L));
        wordFrequency.put("word1", list1);

        List<BagOfWords.WordFrequency> list2 = new ArrayList<>();
        list2.add(new BagOfWords.WordFrequency(1L, 2L));
        list2.add(new BagOfWords.WordFrequency(2L, 0L));
        list2.add(new BagOfWords.WordFrequency(3L, 1L));
        wordFrequency.put("word2", list2);

        List<BagOfWords.WordFrequency> list3 = new ArrayList<>();
        list3.add(new BagOfWords.WordFrequency(1L, 1L));
        list3.add(new BagOfWords.WordFrequency(2L, 1L));
        list3.add(new BagOfWords.WordFrequency(3L, 0L));
        wordFrequency.put("word3", list3);

        List<BagOfWords.WordFrequency> list4 = new ArrayList<>();
        list4.add(new BagOfWords.WordFrequency(1L, 0L));
        list4.add(new BagOfWords.WordFrequency(2L, 1L));
        list4.add(new BagOfWords.WordFrequency(3L, 0L));
        wordFrequency.put("word4", list4);

        List<BagOfWords.WordFrequency> list5 = new ArrayList<>();
        list5.add(new BagOfWords.WordFrequency(1L, 0L));
        list5.add(new BagOfWords.WordFrequency(2L, 1L));
        list5.add(new BagOfWords.WordFrequency(3L, 0L));
        wordFrequency.put("word5", list5);

        return Stream.of(
            Arguments.of(
                    wordFrequency
            )
        );
    }
}