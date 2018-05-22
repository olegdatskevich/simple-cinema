package com.epam.brest.course.client.rest;

import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.model.dto.MovieEarned;
import com.epam.brest.course.model.dto.MoviesTitles;
import com.epam.brest.course.service.MovieService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-test.xml"})
public class MockTestMovieRestClient {

    @Autowired
    private MovieService mockMovieService;

    @Autowired
    private RestTemplate mockRestTemplate;

    private static final int MOVIE_ID = 1;
    private static final MoviesTitles MOVIES_TITLES_1 = new MoviesTitles();
    private static final MoviesTitles MOVIES_TITLES_2 = new MoviesTitles();
    private static final MovieEarned MOVIE_EARNED_1 = new MovieEarned();
    private static final MovieEarned MOVIE_EARNED_2 = new MovieEarned();
    private static final Movie MOVIE_1 = new Movie();
    private static final Movie MOVIE_2 = new Movie();

    @BeforeClass
    public static void beforeClass() {
        MOVIE_EARNED_1.setMovieId(1);
        MOVIE_EARNED_1.setMovieName("REST_CLIENT_MOVIE_1");
        MOVIE_EARNED_1.setEarned(100501);
        MOVIE_EARNED_2.setMovieId(2);
        MOVIE_EARNED_2.setMovieName("REST_CLIENT_MOVIE_2");
        MOVIE_EARNED_2.setEarned(100502);

        MOVIES_TITLES_1.setMovieId(1);
        MOVIES_TITLES_1.setMovieName("Title1");
        MOVIES_TITLES_2.setMovieId(2);
        MOVIES_TITLES_2.setMovieName("Title2");

        MOVIE_1.setMovieId(1);
        MOVIE_1.setMovieName("REST_CLIENT_MOVIE_1");
        MOVIE_1.setMovieDescription("REST_CLIENT_MOVIE_DESCR_1");
        MOVIE_1.setMovieActive(true);
        MOVIE_2.setMovieId(2);
        MOVIE_2.setMovieName("REST_CLIENT_MOVIE_2");
        MOVIE_2.setMovieDescription("REST_CLIENT_MOVIE_DESCR_2");
        MOVIE_2.setMovieActive(true);
    }

    @After
    public void tearDown() {
        verify(mockRestTemplate);
        reset(mockRestTemplate);
    }

    @Test
    public void mockTestMoviesEarnedClient() {
        Collection moviesEarned = Arrays.asList(MOVIE_EARNED_1, MOVIE_EARNED_2);
        ResponseEntity entity = new ResponseEntity<>(moviesEarned, HttpStatus.OK);
        expect(mockRestTemplate.getForEntity(anyString(), anyObject()))
                .andReturn(entity);
        replay(mockRestTemplate);

        Collection<MovieEarned> results = mockMovieService.moviesEarned();
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    public void mockTestMoviesTitlesClient() {
        Collection moviesTitles = Arrays.asList(MOVIES_TITLES_1, MOVIES_TITLES_2);
        ResponseEntity entity = new ResponseEntity<>(moviesTitles, HttpStatus.OK);
        expect(mockRestTemplate.getForEntity(anyString(), anyObject()))
                .andReturn(entity);
        replay(mockRestTemplate);

        Collection<MoviesTitles> results = mockMovieService.getMoviesTitles();
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    public void mockTestMovieByIdClient() {
        ResponseEntity entity = new ResponseEntity<>(MOVIE_1, HttpStatus.FOUND);
        expect(mockRestTemplate.getForEntity(anyString(), anyObject()))
                .andReturn(entity);
        replay(mockRestTemplate);

        Movie result = mockMovieService.getMovieById(MOVIE_1.getMovieId());
        assertNotNull(result);
        assertEquals(MOVIE_1.getMovieName(), result.getMovieName());
    }

    @Test
    public void mockTestAddMovieClient() {
        ResponseEntity entity = new ResponseEntity<>(MOVIE_1, HttpStatus.OK);
        expect(mockRestTemplate.postForEntity(anyString(), anyObject(), anyObject()))
                .andReturn(entity);
        replay(mockRestTemplate);

        Movie result = mockMovieService.addMovie(MOVIE_1);
        assertNotNull(result);
        assertEquals(MOVIE_1.getMovieName(), result.getMovieName());
    }

    @Test
    public void mockTestUpdateMovieClient() {
        mockRestTemplate.put(anyString(), anyObject());
        expectLastCall();
        replay(mockRestTemplate);

        mockMovieService.updateMovie(MOVIE_1);
    }

    @Test
    public void mockTestDeleteMovieClient() {
        mockRestTemplate.put(anyString(), anyObject());
        expectLastCall();
        replay(mockRestTemplate);

        mockMovieService.deleteMovie(MOVIE_ID);
    }
}
