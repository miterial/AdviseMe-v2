package com.lanagj.adviseme.recommender.shingles;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Windows {

    List<String> windows;
    Long documentId;

    public Windows(Long key, List<String> document, int windowSize) {

        this.documentId = key;
        this.windows = new ArrayList<>();

        int max = document.size() - windowSize + 1;
        for (int i = 0; i < max; i++) {
            StringBuilder shingle = new StringBuilder();
            for (int j = i; j < i + windowSize; j++) {
                String token = document.get(j);
                shingle.append(token);
            }
            this.windows.add(shingle.toString());
        }
    }
}