package com.epam.brest.course.client.rest;

import com.epam.brest.course.client.ServerDataAccessException;
import com.epam.brest.course.model.dao.Seance;
import com.epam.brest.course.service.SeanceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Date;

/**
 * Rest client for SEANCE.
 */
@Service
public class SeanceRestClient implements SeanceService {

    /**
     * Logger for SeanceRestClient.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * URL for SEANCES.
     */
    @Value("${seanceRestClientUrl}")
    private String url;
    /**
     * For interaction with REST module.
     */
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public final Collection<Seance> getSeances()
            throws ServerDataAccessException {
        ResponseEntity<Collection> responseEntity
                = restTemplate.getForEntity(url, Collection.class);
        Collection<Seance> seances
                = (Collection<Seance>) responseEntity.getBody();
        LOGGER.debug("REST-client getSeanceById({})", responseEntity);
        return seances;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Collection<Seance> filterSeanceByDate(final Date fromDate,
                                                       final Date toDate)
            throws ServerDataAccessException {
        LOGGER.debug("REST-client filterSeanceByDate({} - {})",
                fromDate, toDate);
        ResponseEntity<Collection> responseEntity
                = restTemplate.getForEntity(
                url + "/" + fromDate + "/" + toDate,
                Collection.class);
        LOGGER.debug("REST-client filterSeanceByDate responseEntity({})",
                responseEntity);
        Collection<Seance> seances
                = (Collection<Seance>) responseEntity.getBody();
        return seances;
    }

    @Override
    public final Seance getSeanceById(final int seanceId)
            throws ServerDataAccessException {
        ResponseEntity<Seance> responseEntity = restTemplate
                .getForEntity(url + "/" + seanceId, Seance.class);
        Seance seance = responseEntity.getBody();
        LOGGER.debug("REST-client getSeances({})", seance);
        return seance;
    }

    @Override
    public final Seance addSeance(final Seance seance)
            throws ServerDataAccessException {
        ResponseEntity<Seance> responseEntity
                = restTemplate.postForEntity(url, seance, Seance.class);
        Seance result = responseEntity.getBody();
        LOGGER.debug("REST-client addSeance({})", result);
        return result;
    }

    @Override
    public final void updateSeance(final Seance seance)
            throws ServerDataAccessException {
        LOGGER.debug("REST-client updateSeance({})", seance);
        restTemplate.put(url, seance);
    }

    @Override
    public final void deleteSeance(final int seanceId)
            throws ServerDataAccessException {
        LOGGER.debug("REST-client deleteSeance({})", seanceId);
        restTemplate.put(url + "/" + seanceId, seanceId);
    }
}
