package com.epam.brest.course.rest;

import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.model.dto.MovieEarned;
import com.epam.brest.course.model.dto.MoviesTitles;
import com.epam.brest.course.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-spring-test.xml"})
public class MockTestMovieController {

    @Autowired
    private MovieRestController movieRestController;

    @Autowired
    private MovieService mockMovieService;

    private MockMvc mockMvc;

    private static final int MOVIE_ID = 1;
    private static final MovieEarned MOVIE_EARNED = new MovieEarned();
    private static final MoviesTitles MOVIES_TITLES = new MoviesTitles();
    private static final Movie MOVIE = new Movie();

    @BeforeClass
    public static void beforeClass() {
        MOVIE_EARNED.setMovieId(MOVIE_ID);
        MOVIE_EARNED.setMovieName("REST_MOVIE");
        MOVIE_EARNED.setEarned(100500);
        MOVIE_EARNED.setMovieActive(true);

        MOVIES_TITLES.setMovieId(MOVIE_ID);
        MOVIES_TITLES.setMovieName("MOVIE_TITLE");

        MOVIE.setMovieId(MOVIE_ID);
        MOVIE.setMovieName("REST_MOVIE");
        MOVIE.setMovieDescription("REST_MOVIE_DESCR");
        MOVIE.setMovieActive(true);
    }

    @Before
    public void setUP() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(movieRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        verify(mockMovieService);
        reset(mockMovieService);
    }

    @Test
    public void mockTestMovies() throws Exception {
        expect(mockMovieService.moviesEarned()).andReturn(Arrays.asList(MOVIE_EARNED));
        replay(mockMovieService);

        mockMvc.perform(
                get("/movies")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].movieId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].movieName", Matchers.is("REST_MOVIE")))
                .andExpect(jsonPath("$[0].earned", Matchers.is(100500)))
                .andExpect(jsonPath("$[0].movieActive", Matchers.is(true)));
    }

    @Test
    public void mockTestMoviesTitles() throws Exception {
        expect(mockMovieService.getMoviesTitles()).andReturn(Arrays.asList(MOVIES_TITLES));
        replay(mockMovieService);

        mockMvc.perform(
                get("/moviestitles")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].movieId", Matchers.is(MOVIE_ID)))
                .andExpect(jsonPath("$[0].movieName", Matchers.is(MOVIES_TITLES.getMovieName())));
    }


    @Test
    public void mockTestMovieById() throws Exception {
        expect(mockMovieService.getMovieById(MOVIE_ID)).andReturn(MOVIE);
        replay(mockMovieService);

        mockMvc.perform(
                get("/movies/" + MOVIE_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("movieId", Matchers.is(MOVIE_ID)))
                .andExpect(jsonPath("movieName", Matchers.is("REST_MOVIE")))
                .andExpect(jsonPath("movieDescription", Matchers.is("REST_MOVIE_DESCR")));
    }

    @Test
    public void mockTestAddMovie() throws Exception {
        expect(mockMovieService.addMovie(anyObject())).andReturn(MOVIE);
        replay(mockMovieService);

        mockMvc.perform(
                post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"movieId\":1,\"movieName\":\"REST_MOVIE\",\"movieDescription\":\"REST_MOVIE_DESCR\",\"movieActive\":true}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("movieId", Matchers.is(MOVIE_ID)))
                .andExpect(jsonPath("movieName", Matchers.is("REST_MOVIE")))
                .andExpect(jsonPath("movieDescription", Matchers.is("REST_MOVIE_DESCR")));
    }

    @Test
    public void mockTestUpdateMovie() throws Exception {
        mockMovieService.updateMovie(anyObject());
        expectLastCall();
        replay(mockMovieService);

        mockMvc.perform(
                put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"movieId\":1,\"movieName\":\"REST_MOVIE\",\"movieDescription\":\"REST_MOVIE_DESCR\",\"movieActive\":true}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void mockTestDeleteMovie() throws Exception {
        mockMovieService.deleteMovie(MOVIE_ID);
        expectLastCall();
        replay(mockMovieService);

        mockMvc.perform(
                put("/movies/" + MOVIE_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
