package com.epam.brest.course.dao;

import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.model.dto.MovieEarned;
import com.epam.brest.course.model.dto.MoviesTitles;

import java.util.Collection;

/**
 * Movie DAO interface.
 */
public interface MovieDao {

    /**
     * Getting movies titles.
     * @return collection of movie.
     */
    Collection<MoviesTitles> getMoviesTitles();

    /**
     * Getting one movie by id.
     * @param movieId - movie id.
     * @return movie.
     */
    Movie getMovieById(final int movieId);

    /**
     * Getting collection of movies with their earning.
     * @return collection of movies.
     */
    Collection<MovieEarned> moviesEarned();

    /**
     * Add movie in DB.
     * @param movie - movie for adding.
     * @return added movie.
     */
    Movie addMovie(final Movie movie);

    /**
     * Update movie.
     * @param movie - movie for updating
     */
    void updateMovie(final Movie movie);

    /**
     * Delete movie by movie id.
     * @param movieId - movie id for deleting.
     */
    void deleteMovie(final int movieId);
}
