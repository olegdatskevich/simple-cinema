package com.epam.brest.course.webapp.controllers;

import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.model.dao.Seance;
import com.epam.brest.course.model.dto.MovieEarned;
import com.epam.brest.course.model.dto.MoviesTitles;
import com.epam.brest.course.service.MovieService;
import com.epam.brest.course.service.SeanceService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils.postForm;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-webapp-test.xml"})
public class TestSeanceController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private SeanceController seanceController;

    @Autowired
    private MovieService mockMovieService;

    @Autowired
    private SeanceService mockSeanceService;

    private MockMvc mockMvc;

    private static final int SEANCE_ID = 1;
    private static final Seance SEANCE = new Seance();
    private static final Movie MOVIE = new Movie();
    private static final MovieEarned MOVIE_EARNED = new MovieEarned();
    private static final MoviesTitles MOVIES_TITLES = new MoviesTitles();
    private static final String FROM_DATE = "2018-04-02-12:00:00";
    private static final String TO_DATE = "2018-04-02-20:00:00";

    @Before
    public void setUp() throws ParseException {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders
                .standaloneSetup(seanceController)
                .setViewResolvers(viewResolver)
                .build();

        SimpleDateFormat formatDate
                = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.US);
        Date date = formatDate.parse("2018-05-01-12:30:00");

        SEANCE.setSeanceId(1);
        SEANCE.setSeanceDate(new Date(date.getTime()));
        SEANCE.setSeanceCost(5);
        SEANCE.setSeanceSold(25);
        SEANCE.setSeanceActive(true);
        SEANCE.setMovieId(1);

        MOVIE_EARNED.setMovieId(1);
        MOVIE_EARNED.setMovieName("REST_CLIENT_MOVIE");
        MOVIE_EARNED.setEarned(100501);

        MOVIES_TITLES.setMovieId(1);
        MOVIES_TITLES.setMovieName("MOVIE_TITLE");

        MOVIE.setMovieId(1);
        MOVIE.setMovieName("REST_CLIENT_MOVIE");
        MOVIE.setMovieDescription("REST_CLIENT_MOVIE_DESCR");
        MOVIE.setMovieActive(true);

        reset(mockSeanceService);
        reset(mockMovieService);
    }

    @Test
    public void testGetSeances() throws Exception {
        LOGGER.debug("testGetSeances()");
        reset(mockMovieService);
        expect(mockSeanceService.getSeances()).andReturn(Arrays.asList(SEANCE));
        replay(mockSeanceService);
        expect(mockMovieService.moviesEarned()).andReturn(Arrays.asList(MOVIE_EARNED));
        replay(mockMovieService);

        mockMvc.perform(
                get("/seances")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("seances"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("seances"));
        verify(mockSeanceService);
        verify(mockMovieService);
    }

    @Test
    public void testFilterSeanceByDate() throws Exception {
        SimpleDateFormat formatDate
                = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.US);
        Date startDate = formatDate.parse(FROM_DATE);
        Date endDate = formatDate.parse(TO_DATE);
        LOGGER.debug("testFilterSeanceByDate()");
        expect(mockSeanceService.filterSeanceByDate(startDate, endDate))
                .andReturn(Arrays.asList(SEANCE));
        replay(mockSeanceService);
        expect(mockMovieService.moviesEarned()).andReturn(Arrays.asList(MOVIE_EARNED));
        replay(mockMovieService);

        mockMvc.perform(
                get("/seances/{fromDate}/{toDate}", FROM_DATE, TO_DATE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("seances"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("seances"));
        verify(mockSeanceService);
        verify(mockMovieService);
    }


    @Test
    public void testGetAddSeance() throws Exception {
        LOGGER.debug("testGetAddSeance()");
        reset(mockMovieService);
        expect(mockMovieService.getMoviesTitles())
                .andReturn(Arrays.asList(MOVIES_TITLES));
        replay(mockMovieService);

        mockMvc.perform(
                get("/seance")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("seance"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("isNew"))
                .andExpect(model().attribute("seance", new Seance()))
                .andExpect(model().attribute("isNew", true))
                .andExpect(view().name("seance"));

        verify(mockMovieService);
    }

    @Test
    public void testAddSeance() throws Exception {
        LOGGER.debug("testAddSeance({})", SEANCE);
        expect(mockSeanceService.addSeance(SEANCE)).andReturn(SEANCE);
        replay(mockSeanceService);

        mockMvc.perform(postForm("/seance", SEANCE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/seances"));

        verify(mockSeanceService);
    }

    @Test
    public void testGetUpdateSeance() throws Exception {
        LOGGER.debug("testGetUpdateSeance()");
        expect(mockSeanceService.getSeanceById(SEANCE_ID)).andReturn(SEANCE);
        replay(mockSeanceService);
        reset(mockMovieService);
        expect(mockMovieService.getMoviesTitles()).andReturn(Arrays.asList(MOVIES_TITLES));
        replay(mockMovieService);

        mockMvc.perform(
                get("/seance/{id}", SEANCE_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("seance"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("isNew"))
                .andExpect(model().attribute("seance", SEANCE))
                .andExpect(model().attribute("isNew", false))
                .andExpect(view().name("seance"));

        verify(mockSeanceService);
        verify(mockMovieService);
    }

    @Test
    public void testUpdateSeance() throws Exception {
        LOGGER.debug("testUpdateSeance()");
        mockSeanceService.updateSeance(SEANCE);
        expectLastCall();
        replay(mockSeanceService);

        mockMvc.perform(postForm("/seance/" + SEANCE.getMovieId(), SEANCE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/seances"));

        verify(mockSeanceService);
    }

    @Test
    public void testDeleteSeance() throws Exception {
        LOGGER.debug("testDeleteSeance()");
        mockSeanceService.deleteSeance(SEANCE_ID);
        expectLastCall();
        replay(mockSeanceService);

        mockMvc.perform(
                get("/seance/{id}/delete", SEANCE_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/seances"));

        verify(mockSeanceService);
    }
}
