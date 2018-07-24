package com.entertainment.dao;

import com.entertainment.model.dao.Movie;
import com.entertainment.model.dto.MovieEarned;
import com.entertainment.model.dto.MoviesTitles;

import java.util.Collection;

public interface MovieDao {

    Collection<MoviesTitles> getMoviesTitles();
    Movie getMovieById(final int movieId);
    Collection<MovieEarned> moviesEarned();
    int addMovie(final Movie movie);
    void updateMovie(final Movie movie);
    void deleteMovie(final int movieId);
}
