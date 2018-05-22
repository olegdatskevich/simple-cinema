package com.entertainment.dao;

import com.entertainment.model.dao.Seance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

/**
 * Implementation of DAO layer for seance.
 */
@Repository
public class SeanceDaoImpl implements SeanceDao {

    /**
     * Logger for SeanceDaoImpl.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(SeanceDaoImpl.class);

    /**
     * NamedParameterJdbcTemplate.
     */
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Column seanceId in seance table DB.
     */
    private static final String SEANCE_ID = "seanceId";
    /**
     * Column seanceDate in seance table DB.
     */
    private static final String SEANCE_DATE = "seanceDate";
    /**
     * Column seanceCost in seance table DB.
     */
    private static final String SEANCE_COST = "seanceCost";
    /**
     * Column seanceSold in seance table DB.
     */
    private static final String SEANCE_SOLD = "seanceSold";
    /**
     * Column seanceActive in seance table DB.
     */
    private static final String SEANCE_ACTIVE = "seanceActive";
    /**
     * Column movieId in seance table DB.
     */
    private static final String MOVIE_ID = "movieId";
    /**
     * fromDate for SQL query.
     */
    private static final String FROM_DATE = "fromDate";
    /**
     * toDate for SQL query.
     */
    private static final String TO_DATE = "toDate";

    /**
     * SQL query for select all seances.
     */
    @Value("${seance.select}")
    private String seancesSelect;
    /**
     * SQL query for select seance by id.
     */
    @Value("${seance.selectById}")
    private String seanceSelectById;
    /**
     * SQL query for filter seances by date.
     */
    @Value("${seance.filter}")
    private String seanceFilter;
    /**
     * SQL query for insert seance.
     */
    @Value("${seance.insert}")
    private String insert;
    /**
     * SQL query for update seance.
     */
    @Value("${seance.update}")
    private String update;
    /**
     * SQL query for delete seance.
     */
    @Value("${seance.delete}")
    private String delete;

    @Override
    public final Collection<Seance> getSeances() {
        Collection<Seance> seances = namedParameterJdbcTemplate
                .query(seancesSelect,
                        BeanPropertyRowMapper.newInstance(Seance.class));
        LOGGER.debug("getSeances({})", seances);
        return seances;
    }

    @Override
    public final Seance getSeanceById(final int seanceId) {
        SqlParameterSource namedParameters
                = new MapSqlParameterSource(SEANCE_ID, seanceId);
        Seance seance = namedParameterJdbcTemplate.queryForObject(
                seanceSelectById,
                namedParameters,
                BeanPropertyRowMapper.newInstance(Seance.class));
        LOGGER.debug("getSeanceById({})", seance);
        return seance;
    }

    @Override
    public final Seance addSeance(final Seance seance) {
        LOGGER.debug("addSeanceIn({})", seance);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(SEANCE_DATE, seance.getSeanceDate())
                .addValue(SEANCE_COST, seance.getSeanceCost())
                .addValue(SEANCE_SOLD, seance.getSeanceSold())
                .addValue(SEANCE_ACTIVE, true)
                .addValue(MOVIE_ID, seance.getMovieId());
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insert, namedParameters,
                generatedKeyHolder);
        seance.setSeanceId(generatedKeyHolder.getKey().intValue());
        LOGGER.debug("addSeanceOut({})", seance);
        return seance;
    }

    @Override
    public final void updateSeance(final Seance seance) {
        LOGGER.debug("updateSeance({})", seance);
        SqlParameterSource namedParameter
                = new BeanPropertySqlParameterSource(seance);
        namedParameterJdbcTemplate.update(update, namedParameter);
    }

    @Override
    public final void deleteSeance(final int seanceId) {
        LOGGER.debug("deleteSeance({})", seanceId);
        namedParameterJdbcTemplate.
                getJdbcOperations().update(delete, seanceId);
    }

    @Override
    public final Collection<Seance> filterSeanceByDate(final Date fromDate,
                                                       final Date toDate) {
        LOGGER.debug("filterSeanceByDate({}, {})", fromDate, toDate);

        SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue(FROM_DATE, fromDate)
        .addValue(TO_DATE, toDate);
        Collection<Seance> seances = namedParameterJdbcTemplate.query(
                seanceFilter,
                namedParameters,
                BeanPropertyRowMapper.newInstance(Seance.class));
        LOGGER.debug("filteredSeances({})", seances);
        return seances;
    }
}
