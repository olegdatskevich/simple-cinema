package com.entertainment.service;

import com.entertainment.dao.SeanceDao;
import com.entertainment.model.dao.Seance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Service("seanceService")
public class SeanceServiceImpl implements SeanceService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private SeanceDao seanceDao;

    @Override
    public final Collection<Seance> getSeances() throws DataAccessException {
        Collection<Seance> seances = seanceDao.getSeances();
        LOGGER.debug("getSeances({})", seances.size());
        return seances;
    }

    @Override
    public final Seance getSeanceById(final int seanceId)
            throws DataAccessException {
        Seance seance = seanceDao.getSeanceById(seanceId);
        LOGGER.debug("getSeanceById({})", seance);
        return seance;
    }

    @Override
    @Transactional
    public int addSeance(final Seance seance) throws DataAccessException {
        int addedSeance = seanceDao.addSeance(seance);
        LOGGER.debug("addSeance({})", seance);
        return addedSeance;
    }

    @Override
    @Transactional
    public void updateSeance(final Seance seance)
            throws DataAccessException {
        seanceDao.updateSeance(seance);
        LOGGER.debug("updateSeance({})", seance);
    }

    @Override
    @Transactional
    public void deleteSeance(final int seanceId)
            throws DataAccessException {
        LOGGER.debug("deleteSeance({})", seanceId);
        seanceDao.deleteSeance(seanceId);
    }

    @Override
    public final Collection<Seance> filterSeanceByDate(final Date fromDate,
                                                         final Date toDate)
            throws DataAccessException {
        Collection<Seance> seances
                = seanceDao.filterSeanceByDate(fromDate, toDate);
        LOGGER.debug("filterSeanceByDate({}, {})", fromDate, toDate);
        return seances;
    }
}
