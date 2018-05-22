package com.epam.brest.course.dao;

import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.model.dto.MovieEarned;
import com.epam.brest.course.model.dto.MoviesTitles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Implementation of DAO layer for movies.
 */
@Repository
public class MovieDaoImpl implements MovieDao {

    /**
     * Logger for MovieDaoImpl.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(MovieDaoImpl.class);

    /**
     * NamedParameterJdbcTemplate.
     */
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Column name in movie table DB.
     */
    private static final String MOVIE_ID = "movieId";

    /**
     * SQL query for select all movies.
     */
    @Value("${movie.select}")
    private String moviesSelect;
    /**
     * SQL query for select movie by id.
     */
    @Value("${movie.selectById}")
    private String movieSelectById;
    /**
     * SQL query for calculate earn.
     */
    @Value("${movie.calculate}")
    private String movieCalcalulateEarn;
    /**
     * SQL query for insert movie.
     */
    @Value("${movie.insert}")
    private String insert;
    /**
     * SQL query for update movie.
     */
    @Value("${movie.update}")
    private String update;
    /**
     * SQL query for delete movie.
     */
    @Value("${movie.delete}")
    private String delete;

    @Override
    public final Collection<MoviesTitles> getMoviesTitles() {
        Collection<MoviesTitles> movies = namedParameterJdbcTemplate
                .query(moviesSelect,
                        BeanPropertyRowMapper.newInstance(MoviesTitles.class));
        LOGGER.debug("getMoviesTitles({})", movies);
        return movies;
    }

    @Override
    public final Movie getMovieById(final int movieId) {
        SqlParameterSource namedParameters
                = new MapSqlParameterSource(MOVIE_ID, movieId);
        Movie movie = namedParameterJdbcTemplate.queryForObject(
                movieSelectById,
                namedParameters,
                BeanPropertyRowMapper.newInstance(Movie.class));
        LOGGER.debug("getMovieById({})", movie);
        return movie;
    }

    @Override
    public final Collection<MovieEarned> moviesEarned() {
        Collection<MovieEarned> movies = namedParameterJdbcTemplate
                .query(movieCalcalulateEarn,
                        BeanPropertyRowMapper.newInstance(MovieEarned.class));
        LOGGER.debug("moviesEarned({})", movies);
        return movies;
    }

    @Override
    public final Movie addMovie(final Movie movie) throws DataAccessException {
        LOGGER.debug("addMovieIn({})", movie);
        SqlParameterSource namedParameters
                = new BeanPropertySqlParameterSource(movie);
        KeyHolder generatedKey = new GeneratedKeyHolder();
        namedParameterJdbcTemplate
                .update(insert, namedParameters, generatedKey);
        movie.setMovieId(generatedKey.getKey().intValue());
        movie.setMovieActive(true);
        LOGGER.debug("addMovieOut({})", movie);
        return movie;
    }

    @Override
    public final void updateMovie(final Movie movie) {
        LOGGER.debug("updateMovie({})", movie);
        SqlParameterSource namedParameter
                = new BeanPropertySqlParameterSource(movie);
        namedParameterJdbcTemplate.update(update, namedParameter);
    }

    @Override
    public final void deleteMovie(final int movieId) {
        LOGGER.debug("deleteMovie({})", movieId);
        namedParameterJdbcTemplate.getJdbcOperations().update(delete, movieId);
    }
}
