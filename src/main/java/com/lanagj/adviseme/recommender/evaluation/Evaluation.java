package com.lanagj.adviseme.recommender.evaluation;

import com.lanagj.adviseme.configuration.AlgorithmType;
import com.lanagj.adviseme.data_import.movielens.MovielensImporter;
import com.lanagj.adviseme.entity.movie_list.UserMovieStatus;
import com.lanagj.adviseme.entity.movie_list.evaluation.EvaluationUserMovie;
import com.lanagj.adviseme.entity.movie_list.evaluation.EvaluationUserMovieRepository;
import com.lanagj.adviseme.entity.movie_list.evaluation.TestUserMovie;
import com.lanagj.adviseme.entity.movie_list.evaluation.TestUserMovieRepository;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.entity.similarity.CompareResultRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Evaluation {

    private final Double SIMILARITY_LIMIT = 0.7;

    EvaluationUserMovieRepository evaluationRepository;
    TestUserMovieRepository testUserMovieRepository;

    MovielensImporter movielensImporter;
    CompareResultRepository compareResultRepository;

    public Evaluation(EvaluationUserMovieRepository evaluationRepository, TestUserMovieRepository testUserMovieRepository, MovielensImporter movielensImporter, CompareResultRepository compareResultRepository) {

        this.evaluationRepository = evaluationRepository;
        this.testUserMovieRepository = testUserMovieRepository;
        this.movielensImporter = movielensImporter;
        this.compareResultRepository = compareResultRepository;
    }

    List<EvaluationUserMovie> evaluationMovies;
    List<TestUserMovie> testMovies;

    public void init() {
        evaluationMovies = this.importEvaluationMovies();
        testMovies = this.selectTestMovies(evaluationMovies);
    }

    public EvaluationResult compare() {
        log.info("Comparing...");
        Double f1Score_lsa = this.getF1Score(AlgorithmType.LSA);
        Double f1Score_mlsa = this.getF1Score(AlgorithmType.MLSA);
        return new EvaluationResult(f1Score_lsa, f1Score_mlsa);
    }

    private List<TestUserMovie> selectTestMovies(List<EvaluationUserMovie> evaluationMovies) {

        List<TestUserMovie> result = this.testUserMovieRepository.findAll();
        if(result.isEmpty()) {
            log.info("Calculating test dataset");
            //key - movieId, value - users that rated this movie
            Map<Integer, Set<Integer>> movies = evaluationMovies.stream().collect(Collectors.groupingBy(EvaluationUserMovie::getMovieId, mapping(EvaluationUserMovie::getUserId, toSet())));

            List<CompareResult> byMovieIds = this.compareResultRepository.findByMovieIds(movies.keySet());

            for (CompareResult compareResult : byMovieIds) {
                Integer movieId1 = compareResult.getIdPair().getMovieId1();
                Set<Integer> userIds = movies.get(movieId1);
                if(userIds != null) {
                    for (Integer userId : userIds) {
                        //lsa
                        Double simResult = compareResult.getResults().get(AlgorithmType.LSA);
                        UserMovieStatus status = simResult >= SIMILARITY_LIMIT ? UserMovieStatus.RECOMMENDED : UserMovieStatus.NOT_RECOMMENDED;
                        result.add(new TestUserMovie(userId, movieId1, status, AlgorithmType.LSA));

                        //mlsa
                        simResult = compareResult.getResults().get(AlgorithmType.MLSA);
                        status = simResult >= SIMILARITY_LIMIT ? UserMovieStatus.RECOMMENDED : UserMovieStatus.NOT_RECOMMENDED;
                        result.add(new TestUserMovie(userId, movieId1, status, AlgorithmType.MLSA));
                    }
                }

                Integer movieId2 = compareResult.getIdPair().getMovieId2();
                userIds = movies.get(movieId2);
                if( userIds != null) {
                    for (Integer userId : userIds) {
                        //lsa
                        Double simResult = compareResult.getResults().get(AlgorithmType.LSA);
                        UserMovieStatus status = simResult >= SIMILARITY_LIMIT ? UserMovieStatus.RECOMMENDED : UserMovieStatus.NOT_RECOMMENDED;
                        result.add(new TestUserMovie(userId, movieId2, status, AlgorithmType.LSA));

                        //mlsa
                        simResult = compareResult.getResults().get(AlgorithmType.MLSA);
                        status = simResult >= SIMILARITY_LIMIT ? UserMovieStatus.RECOMMENDED : UserMovieStatus.NOT_RECOMMENDED;
                        result.add(new TestUserMovie(userId, movieId2, status, AlgorithmType.MLSA));
                    }
                }
            }

            this.testUserMovieRepository.saveAll(result);

        }

        return result;
    }

    private List<EvaluationUserMovie> importEvaluationMovies() {

        List<EvaluationUserMovie> result = this.evaluationRepository.findAll();
        if(result.isEmpty()) {
            log.info("Importing movielens dataset");
            this.movielensImporter.importTestData("movielens");
            return this.evaluationRepository.findAll();
        }

        return result;

    }

    private Double getF1Score(AlgorithmType algorithmType) {

        List<TestUserMovie> algorithmList = new ArrayList<>(testMovies);
        algorithmList.removeIf(tum -> tum.getAlgorithmType() != algorithmType);

        Set<Integer> recommendedIds = algorithmList.stream().filter(tum -> tum.getStatus() == UserMovieStatus.RECOMMENDED).map(TestUserMovie::getMovieId).collect(Collectors.toSet());
        Set<Integer> notRecommendedIds = algorithmList.stream().filter(tum -> tum.getStatus() == UserMovieStatus.NOT_RECOMMENDED).map(TestUserMovie::getMovieId).collect(Collectors.toSet());;
        Set<Integer> likedIds = this.evaluationMovies.stream().filter(eum -> eum.getStatus() == UserMovieStatus.LIKED).map(EvaluationUserMovie::getMovieId).collect(toSet());

        recommendedIds.retainAll(likedIds);
        notRecommendedIds.retainAll(likedIds);

        double recommendedCount = recommendedIds.size();
        double recommendedLikedCount = recommendedIds.size();
        double notRecommendedLikedCount = notRecommendedIds.size();

        if(recommendedLikedCount == 0.0 && notRecommendedLikedCount == 0.0) {
            throw new IllegalStateException("Fix your lists!");
        }

        Double precision = recommendedLikedCount / recommendedCount;
        Double recall = recommendedLikedCount / (recommendedLikedCount + notRecommendedLikedCount);

        Double result = (2 * (precision * recall)) / (precision + recall);

        return result;
    }

}
