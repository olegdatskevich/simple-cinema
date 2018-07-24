package com.entertainment.dao;

import com.entertainment.model.dao.Movie;
import com.entertainment.model.dto.MovieEarned;
import com.entertainment.model.dto.MoviesTitles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dao-test.xml")
@Rollback
@Transactional
public class TestMovieDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger(MovieDaoImpl.class);

    @Autowired
    private MovieDao movieDao;

    @Test
    public void testGetMoviesTitles() {
        Collection<MoviesTitles> movies = movieDao.getMoviesTitles();
        LOGGER.debug("testGetMovies({})", movies);
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testMoviesEarned() {
        Collection<MovieEarned> movies = movieDao.moviesEarned();
        LOGGER.debug("testMoviesEarned({})", movies);
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testGetMovieById() {
        Movie movie = movieDao.getMovieById(2);
        LOGGER.debug("testGetMovieById({})", movie);
        assertNotNull(movie);
        assertTrue(movie.getMovieId().equals(2));
        assertTrue(movie.getMovieName().equals("Terminator"));
        assertTrue(movie.getMovieDescription().equals("Arni"));
        assertTrue(movie.isMovieActive());
    }

    @Test
    public void testAddMovie() {
        Movie testMovie = new Movie("testAddMovie","testAddDescr",true);
        int addedMovieId = movieDao.addMovie(testMovie);
        LOGGER.debug("testAddMovieId({})", addedMovieId);
        assertEquals(testMovie.getMovieName(), movieDao.getMovieById(addedMovieId).getMovieName());
        assertEquals(testMovie.getMovieDescription(), movieDao.getMovieById(addedMovieId).getMovieDescription());
        assertTrue(movieDao.getMovieById(addedMovieId).isMovieActive());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddSameMovie() {
        Movie originalMovie = new Movie("testAddSameName", "TestAddSameDescr", true);
        Movie duplicateMovie = new Movie("testAddSameName", "TestAddSameDescr", true);
        movieDao.addMovie(originalMovie);
        movieDao.addMovie(duplicateMovie);
    }

    @Test
    public void testUpdateMovie() {
        Movie movieToUpdate = new Movie("testUpdateMovie","testUpdateDescr",true);
        int idUpdatedMovie = movieDao.addMovie(movieToUpdate);
        movieToUpdate.setMovieName("NEW" + movieToUpdate.getMovieName());
        movieToUpdate.setMovieDescription("NEW" + movieToUpdate.getMovieDescription());
        LOGGER.debug("testUpdateMovie({})", movieToUpdate);
        movieDao.updateMovie(movieToUpdate);
        assertEquals(movieToUpdate.getMovieName(), movieDao.getMovieById(idUpdatedMovie).getMovieName());
        assertEquals(movieToUpdate.getMovieDescription(), movieDao.getMovieById(idUpdatedMovie).getMovieDescription());
        assertEquals(movieDao.getMovieById(idUpdatedMovie).isMovieActive(), movieToUpdate.isMovieActive());
    }

    @Test
    public void testDeleteMovie() {
        Movie movie = new Movie("testDeleteMovie","testDeleteDescr",true);
        int delMovieId = movieDao.addMovie(movie);
        LOGGER.debug("testDeleteMovie({})", movie);
        movieDao.deleteMovie(delMovieId);
        assertFalse(movieDao.getMovieById(delMovieId).isMovieActive());
        assertEquals(movie.getMovieName(), movieDao.getMovieById(delMovieId).getMovieName());
    }
}
