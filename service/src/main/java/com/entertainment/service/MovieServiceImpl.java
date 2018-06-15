package com.entertainment.service;

import com.entertainment.dao.MovieDao;
import com.entertainment.model.dao.Movie;
import com.entertainment.model.dto.MovieEarned;
import com.entertainment.model.dto.MoviesTitles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service("movieService")
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MovieDao movieDao;

    @Override
    public final Collection<MoviesTitles> getMoviesTitles()
            throws DataAccessException {
        Collection<MoviesTitles> movies = movieDao.getMoviesTitles();
        LOGGER.debug("getMoviesTitles({})", movies);
        return movies;
    }

    @Override
    public final Movie getMovieById(final int movieId)
            throws DataAccessException {
        Movie movie = movieDao.getMovieById(movieId);
        LOGGER.debug("getMovieById({})", movie);
        return movie;
    }

    @Override
    public final Collection<MovieEarned> moviesEarned()
            throws DataAccessException {
        Collection<MovieEarned> moviesEarn = movieDao.moviesEarned();
        LOGGER.debug("moviesEarned({})", moviesEarn);
        return moviesEarn;
    }

    @Override
    @Transactional
    public Movie addMovie(final Movie movie) throws DataAccessException {
        Movie addedMovie = movieDao.addMovie(movie);
        LOGGER.debug("addMovie({})", movie);
        return addedMovie;
    }

    @Override
    @Transactional
    public void updateMovie(final Movie movie)
            throws DataAccessException {
        LOGGER.debug("updateMovie({})", movie);
        movieDao.updateMovie(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(final int movieId)
            throws DataAccessException {
        LOGGER.debug("deleteMovie({})", movieId);
        movieDao.deleteMovie(movieId);
    }
}
