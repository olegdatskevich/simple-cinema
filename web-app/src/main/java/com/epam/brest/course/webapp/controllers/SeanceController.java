package com.epam.brest.course.webapp.controllers;

import com.epam.brest.course.model.dao.Seance;
import com.epam.brest.course.service.MovieService;
import com.epam.brest.course.service.SeanceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Seance Controller for web-app.
 */
@Controller
public class SeanceController {

    /**
     * Logger for Seance controller.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Service of movie.
     */
    @Autowired
    private MovieService movieService;

    /**
     * Service of seance.
     */
    @Autowired
    private SeanceService seanceService;

    /**
     * Get seances.
     * @param model - model of data
     * @return - path.
     * @throws Exception exception handling.
     */
    @GetMapping(value = "/seances")
    public final String getSeances(final Model model) throws Exception {
        LOGGER.debug("getSeances()");
        model.addAttribute("movies", movieService.moviesEarned());
        model.addAttribute("seances", seanceService.getSeances());
        return "seances";
    }

    /**
     * Get seance by dates.
     * @param fromDate - start date.
     * @param toDate - end date.
     * @param model - model of data.
     * @return - path.
     * @throws ParseException - exception of parsing date.
     */
    @GetMapping(value = "/seances/{fromDate}/{toDate}")
    public final String filterSeanceByDate(@PathVariable final String fromDate,
                                           @PathVariable final String toDate,
                                           final Model model)
            throws ParseException {
        LOGGER.debug("filterSeanceByDate({} - {})", fromDate, toDate);
        SimpleDateFormat formatDate
                = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date startDate = formatDate.parse(fromDate);
        Date endDate = formatDate.parse(toDate);
        model.addAttribute("movies", movieService.moviesEarned());
        model.addAttribute("seances", seanceService
                .filterSeanceByDate(startDate, endDate));
        return "seances";
    }

    /**
     * New seance for posting.
     * @param model - model of data.
     * @return - path.
     * @throws Exception exception handling.
     */
    @GetMapping(value = "/seance")
    public final String getAddSeance(final Model model) throws Exception {
        LOGGER.debug("getAddSeance()");
        model.addAttribute("movies", movieService.getMoviesTitles());
        model.addAttribute("seance", new Seance());
        model.addAttribute("isNew", true);
        return "seance";
    }

    /**
     * Post seance.
     * @param seanceAdd
     * @param result - validation result.
     * @param model - model of data.
     * @return - path
     * @throws Exception exception handling.
     */
    @PostMapping(value = "/seance")
    public final String addSeance(@Valid final Seance seanceAdd,
                                   final BindingResult result,
                                   final Model model) throws Exception {
        LOGGER.debug("postAddSeance({}, {})", seanceAdd, result);
        if (result.hasErrors()) {
            model.addAttribute("movies", movieService.getMoviesTitles());
            model.addAttribute("seance", seanceAdd);
            model.addAttribute("isNew", true);
            return "seance";
        } else {
            seanceService.addSeance(seanceAdd);
            return "redirect:/seances";
        }
    }

    /**
     * Get-update controller for seance.
     * @param id - seance's ID for output.
     * @param model - data for HTML-page.
     * @return - path.
     * @throws Exception exception handling.
     */
    @GetMapping(value = "/seance/{id}")
    public final String getUpdateSeance(@PathVariable final int id,
                                      final Model model) throws Exception {
        LOGGER.debug("getUpdateSeance({})", id);
        model.addAttribute("movies", movieService.getMoviesTitles());
        model.addAttribute("seance", seanceService.getSeanceById(id));
        model.addAttribute("isNew", false);
        return "seance";
    }

    /**
     * Post-update controller for seance.
     * @param seance - seance for update.
     * @param result - validation result.
     * @param model - data for HTML-page.
     * @return - path.
     * @throws Exception exception handling.
     */
    @PostMapping(value = "/seance/{id}")
    public final String updateSeance(@Valid final Seance seance,
                                      final BindingResult result,
                                      final Model model) throws Exception {
        LOGGER.debug("postUpdateSeance({}, {})", seance, result);
        if (result.hasErrors()) {
            model.addAttribute("seance", seance);
            model.addAttribute("movies", movieService.getMoviesTitles());
            model.addAttribute("isNew", false);
            return "seance";
        } else {
            seanceService.updateSeance(seance);
            return "redirect:/seances";
        }
    }

    /**
     * Delete controller for seance.
     * @param id - seance's ID for delete.
     * @return path.
     * @throws Exception exception handling.
     */
    @GetMapping(value = "/seance/{id}/delete")
    public final String deleteSeance(@PathVariable final int id)
            throws Exception {
        LOGGER.debug("deleteSeance({})", id);
        seanceService.deleteSeance(id);
        return "redirect:/seances";
    }
}
