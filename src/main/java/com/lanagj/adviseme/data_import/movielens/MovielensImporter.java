package com.lanagj.adviseme.data_import.movielens;

import com.lanagj.adviseme.entity.movie_list.evaluation.TestUserMovie;
import com.lanagj.adviseme.entity.movie_list.evaluation.TestUserMovieRepository;
import com.lanagj.adviseme.entity.movie_list.UserMovieStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MovielensImporter {

    TestUserMovieRepository repository;

    public void importTestData(String folderName, int limit) {

        log.info("Importing links data");

        String line;
        String cvsSplitBy = ",";

        String linksFile = this.getFilePath(folderName, "links.csv");
        Map<Integer, Integer> movielensTmdbId = new HashMap<>();

        try (ReversedLinesFileReader fr = new ReversedLinesFileReader(new File(linksFile), Charset.defaultCharset())) {

            while ((line = fr.readLine()) != null) {
                String[] lineItems = line.split(cvsSplitBy);
                movielensTmdbId.put(Integer.parseInt(lineItems[0]), Integer.parseInt(lineItems[1]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Importing ratings data");
        String ratingsFile = this.getFilePath(folderName, "ratings.csv");
        try (ReversedLinesFileReader fr = new ReversedLinesFileReader(new File(ratingsFile), Charset.defaultCharset())) {

            List<TestUserMovie> result = new ArrayList<>();
            while ((line = fr.readLine()) != null && result.size() < limit) {
                String[] lineItems = line.split(cvsSplitBy);

                Integer userId = Integer.parseInt(lineItems[0]);
                Integer movielensMovieId = Integer.parseInt(lineItems[1]);
                double rating = Double.parseDouble(lineItems[2]);
                UserMovieStatus status = rating > 3.0 ? UserMovieStatus.LIKED : UserMovieStatus.DISLIKED;

                result.add(new TestUserMovie(
                        userId,
                        movielensTmdbId.get(movielensMovieId),
                        status));
            }
            this.repository.saveAll(result);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getFilePath(String folderName, String fileName) {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        return path.substring(0, path.length() - 1) + folderName + "\\" + fileName;
    }
}
