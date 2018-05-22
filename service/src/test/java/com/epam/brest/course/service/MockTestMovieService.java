package com.epam.brest.course.service;

import com.epam.brest.course.dao.MovieDao;
import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.model.dto.MovieEarned;
import com.epam.brest.course.model.dto.MoviesTitles;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-mock-test.xml"})
public class MockTestMovieService {

    private static final int MOVIE_ID = 1;
    private static final MoviesTitles MOVIES_TITLES
            = new MoviesTitles(2, "MoviesTitle");
    private static final Movie MOVIE
            = new Movie("TestMovie", "TestMovieDep", true);
    private static final MovieEarned MOVIE_EARNED
            = new MovieEarned(1, "Money", 100, true, true);

    @Autowired
    private MovieDao mockMovieDao;

    @Autowired
    private MovieService movieService;

    @Before
    public void setUp() {
        reset(mockMovieDao);
    }

    @After
    public void tearDown() {
        verify(mockMovieDao);
    }

    @Test
    public void mockTestGetMovies() {
        Collection<MoviesTitles> movies = Arrays.asList(MOVIES_TITLES);
        expect(mockMovieDao.getMoviesTitles()).andReturn(movies);
        replay(mockMovieDao);
        movieService.getMoviesTitles();
    }

    @Test
    public void mockTestGetMovieEarn() {
        Collection<MovieEarned> moviesEarn = Arrays.asList(MOVIE_EARNED);
        expect(mockMovieDao.moviesEarned()).andReturn(moviesEarn);
        replay(mockMovieDao);
        movieService.moviesEarned();
    }

    @Test
    public void mockTestGetMovieById() {
        expect(mockMovieDao.getMovieById(MOVIE_ID)).andReturn(new Movie());
        replay(mockMovieDao);
        movieService.getMovieById(MOVIE_ID);
    }

    @Test
    public void mockTestAddMovie() {
        expect(mockMovieDao.addMovie(MOVIE)).andReturn(new Movie());
        replay(mockMovieDao);
        movieService.addMovie(MOVIE);
    }

    @Test
    public void mockTestUpdateMovie() {
        mockMovieDao.updateMovie(MOVIE);
        expectLastCall();
        replay(mockMovieDao);
        movieService.updateMovie(MOVIE);
    }

    @Test
    public void mockTestDeleteMovie() {
        mockMovieDao.deleteMovie(MOVIE_ID);
        expectLastCall();
        replay(mockMovieDao);
        movieService.deleteMovie(MOVIE_ID);
    }
}
