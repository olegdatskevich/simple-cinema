package com.entertainment.rest;

import com.entertainment.model.dao.Seance;
import com.entertainment.service.SeanceService;
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

@RestController
public class SeanceRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private SeanceService seanceService;

    @GetMapping(value = "/seances")
    public final Collection<Seance> seances() {
        LOGGER.debug("REST-server seances()");
        return seanceService.getSeances();
    }

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

    @GetMapping(value = "/seances/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public final Seance seanceById(@PathVariable(value = "id") final int id) {
        LOGGER.debug("REST-server seanceById({})", id);
        return seanceService.getSeanceById(id);
    }

    @PostMapping(value = "/seances")
    @ResponseStatus(HttpStatus.CREATED)
    public final int addSeance(@RequestBody final Seance seance) {
        LOGGER.debug("REST-server addSeance({})", seance);
        return seanceService.addSeance(seance);
    }

    @PutMapping(value = "/seances")
    public final void updateSeance(@RequestBody final Seance seance) {
        LOGGER.debug("REST-server updateSeance({})", seance);
        seanceService.updateSeance(seance);
    }

    @PutMapping(value = "/seances/{id}")
    public final void deleteSeance(@PathVariable(value = "id") final int id) {
        LOGGER.debug("REST-server deleteSeance({})", id);
        seanceService.deleteSeance(id);
    }
}
