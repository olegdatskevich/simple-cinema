package com.epam.brest.course.dao;

import com.epam.brest.course.model.dao.Seance;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:testdb-spring.xml",
        "classpath:dao.xml",
        "classpath:dao-test.xml"})
@Rollback
@Transactional
public class TestSeanceDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DATE = "2018-05-01";
    private static final int COST = 5;
    private static final int SOLD = 25;
    private static final boolean ACTIVE = true;
    private static final int MOVIE_ID = 1;

    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final String FROM_DATE = "2018-05-02";
    private static final String TO_DATE = "2018-05-03";

    @Autowired
    private SeanceDao seanceDao;

    @Test
    public void testGetSeances() {
        Collection<Seance> seances = seanceDao.getSeances();
        LOGGER.debug("testGetSeances({})", seances);
        assertFalse(seances.isEmpty());
    }

    @Test
    public void testGetSeanceById() throws ParseException {
        Date date = formatDate.parse(DATE);
        Seance seance = seanceDao.addSeance(
                new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID));
        int addedSeance = seance.getSeanceId();
        Seance receivedSeance = seanceDao.getSeanceById(addedSeance);
        LOGGER.debug("testAddSeance({}, {})", seance, receivedSeance);
        assertTrue(seance.getSeanceDate().equals(receivedSeance.getSeanceDate()));
        assertTrue(seance.getSeanceCost() == receivedSeance.getSeanceCost());
    }

    @Test
    public void testAddSeance() throws ParseException {
        Date date = formatDate.parse(DATE);
        Collection<Seance> seances = seanceDao.getSeances();
        int sizeBefore = seances.size();
        Seance seance = new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID);
        Seance addedSeance = seanceDao.addSeance(seance);
        LOGGER.debug("testAddSeance({}, {})", seance, addedSeance);
        assertNotNull(addedSeance);
        assertTrue(seance.getSeanceDate().equals(addedSeance.getSeanceDate()));
        assertTrue((sizeBefore + 1) == seanceDao.getSeances().size());
    }

    @Test(expected = DuplicateKeyException.class)
    public void testAddSameSeance() throws ParseException {
        Date date = formatDate.parse(DATE);
        Seance seance = seanceDao.addSeance(
                new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID));
        seanceDao.addSeance(seance);
        seanceDao.addSeance(seance);
    }

    @Test
    public void testUpdateSeance() throws ParseException {
        Date date = formatDate.parse(DATE);
        Seance newSeance = seanceDao.addSeance(
                new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID));
        newSeance.setSeanceSold(30);
        newSeance.setMovieId(3);
        LOGGER.debug("testUpdateSeance({})", newSeance);
        int updatedSeanceId = newSeance.getSeanceId();
        seanceDao.updateSeance(newSeance);
        assertEquals(newSeance.getSeanceDate(),
                seanceDao.getSeanceById(updatedSeanceId).getSeanceDate());

    }

    @Test
    public void testDeleteSeance() throws ParseException {
        Date date = formatDate.parse(DATE);
        Seance seance = seanceDao.addSeance(
                new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID));
        int deletedSeanceId = seance.getSeanceId();
        LOGGER.debug("testDeleteSeance({})", seance);
        seanceDao.deleteSeance(deletedSeanceId);
        assertFalse(seanceDao.getSeanceById(deletedSeanceId).isSeanceActive());
    }

    @Test
    public void testFilterSeanceByDates() throws ParseException {
        Date fromDate = formatDate.parse(FROM_DATE);
        Date toDate = formatDate.parse(TO_DATE);
        Collection<Seance> seances = seanceDao.filterSeanceByDate(fromDate, toDate);
        LOGGER.debug("testFilterSeanceByDate({})", seances);
        assertNotNull(seances);
        assertTrue(seances.size() > 1);
    }

    @Test
    public void testFilterSeanceByNullDates() {
        Collection<Seance> seancesByDateNull
                = seanceDao.filterSeanceByDate(null, null);
        Collection<Seance> allSeances = seanceDao.getSeances();
        LOGGER.debug("testFilterSeanceByNullDate({}, {})",
                seancesByDateNull, allSeances);
        assertNotNull(seancesByDateNull);
        assertNotNull(allSeances);
        assertTrue(seancesByDateNull.size() == allSeances.size());
    }

    @Test
    public void testFilterSeanceByOneNullDate() throws ParseException {
        Date fromDate = formatDate.parse(FROM_DATE);
        Date toDate = formatDate.parse(TO_DATE);
        Collection<Seance> seancesByFromDateNull
                = seanceDao.filterSeanceByDate(null, toDate);
        Collection<Seance> seancesByToDateNull
                = seanceDao.filterSeanceByDate(fromDate, null);
        LOGGER.debug("testFilterSeancesByFromDateNull({})", seancesByFromDateNull);
        LOGGER.debug("testFilterSeancesByToDateNull({})", seancesByToDateNull);
        assertNotNull(seancesByFromDateNull);
        assertNotNull(seancesByToDateNull);
    }
}
