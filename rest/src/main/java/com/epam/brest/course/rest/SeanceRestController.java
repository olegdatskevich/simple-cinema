package com.epam.brest.course.rest;

import com.epam.brest.course.model.dao.Seance;
import com.epam.brest.course.service.SeanceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * Seance REST controller.
 */
@RestController
public class SeanceRestController {

    /**
     * Logger for SeanceRestController.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * seanceService.
     */
    @Autowired
    private SeanceService seanceService;

    /**
     * Getting list of seances for today.
     * @return collection of seances.
     */
    @GetMapping(value = "/seances")
    public final Collection<Seance> seances() {
        LOGGER.debug("REST-server seances()");
        return seanceService.getSeances();
    }

    /**
     * Filtration seances by dates.
     * @param fromDate - start date.
     * @param toDate - end date.
     * @return - collection of seances.
     * @throws ParseException - exception for parsing date.
     */
    @GetMapping(value = "/seances/{fromDate}/{toDate}")
    public final Collection<Seance> filterByDate(
            @PathVariable(value = "fromDate") final String fromDate,
            @PathVariable(value = "toDate") final String toDate)
            throws ParseException {
        LOGGER.debug("REST-server filterByDate({} - {})", fromDate, toDate);
        SimpleDateFormat formatDate
                = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
                Locale.US);
        Date startDate = formatDate.parse(fromDate);
        Date endDate = formatDate.parse(toDate);
        return seanceService.filterSeanceByDate(startDate, endDate);
    }

    /**
     * Getting seance by id.
     * @param id - id of seance.
     * @return seance.
     */
    @GetMapping(value = "/seances/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public final Seance seanceById(@PathVariable(value = "id") final int id) {
        LOGGER.debug("REST-server seanceById({})", id);
        return seanceService.getSeanceById(id);
    }

    /**
     * Add seance.
     * @param seance for adding.
     * @return added seance.
     */
    @PostMapping(value = "/seances")
    @ResponseStatus(HttpStatus.CREATED)
    public final Seance addSeance(@RequestBody final Seance seance) {
        LOGGER.debug("REST-server addSeance({})", seance);
        return seanceService.addSeance(seance);
    }

    /**
     * Update seance.
     * @param seance for updating.
     */
    @PutMapping(value = "/seances")
    public final void updateSeance(@RequestBody final Seance seance) {
        LOGGER.debug("REST-server updateSeance({})", seance);
        seanceService.updateSeance(seance);
    }

    /**
     * Delete seance.
     * @param id - id of seance.
     */
    @PutMapping(value = "/seances/{id}")
    public final void deleteSeance(@PathVariable(value = "id") final int id) {
        LOGGER.debug("REST-server deleteSeance({})", id);
        seanceService.deleteSeance(id);
    }
}
