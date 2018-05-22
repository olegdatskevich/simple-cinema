package com.epam.brest.course.webapp.controllers;

import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.model.dto.MovieEarned;
import com.epam.brest.course.service.MovieService;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-webapp-test.xml"})
public class TestMovieController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MovieController movieController;

    @Autowired
    private MovieService mockMovieService;

    private MockMvc mockMvc;

    private static final int MOVIE_ID = 1;
    private static final MovieEarned MOVIE_EARNED = new MovieEarned();
    private static final Movie MOVIE = new Movie();

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders
                .standaloneSetup(movieController)
                .setViewResolvers(viewResolver)
                .build();

        MOVIE_EARNED.setMovieId(1);
        MOVIE_EARNED.setMovieName("REST_CLIENT_MOVIE_1");
        MOVIE_EARNED.setEarned(100501);

        MOVIE.setMovieId(1);
        MOVIE.setMovieName("REST_CLIENT_MOVIE_1");
        MOVIE.setMovieDescription("REST_CLIENT_MOVIE_DESCR_1");
        MOVIE.setMovieActive(true);

        reset(mockMovieService);
    }

    @Test
    public void testGetMovies() throws Exception {
        LOGGER.debug("testGetMovies()");
        expect(mockMovieService.moviesEarned()).andReturn(Arrays.asList(MOVIE_EARNED));
        replay(mockMovieService);

        mockMvc.perform(
                get("/movies")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("movies"));
        verify(mockMovieService);
    }

    @Test
    public void testGetAddMovie() throws Exception {
        LOGGER.debug("testGetAddMovie()");

        mockMvc.perform(
                get("/movie")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("isNew"))
                .andExpect(model().attribute("movie", new Movie()))
                .andExpect(model().attribute("isNew", true))
                .andExpect(view().name("movie"));
    }

    @Test
    public void testAddMovie() throws Exception {
        LOGGER.debug("testAddMovie()");
        expect(mockMovieService.addMovie(MOVIE)).andReturn(MOVIE);
        replay(mockMovieService);

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/movie", MOVIE))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/movies"));

        verify(mockMovieService);
    }

    @Test
    public void testGetUpdateMovie() throws Exception {
        LOGGER.debug("testGetUpdateMovie()");
        expect(mockMovieService.getMovieById(MOVIE_ID)).andReturn(MOVIE);
        replay(mockMovieService);

        mockMvc.perform(
                get("/movie/{id}", MOVIE_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("isNew"))
                .andExpect(model().attribute("movie", MOVIE))
                .andExpect(model().attribute("isNew", false))
                .andExpect(view().name("movie"));

        verify(mockMovieService);
    }

    @Test
    public void testUpdateMovie() throws Exception {
        LOGGER.debug("testUpdateMovie()");
        mockMovieService.updateMovie(MOVIE);
        expectLastCall();
        replay(mockMovieService);

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm(
                "/movie/" + MOVIE.getMovieId(), MOVIE))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/movies"));

        verify(mockMovieService);
    }

    @Test
    public void testDeleteMovie() throws Exception {
        LOGGER.debug("testUpdateMovie()");
        mockMovieService.deleteMovie(MOVIE_ID);
        expectLastCall();
        replay(mockMovieService);

        mockMvc.perform(
                get("/movie/{id}/delete", MOVIE_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/movies"));

        verify(mockMovieService);
    }
}
