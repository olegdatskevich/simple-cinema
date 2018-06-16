package com.entertainment.dao;

import com.entertainment.model.dao.Seance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

/**
 * Implementation of DAO layer for seance.
 */
@Repository("seanceDao")
public class SeanceDaoImpl implements SeanceDao {

    private static final Logger LOGGER = LogManager.getLogger(SeanceDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    private static final String SEANCE_ID = "seanceId";
    private static final String SEANCE_DATE = "seanceDate";
    private static final String SEANCE_COST = "seanceCost";
    private static final String SEANCE_SOLD = "seanceSold";
    private static final String SEANCE_ACTIVE = "seanceActive";
    private static final String MOVIE_ID = "movieId";
    private static final String FROM_DATE = "fromDate";
    private static final String TO_DATE = "toDate";

    @Value("${seance.select}")
    private String seancesSelect;
    @Value("${seance.selectById}")
    private String seanceSelectById;
    @Value("${seance.selectByDate}")
    private String seanceSelectByDate;
    @Value("${seance.filter}")
    private String seanceFilter;
    @Value("${seance.insert}")
    private String insert;
    @Value("${seance.update}")
    private String update;
    @Value("${seance.delete}")
    private String delete;

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
    public Seance getSeanceByDate(final Date seanceDate) {
        Query query= getCurrentSession().createQuery("from Seance where seanceDate = :seanceDate");
        query.setParameter("seanceDate", seanceDate);
        Seance seance = (Seance) query.uniqueResult();
        LOGGER.debug("getSeanceByDate({})", seance);
        return seance;
    }

    @Override
    public final Seance addSeance(final Seance seance) {
        LOGGER.debug("addSeanceIn({})", seance);
        getCurrentSession().save(seance);
        Seance addedSeance = getSeanceByDate(seance.getSeanceDate());
        LOGGER.debug("addSeanceOut({})", addedSeance);
        return addedSeance;
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
