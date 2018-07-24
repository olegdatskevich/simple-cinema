package com.entertainment.dao;

import com.entertainment.model.dao.Seance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
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
@ContextConfiguration(locations = "classpath:dao-test.xml")
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
    private static final String FROM_DATE = "2018-05-01";
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
        Seance seance = new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID);
        int addedSeance = seanceDao.addSeance(seance);
        Seance receivedSeance = seanceDao.getSeanceById(addedSeance);
        LOGGER.debug("testAddSeance({}, {})", seance, receivedSeance);
        assertEquals(seance.getSeanceDate(), receivedSeance.getSeanceDate());
        assertEquals(seance.getSeanceCost(), receivedSeance.getSeanceCost());
    }

    @Test
    public void testAddSeance() throws ParseException {
        Date date = formatDate.parse(DATE);
        Seance testSeance = new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID);
        int addedSeanceId = seanceDao.addSeance(testSeance);
        LOGGER.debug("testAddSeanceId({})", addedSeanceId);
        assertEquals(testSeance.getSeanceDate(), seanceDao.getSeanceById(addedSeanceId).getSeanceDate());
        assertEquals(testSeance.getSeanceCost(), seanceDao.getSeanceById(addedSeanceId).getSeanceCost());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddSameSeance() throws ParseException {
        Date date = formatDate.parse(DATE);
        Seance originalSeance = new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID);
        Seance duplicateSeance = new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID);
        seanceDao.addSeance(originalSeance);
        seanceDao.addSeance(duplicateSeance);
    }

    @Test
    public void testUpdateSeance() throws ParseException {
        Date date = formatDate.parse(DATE);
        Seance newSeance = new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID);
        int idUpdatedSeance =seanceDao.addSeance(newSeance);
        newSeance.setSeanceSold(30);
        newSeance.setMovieId(3);
        LOGGER.debug("testUpdateSeance({})", newSeance);
        seanceDao.updateSeance(newSeance);
        assertEquals(newSeance.getSeanceDate(), seanceDao.getSeanceById(idUpdatedSeance).getSeanceDate());
        assertEquals(newSeance.getSeanceSold(), seanceDao.getSeanceById(idUpdatedSeance).getSeanceSold());
        assertEquals(newSeance.getMovieId(), seanceDao.getSeanceById(idUpdatedSeance).getMovieId());
    }

    @Test
    public void testDeleteSeance() throws ParseException {
        Date date = formatDate.parse(DATE);
        Seance seance = new Seance(date, COST, SOLD, ACTIVE, MOVIE_ID);
        int addedSeanceId = seanceDao.addSeance(seance);
        LOGGER.debug("testDeleteSeance({})", seance);
        seanceDao.deleteSeance(addedSeanceId);
        assertFalse(seanceDao.getSeanceById(addedSeanceId).isSeanceActive());
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
        Collection<Seance> seancesByDateNull = seanceDao.filterSeanceByDate(null, null);
        Collection<Seance> allSeances = seanceDao.getSeances();
        LOGGER.debug("testFilterSeanceByNullDate({}, {})", seancesByDateNull, allSeances);
        assertNotNull(seancesByDateNull);
        assertNotNull(allSeances);
        //assertTrue(seancesByDateNull.size() == allSeances.size());
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
