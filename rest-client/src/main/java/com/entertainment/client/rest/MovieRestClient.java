package com.entertainment.client.rest;

import com.entertainment.client.ServerDataAccessException;
import com.entertainment.model.dao.Movie;
import com.entertainment.model.dto.MovieEarned;
import com.entertainment.model.dto.MoviesTitles;
import com.entertainment.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Rest client for MOVIE.
 */
@Service
public class MovieRestClient implements MovieService {

    /**
     * Logger for MovieRestClient.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * URL for MOVIES.
     */
    @Value("${movieRestClientUrl}")
    private String url;
    /**
     * For interaction with REST module.
     */
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public final Collection<MoviesTitles> getMoviesTitles()
            throws ServerDataAccessException, ClassCastException {
        ResponseEntity<Collection> responseEntity
                = restTemplate.getForEntity(url + "titles", Collection.class);
        Collection<MoviesTitles> movies
                = (Collection<MoviesTitles>) responseEntity.getBody();
        LOGGER.debug("REST-client getMoviesTitles({})", responseEntity);
        return movies;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Collection<MovieEarned> moviesEarned()
            throws ServerDataAccessException {
        ResponseEntity<Collection> responseEntity
                = restTemplate.getForEntity(url, Collection.class);
        Collection<MovieEarned> moviesEarned
                = (Collection<MovieEarned>) responseEntity.getBody();
        LOGGER.debug("REST-client moviesEarned({})", responseEntity);
        return moviesEarned;
    }

    @Override
    public final Movie getMovieById(final int movieId)
            throws ServerDataAccessException {
        ResponseEntity<Movie> responseEntity = restTemplate
                .getForEntity(url + "/" + movieId, Movie.class);
        Movie movie = responseEntity.getBody();
        LOGGER.debug("REST-client getMovieById({})", responseEntity);
        return movie;
    }

    @Override
    public final int addMovie(final Movie movie)
            throws ServerDataAccessException {
        ResponseEntity<Integer> responseEntity
                = restTemplate.postForEntity(url, movie, Integer.class);
        Integer result = responseEntity.getBody();
        LOGGER.debug("REST-client addMovie({})", responseEntity);
        return result;
    }

    @Override
    public final  void updateMovie(final Movie movie)
            throws ServerDataAccessException {
        LOGGER.debug("REST-client updateMovie({})", movie);
        restTemplate.put(url, movie);
    }

    @Override
    public final void deleteMovie(final int movieId)
            throws ServerDataAccessException {
        LOGGER.debug("REST-client deleteMovie({})", movieId);
        restTemplate.put(url + "/" + movieId, movieId);
    }
}
