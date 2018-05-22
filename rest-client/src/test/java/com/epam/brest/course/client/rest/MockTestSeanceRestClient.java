package com.epam.brest.course.client.rest;

import com.epam.brest.course.model.dao.Seance;
import com.epam.brest.course.service.SeanceService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-test.xml"})
public class MockTestSeanceRestClient {

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private RestTemplate mockRestTemplate;

    private static final int SEANCE_ID = 1;
    private static final Seance SEANCE_1 = new Seance();
    private static final Seance SEANCE_2 = new Seance();

    @BeforeClass
    public static void beforeClass() throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        SEANCE_1.setSeanceId(1);
        SEANCE_1.setSeanceDate(formatDate.parse("2018-05-01"));
        SEANCE_1.setSeanceCost(5);
        SEANCE_1.setSeanceSold(25);
        SEANCE_1.setSeanceActive(true);
        SEANCE_1.setMovieId(1);

        SEANCE_2.setSeanceId(1);
        SEANCE_2.setSeanceDate(formatDate.parse("2018-05-02"));
        SEANCE_2.setSeanceCost(6);
        SEANCE_2.setSeanceSold(26);
        SEANCE_2.setSeanceActive(true);
        SEANCE_2.setMovieId(2);
    }

    @After
    public void tearDown() {
        verify(mockRestTemplate);
        reset(mockRestTemplate);
    }

    @Test
    public void mockTestSeancesClient() {
        Collection<Seance> seances = Arrays.asList(SEANCE_1, SEANCE_2);
        ResponseEntity entity = new ResponseEntity<>(seances, HttpStatus.OK);
        expect(mockRestTemplate.getForEntity(anyString(), anyObject()))
                .andReturn(entity);
        replay(mockRestTemplate);

        Collection<Seance> results = seanceService.getSeances();
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    public void mockTestFilterByDateClient() {
        Collection<Seance> seancesByDate = Arrays.asList(SEANCE_1, SEANCE_2);
        ResponseEntity entity = new ResponseEntity<>(seancesByDate, HttpStatus.OK);
        expect(mockRestTemplate.getForEntity(anyString(), anyObject())).andReturn(entity);
        replay(mockRestTemplate);

        Collection<Seance> results = seanceService.filterSeanceByDate(
                SEANCE_1.getSeanceDate(), SEANCE_2.getSeanceDate());
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    public void mockTestSeanceByIdClient() {
        ResponseEntity entity = new ResponseEntity<>(SEANCE_1, HttpStatus.FOUND);
        expect(mockRestTemplate.getForEntity(anyString(), anyObject()))
                .andReturn(entity);
        replay(mockRestTemplate);

        Seance result = seanceService.getSeanceById(SEANCE_1.getSeanceId());
        assertNotNull(result);
        assertEquals(SEANCE_1, result);
    }

    @Test
    public void mockTestAddSeanceClient() {
        ResponseEntity entity = new ResponseEntity<>(SEANCE_1, HttpStatus.OK);
        expect(mockRestTemplate.postForEntity(anyString(), anyObject(), anyObject()))
                .andReturn(entity);
        replay(mockRestTemplate);

        Seance result = seanceService.addSeance(SEANCE_1);
        assertNotNull(result);
        assertEquals(SEANCE_1, result);
    }

    @Test
    public void mockTestUpdateSeanceClient() {
        mockRestTemplate.put(anyString(), anyObject());
        expectLastCall();
        replay(mockRestTemplate);

        seanceService.updateSeance(SEANCE_1);
    }

    @Test
    public void mockTestDeleteSeanceClient() {
        mockRestTemplate.put(anyString(), anyObject());
        expectLastCall();
        replay(mockRestTemplate);

        seanceService.deleteSeance(SEANCE_ID);
    }
}
