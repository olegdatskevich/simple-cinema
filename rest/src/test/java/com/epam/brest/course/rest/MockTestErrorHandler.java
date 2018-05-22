package com.epam.brest.course.rest;

import com.epam.brest.course.service.MovieService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-spring-test.xml"})
@Rollback
public class MockTestErrorHandler {

    @Autowired
    private RestErrorHandler restErrorHandler;

    @Autowired
    private MovieRestController movieRestController;

    @Autowired
    private MovieService mockMovieService;

    private MockMvc mockMvc;

    private static final int MOVIE_ID = 500;

    @Before
    public void testSetUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieRestController)
                .setControllerAdvice(restErrorHandler)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        verify(mockMovieService);
        reset(mockMovieService);
    }

    @Test
    public void handleException() throws Exception {
        expect(mockMovieService.getMovieById(anyInt()))
                .andThrow(new EmptyResultDataAccessException("There is no such film.", 1));
        replay(mockMovieService);


        mockMvc.perform(
                get("/movies/" + MOVIE_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "\"There is no such film.\""));
    }
}
