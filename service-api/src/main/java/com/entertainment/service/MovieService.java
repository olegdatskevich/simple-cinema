package com.entertainment.service;

import com.entertainment.model.dao.Movie;
import com.entertainment.model.dto.MovieEarned;
import com.entertainment.model.dto.MoviesTitles;

import java.util.Collection;

/**
 * Service API for Movie.
 */
public interface MovieService {

    /**
     * Getting all movies titles from DB.
     * @return - collection of movies.
     */
    Collection<MoviesTitles> getMoviesTitles();

    /**
     * Getting one movie from BD.
     * @param movieId - id of movie
     * @return movie.
     */
    Movie getMovieById(final int movieId);

    /**
     * Getting all movies from DB with earn.
     * @return collection movies.
     */
    Collection<MovieEarned> moviesEarned();

    /**
     * Add movie in DB.
     * @param movie - movie that need to add.
     * @return movie which was added.
     */
    int addMovie(final Movie movie);

    /**
     * Update movie.
     * @param movie which was updated
     */
    void updateMovie(final Movie movie);

    /**
     * Deactivate movie.
     * @param movieId - id of movie which was deactivated.
     */
    void deleteMovie(final int movieId);
}
