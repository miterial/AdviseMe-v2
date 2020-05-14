package com.lanagj.adviseme.recommender.nlp.preprocessing;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://lucene.apache.org/core/4_0_0/analyzers-common/org/apache/lucene/analysis/ru/RussianAnalyzer.html
 */
@Service
public class PorterStemming {

    public List<String> process(String document) {

        List<String> results = new ArrayList<>();

        Analyzer analyzer = new RussianAnalyzer();
        try (TokenStream tokens = analyzer.tokenStream("", document)) {
            CharTermAttribute term = tokens.getAttribute(CharTermAttribute.class);
            tokens.reset();
            while (tokens.incrementToken()) {
                String t = term.toString().trim();
                if (t.length() > 0) {
                    results.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }
}
