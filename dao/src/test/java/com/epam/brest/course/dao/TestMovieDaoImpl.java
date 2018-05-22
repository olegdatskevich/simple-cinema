package com.epam.brest.course.dao;

import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.model.dto.MovieEarned;
import com.epam.brest.course.model.dto.MoviesTitles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:testdb-spring.xml",
        "classpath:dao.xml",
        "classpath:dao-test.xml"})
@Rollback
@Transactional
public class TestMovieDaoImpl {

    private static final Logger LOGGER
            = LogManager.getLogger(MovieDaoImpl.class);

    @Autowired
    private MovieDao movieDao;

    @Test
    public void testGetMoviesTitles() {
        Collection<MoviesTitles> movies = movieDao.getMoviesTitles();
        LOGGER.debug("testGetMovies({})", movies);
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
    public void testMoviesEarned() {
        Collection<MovieEarned> movies = movieDao.moviesEarned();
        LOGGER.debug("testMoviesEarned({})", movies);
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testAddMovie() {
        Collection<MoviesTitles> movies = movieDao.getMoviesTitles();
        int sizeBeforeAdd = movies.size();
        Movie newMovie = movieDao.addMovie(new Movie(
                "testAddMovie",
                "testAddDescr",
                true));
        int addedMovieId = newMovie.getMovieId();
        LOGGER.debug("testAddMovie({})", newMovie);
        assertEquals(newMovie.getMovieName(),
                movieDao.getMovieById(addedMovieId).getMovieName());
        assertEquals(newMovie.getMovieDescription(),
                movieDao.getMovieById(addedMovieId).getMovieDescription());
        assertTrue(movieDao.getMovieById(addedMovieId).isMovieActive());
        assertTrue((sizeBeforeAdd + 1) == movieDao.getMoviesTitles().size());
    }

    @Test(expected = DuplicateKeyException.class)
    public void testAddSameMovie() {
        Movie testMovie = new Movie(
                "testAddSameName",
                "TestAddSameDescr",
                true);
        movieDao.addMovie(testMovie);
        movieDao.addMovie(testMovie);
    }

    @Test
    public void testUpdateMovie() {
        Movie newMovie = movieDao.addMovie(new Movie(
                "testUpdateMovie",
                "testUpdateDescr",
                true));
        newMovie.setMovieName("NEW" + newMovie.getMovieName());
        newMovie.setMovieDescription("NEW" + newMovie.getMovieDescription());
        LOGGER.debug("testUpdateMovie({})", newMovie);
        int updatedMovieId = newMovie.getMovieId();
        movieDao.updateMovie(newMovie);
        assertEquals(newMovie.getMovieName(),
                movieDao.getMovieById(updatedMovieId).getMovieName());
        assertEquals(newMovie.getMovieDescription(),
                movieDao.getMovieById(updatedMovieId).getMovieDescription());
        assertEquals(movieDao.getMovieById(updatedMovieId).isMovieActive(),
                newMovie.isMovieActive());
    }

    @Test
    public void testDeleteMovie() {
        Movie movie = new Movie("testDeleteMovie",
                "testDeleteDescr", true);
        movie = movieDao.addMovie(movie);
        int delMovieId = movie.getMovieId();
        LOGGER.debug("testDeleteMovie({})", movie);
        movieDao.deleteMovie(movie.getMovieId());
        assertFalse(movieDao.getMovieById(delMovieId).isMovieActive());
        assertEquals(movie.getMovieName(),
                movieDao.getMovieById(delMovieId).getMovieName());
    }
}
