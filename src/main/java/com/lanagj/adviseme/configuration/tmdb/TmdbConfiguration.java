package com.lanagj.adviseme.configuration.tmdb;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import com.uwetrottmann.tmdb2.services.GenresService;
import com.uwetrottmann.tmdb2.services.MoviesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TmdbConfiguration {

    @Value("${tmdb.secret}")
    String tmdbSecret;

    private final Tmdb tmdb;

    public TmdbConfiguration() {
        tmdb = new Tmdb(this.tmdbSecret);
    }

    @Bean
    public MoviesService getMoviesService() {
        return tmdb.moviesService();
    }

    @Bean
    public DiscoverService getDiscoverService() {
        return tmdb.discoverService();
    }
}
