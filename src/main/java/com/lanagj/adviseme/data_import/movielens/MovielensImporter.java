package com.lanagj.adviseme.data_import.movielens;

import com.lanagj.adviseme.entity.movie_list.EvaluationUserMovie;
import com.lanagj.adviseme.entity.movie_list.EvaluationUserMovieRepository;
import com.lanagj.adviseme.entity.movie_list.UserMovieStatus;
import lombok.AllArgsConstructor;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class MovielensImporter {

    EvaluationUserMovieRepository repository;

    public void importTestData(String folderName) {

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

        String ratingsFile = this.getFilePath(folderName, "ratings.csv");
        try (ReversedLinesFileReader fr = new ReversedLinesFileReader(new File(ratingsFile), Charset.defaultCharset())) {

            int i = 0;
            while ((line = fr.readLine()) != null && i != 5_000_000) {
                String[] lineItems = line.split(cvsSplitBy);

                Integer userId = Integer.parseInt(lineItems[0]);
                Integer movielensMovieId = Integer.parseInt(lineItems[1]);
                double rating = Double.parseDouble(lineItems[2]);
                UserMovieStatus status = rating > 3.0 ? UserMovieStatus.LIKED : UserMovieStatus.DISLIKED;

                this.repository.save(new EvaluationUserMovie(
                        userId,
                        movielensTmdbId.get(movielensMovieId).toString(),
                        status));

                i++;
            }

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
