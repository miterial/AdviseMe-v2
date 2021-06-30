package com.lanagj.adviseme.configuration.tmdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.TmdbMovies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TmdbConfiguration {

    @Value("${tmdb.secret}")
    String tmdbSecret;

    private TmdbApi tmdb;

    @PostConstruct
    public void init() {
        tmdb = new TmdbApi(this.tmdbSecret);
    }

    @Bean
    public TmdbMovies getMoviesService() {
        if(tmdb.getApiKey() == null) {
            throw new IllegalArgumentException("TMDb API key is not set!");
        }
        return tmdb.getMovies();
    }

    @Bean
    public TmdbDiscover getDiscoverService() {
        if(tmdb.getApiKey() == null) {
            throw new IllegalArgumentException("TMDb API key is not set!");
        }
        return tmdb.getDiscover();
    }

    @Bean
    public TmdbGenre getGenresService() {
        if(tmdb.getApiKey() == null) {
            throw new IllegalArgumentException("TMDb API key is not set!");
        }
        return tmdb.getGenre();
    }
}
