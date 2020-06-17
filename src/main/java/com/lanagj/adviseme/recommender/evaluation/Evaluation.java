package com.lanagj.adviseme.recommender.evaluation;

import com.lanagj.adviseme.configuration.AlgorithmType;
import com.lanagj.adviseme.data_import.movielens.MovielensImporter;
import com.lanagj.adviseme.entity.movie_list.UserMovieStatus;
import com.lanagj.adviseme.entity.movie_list.evaluation.TestUserMovie;
import com.lanagj.adviseme.entity.movie_list.evaluation.TestUserMovieRepository;
import com.lanagj.adviseme.entity.movie_list.evaluation.EvaluationUserMovie;
import com.lanagj.adviseme.entity.movie_list.evaluation.EvaluationUserMovieRepository;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.entity.similarity.CompareResultRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Evaluation {

    private final Double SIMILARITY_LIMIT = 0.6;

    TestUserMovieRepository evaluationRepository;
    EvaluationUserMovieRepository evaluationUserMovieRepository;

    MovielensImporter movielensImporter;
    CompareResultRepository compareResultRepository;

    public Evaluation(TestUserMovieRepository evaluationRepository, EvaluationUserMovieRepository evaluationUserMovieRepository, MovielensImporter movielensImporter, CompareResultRepository compareResultRepository) {

        this.evaluationRepository = evaluationRepository;
        this.evaluationUserMovieRepository = evaluationUserMovieRepository;
        this.movielensImporter = movielensImporter;
        this.compareResultRepository = compareResultRepository;
    }

    List<TestUserMovie> realUserMovies;
    List<EvaluationUserMovie> evaluationMovies;

    public void init() {
        if(realUserMovies == null || realUserMovies.isEmpty()) {
            realUserMovies = this.importEvaluationMovies(10);
        }
        evaluationMovies = this.selectTestMovies(realUserMovies);
    }

    public EvaluationResult compare() {
        log.info("Comparing...");
        Double f1Score_lsa = this.getF1Score(AlgorithmType.LSA);
        Double f1Score_mlsa = this.getF1Score(AlgorithmType.MLSA);
        Double f1Score_ngram = this.getF1Score(AlgorithmType.NGRAM);
        return new EvaluationResult(f1Score_lsa, f1Score_mlsa, f1Score_ngram);
    }

    private List<EvaluationUserMovie> selectTestMovies(List<TestUserMovie> evaluationMovies) {

        List<EvaluationUserMovie> result = this.evaluationUserMovieRepository.findAll();
        if(result.isEmpty()) {
            log.info("Calculating test dataset");
            //key - movieId, value - users that rated this movie
            Map<Integer, Set<Integer>> movies = evaluationMovies.stream().collect(Collectors.groupingBy(TestUserMovie::getMovieId, mapping(TestUserMovie::getUserId, toSet())));

            List<CompareResult> byMovieIds = this.compareResultRepository.findByMovieIds(movies.keySet());

            CompareResult maxCompareResultMlsa = byMovieIds.stream().max(Comparator.comparing(x -> x.getResults().get(AlgorithmType.MLSA))).get();
            System.out.println("max compare result by MLSA: " + maxCompareResultMlsa);

            for (CompareResult compareResult : byMovieIds) {
                Integer movieId1 = compareResult.getIdPair().getMovieId1();
                Set<Integer> userIds = movies.get(movieId1);
                if(userIds != null) {
                    for (Integer userId : userIds) {
                        //lsa
                        Double simResult = compareResult.getResults().get(AlgorithmType.LSA);
                        UserMovieStatus status = simResult >= SIMILARITY_LIMIT ? UserMovieStatus.RECOMMENDED : UserMovieStatus.NOT_RECOMMENDED;
                        result.add(new EvaluationUserMovie(userId, movieId1, status, AlgorithmType.LSA));

                        //mlsa
                        simResult = compareResult.getResults().get(AlgorithmType.MLSA);
                        status = simResult >= SIMILARITY_LIMIT ? UserMovieStatus.RECOMMENDED : UserMovieStatus.NOT_RECOMMENDED;
                        result.add(new EvaluationUserMovie(userId, movieId1, status, AlgorithmType.MLSA));
                    }
                }

                Integer movieId2 = compareResult.getIdPair().getMovieId2();
                userIds = movies.get(movieId2);
                if( userIds != null) {
                    for (Integer userId : userIds) {
                        //lsa
                        Double simResult = compareResult.getResults().get(AlgorithmType.LSA);
                        UserMovieStatus status = simResult >= SIMILARITY_LIMIT ? UserMovieStatus.RECOMMENDED : UserMovieStatus.NOT_RECOMMENDED;
                        result.add(new EvaluationUserMovie(userId, movieId2, status, AlgorithmType.LSA));

                        //mlsa
                        simResult = compareResult.getResults().get(AlgorithmType.MLSA);
                        status = simResult >= SIMILARITY_LIMIT ? UserMovieStatus.RECOMMENDED : UserMovieStatus.NOT_RECOMMENDED;
                        result.add(new EvaluationUserMovie(userId, movieId2, status, AlgorithmType.MLSA));
                    }
                }
            }

            this.evaluationUserMovieRepository.saveAll(result);

        }

        return result;
    }

    public List<TestUserMovie> importEvaluationMovies(int limit) {

        List<TestUserMovie> result = this.evaluationRepository.findAll();
        if(result.isEmpty()) {
            log.info("Importing movielens dataset");
            this.movielensImporter.importTestData("movielens", limit);
            return this.evaluationRepository.findAll();
        }

        return result;

    }

    private Double getF1Score(AlgorithmType algorithmType) {

        List<EvaluationUserMovie> algorithmList = new ArrayList<>(evaluationMovies);
        algorithmList.removeIf(tum -> tum.getAlgorithmType() != algorithmType);

        Set<Integer> recommendedIds = algorithmList.stream().filter(tum -> tum.getStatus() == UserMovieStatus.RECOMMENDED).map(EvaluationUserMovie::getMovieId).collect(Collectors.toSet());
        Set<Integer> notRecommendedIds = algorithmList.stream().filter(tum -> tum.getStatus() == UserMovieStatus.NOT_RECOMMENDED).map(EvaluationUserMovie::getMovieId).collect(Collectors.toSet());;
        Set<Integer> likedIds = this.realUserMovies.stream().filter(eum -> eum.getStatus() == UserMovieStatus.LIKED).map(TestUserMovie::getMovieId).collect(toSet());

        double recommendedCount = recommendedIds.size();

        recommendedIds.retainAll(likedIds);
        notRecommendedIds.retainAll(likedIds);

        double recommendedLikedCount = recommendedIds.size();
        double notRecommendedLikedCount = notRecommendedIds.size();

        log.info("type={}, recommended={}, rec+liked={}, not_rec+liked={}", algorithmType, recommendedCount, recommendedLikedCount, notRecommendedLikedCount);

        /*if(recommendedLikedCount == 0.0 && notRecommendedLikedCount == 0.0) {
            throw new IllegalStateException("Fix your lists!");
        }*/

        Double precision = recommendedLikedCount / recommendedCount;
        Double recall = recommendedLikedCount / (recommendedLikedCount + notRecommendedLikedCount);

        Double result = (2 * (precision * recall)) / (precision + recall);

        if(result.isNaN()) {
            return 0.0;
        }

        return result;
    }

}
