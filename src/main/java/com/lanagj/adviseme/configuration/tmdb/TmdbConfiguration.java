package com.lanagj.adviseme.configuration.tmdb;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import com.uwetrottmann.tmdb2.services.GenresService;
import com.uwetrottmann.tmdb2.services.MoviesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TmdbConfiguration {

    @Value("${tmdb.secret}")
    String tmdbSecret;

    private Tmdb tmdb;

    @PostConstruct
    public void init() {
        tmdb = new Tmdb(this.tmdbSecret);
    }

    @Bean
    public MoviesService getMoviesService() {
        if(tmdb.apiKey() == null) {
            throw new IllegalArgumentException("TMDb API key is not set!");
        }
        return tmdb.moviesService();
    }

    @Bean
    public DiscoverService getDiscoverService() {
        if(tmdb.apiKey() == null) {
            throw new IllegalArgumentException("TMDb API key is not set!");
        }
        return tmdb.discoverService();
    }

    @Bean
    public GenresService getGenresService() {
        if(tmdb.apiKey() == null) {
            throw new IllegalArgumentException("TMDb API key is not set!");
        }
        return tmdb.genreService();
    }
}
