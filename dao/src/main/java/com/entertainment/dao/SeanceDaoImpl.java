package com.entertainment.dao;

import com.entertainment.model.dao.Seance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Repository("seanceDao")
@Transactional
public class SeanceDaoImpl implements SeanceDao {

    private static final Logger LOGGER = LogManager.getLogger(SeanceDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Value("${seance.filter}")
    private String seanceFilter;


    @Override
    @SuppressWarnings("unchecked")
    public final Collection<Seance> getSeances() {
        Collection<Seance> seances = getCurrentSession().createQuery("from Seance").list();
        LOGGER.debug("getSeances({})", seances);
        return seances;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Collection<Seance> filterSeanceByDate(final Date fromDate, final Date toDate) {
        LOGGER.debug("filterSeanceByDate({}, {})", fromDate, toDate);
        Collection<Seance> seances = getCurrentSession().createQuery(seanceFilter, Seance.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
        LOGGER.debug("filteredSeances({})", seances);
        return seances;
    }

    @Override
    public final Seance getSeanceById(final int seanceId) {
        Seance seance = getCurrentSession().get(Seance.class, seanceId);
        LOGGER.debug("getSeanceById({})", seance);
        return seance;
    }

    @Override
    public final int addSeance(final Seance seance) {
        int seanceId = (int) getCurrentSession().save(seance);
        LOGGER.debug("ID:{} addSeance({})", seanceId, seance);
        return seanceId;
    }

    @Override
    public final void updateSeance(final Seance seance) {
        LOGGER.debug("updateSeance({})", seance);
        Seance seanceToUpdate = getCurrentSession().get(Seance.class, seance.getSeanceId());
        seanceToUpdate.setSeanceDate(seance.getSeanceDate());
        seanceToUpdate.setSeanceSold(seance.getSeanceSold());
        seanceToUpdate.setSeanceCost(seance.getSeanceCost());
        seanceToUpdate.setSeanceActive(seance.isSeanceActive());
        seanceToUpdate.setMovieId(seance.getMovieId());
        getCurrentSession().update(seanceToUpdate);
    }

    @Override
    public final void deleteSeance(final int seanceId) {
        LOGGER.debug("deleteSeance({})", seanceId);
        Seance seanceToDelete = getCurrentSession().get(Seance.class, seanceId);
        seanceToDelete.setSeanceActive(false);
        getCurrentSession().update(seanceToDelete);
    }
}
