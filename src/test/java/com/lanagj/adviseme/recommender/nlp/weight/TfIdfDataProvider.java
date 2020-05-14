package com.lanagj.adviseme.recommender.nlp.weight;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TfIdfDataProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

        List<List<DocumentStats>> data = new ArrayList<>();
        data.add(new ArrayList<>());
        data.add(new ArrayList<>());
        data.add(new ArrayList<>());

        data.get(0).add(new DocumentStats("word1", 1, 0.135));
        data.get(0).add(new DocumentStats("word2", 1, 0.27));
        data.get(0).add(new DocumentStats("word3", 1, 0.13));

        data.get(1).add(new DocumentStats("word1", 2, 0.1));
        data.get(1).add(new DocumentStats("word3", 2, 0.1));
        data.get(1).add(new DocumentStats("word4", 2, 0.27));
        data.get(1).add(new DocumentStats("word5", 2, 0.27));

        data.get(2).add(new DocumentStats("word2", 3, 0.4));

        return Stream.of(Arguments.of(data));
    }
}