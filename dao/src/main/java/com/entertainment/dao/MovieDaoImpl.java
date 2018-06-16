package com.entertainment.dao;

import com.entertainment.model.dao.Movie;
import com.entertainment.model.dto.MovieEarned;
import com.entertainment.model.dto.MoviesTitles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.transform.Transformer;
import java.util.Collection;

@Repository("movieDao")
public class MovieDaoImpl implements MovieDao {

    private static final Logger LOGGER = LogManager.getLogger(MovieDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    private static final String MOVIE_ID = "movieId";

    @Value("${movie.select}")
    private String moviesSelect;
    @Value("${movie.selectById}")
    private String movieSelectById;
    @Value("${movie.selectByName}")
    private String movieSelectByName;
    @Value("${movie.calculate}")
    private String movieCalcalulateEarn;
    @Value("${movie.insert}")
    private String insert;
    @Value("${movie.update}")
    private String update;
    @Value("${movie.delete}")
    private String delete;

    @Override
    @SuppressWarnings("unchecked")
    public final Collection<MoviesTitles> getMoviesTitles() {
        Collection<MoviesTitles> movies
                = getCurrentSession()
                .createQuery(moviesSelect, MoviesTitles.class)
                .getResultList();
        LOGGER.debug("getMoviesTitles({})", movies);
        return movies;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Collection<MovieEarned> moviesEarned() {
        Collection<MovieEarned> movies
                = getCurrentSession()
                .createQuery(movieCalcalulateEarn, MovieEarned.class)
                .getResultList();
//        Collection<MovieEarned> movies = getCurrentSession().createQuery(movieCalcalulateEarn).list();
        LOGGER.debug("moviesEarned({})", movies);
        return movies;
    }

    @Override
    public final Movie getMovieById(final int movieId) {
        Movie movie = (Movie) getCurrentSession().get(Movie.class, movieId);
        LOGGER.debug("getMovieById({})", movie);
        return movie;
    }

    @Override
    public final int addMovie(final Movie movie) throws DataAccessException {
        LOGGER.debug("addMovieIn({})", movie);
        int movieId = (int) getCurrentSession().save(movie);
        LOGGER.debug("addedMovieId({})", movieId);
        return movieId;
    }

    @Override
    public final void updateMovie(final Movie movie) {
        LOGGER.debug("updateMovie({})", movie);
        Movie movieToUpdate = getCurrentSession().get(Movie.class, movie.getMovieId());
        movieToUpdate.setMovieName(movie.getMovieName());
        movieToUpdate.setMovieDescription(movie.getMovieDescription());
        movieToUpdate.setMovieActive(movie.isMovieActive());
        getCurrentSession().update(movieToUpdate);
    }

    @Override
    public final void deleteMovie(final int movieId) {
        LOGGER.debug("deleteMovie({})", movieId);
        Movie movieToDelete = getCurrentSession().get(Movie.class, movieId);
        movieToDelete.setMovieActive(false);
        getCurrentSession().update(movieToDelete);
    }
}
