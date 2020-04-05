package com.lanagj.adviseme.lsa.weight;

import com.lanagj.adviseme.lsa.weight.bag_of_words.BagOfWords;
import com.lanagj.adviseme.lsa.weight.bag_of_words.BagOfWordsDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TfIdfDataProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

        List<WordDocument> data = new ArrayList<>();
        Map<String, Double> map1 = new HashMap<>();
        map1.put("word1", 0.135);
        map1.put("word2", 0.135);
        map1.put("word3", 0.27);
        data.add(new WordDocument(1L, map1));

        Map<String, Double> map2 = new HashMap<>();
        map2.put("word1", 0.1);
        map2.put("word3", 0.1);
        map2.put("word4", 0.27);
        map2.put("word5", 0.27);
        data.add(new WordDocument(2L, map2));

        Map<String, Double> map3 = new HashMap<>();
        map3.put("word2", 0.4);
        data.add(new WordDocument(2L, map3));

        return Stream.of(Arguments.of(data));
    }
}