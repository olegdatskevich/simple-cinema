package com.epam.brest.course.service;

import com.epam.brest.course.dao.SeanceDao;
import com.epam.brest.course.model.dao.Seance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-mock-test.xml"})
public class MockTestSeanceService {

    @Autowired
    private SeanceDao mockSeanceDao;

    @Autowired
    private SeanceService seanceService;

    private static final int Seance_ID = 1;
    private static final Seance Seance = new Seance();

    @Before
    public void setUp() throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatDate.parse("2018-05-01");
        Seance.setSeanceDate(date);
        Seance.setSeanceCost(5);
        Seance.setSeanceSold(25);
        Seance.setSeanceActive(true);
        Seance.setMovieId(1);
    }

    @After
    public void tearDown() {
        verify(mockSeanceDao);
        reset(mockSeanceDao);
    }

    @Test
    public void mockTestGetSeance() {
        Collection<Seance> seances = Arrays.asList(Seance);
        expect(mockSeanceDao.getSeances()).andReturn(seances);
        replay(mockSeanceDao);
        seanceService.getSeances();
    }

    @Test
    public void mockTestGetSeanceById() {
        expect(mockSeanceDao.getSeanceById(Seance_ID)).andReturn(new Seance());
        replay(mockSeanceDao);
        seanceService.getSeanceById(Seance_ID);
    }

    @Test
    public void mockTestAddSeance() {
        expect(mockSeanceDao.addSeance(Seance)).andReturn(new Seance());
        replay(mockSeanceDao);
        seanceService.addSeance(Seance);
    }

    @Test
    public void mockTestUpdateSeance() {
        mockSeanceDao.updateSeance(Seance);
        expectLastCall();
        replay(mockSeanceDao);
        seanceService.updateSeance(Seance);
    }

    @Test
    public void mockTestDeleteSeance() {
        mockSeanceDao.deleteSeance(Seance_ID);
        expectLastCall();
        replay(mockSeanceDao);
        seanceService.deleteSeance(Seance_ID);
    }

    @Test
    public void mockTestFilterSeanceByDates() throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = formatDate.parse("2018-04-02");
        Date toDate = formatDate.parse("2018-04-03");
        Collection<Seance> seances = Arrays.asList(Seance);
        expect(mockSeanceDao.filterSeanceByDate(fromDate, toDate)).andReturn(seances);
        replay(mockSeanceDao);
        seanceService.filterSeanceByDate(fromDate, toDate);
    }
}
