package com.epam.brest.course.webapp.controllers;

import com.epam.brest.course.model.dao.Movie;
import com.epam.brest.course.service.MovieService;
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

/**
 * Movies Controller for web-app.
 */
@Controller
public class MovieController {

    /**
     * Logger for Movie controller.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Service of movies.
     */
    @Autowired
    private MovieService movieService;

    /**
     * Get movies list.
     * @param model model of data.
     * @return url path.
     */
    @GetMapping(value = "/movies")
    public final String getMovies(final Model model) {
        LOGGER.debug("getMovies({})", model);
        model.addAttribute("movies", movieService.moviesEarned());
        return "movies";
    }

    /**
     * New movie for post.
     * @param model model of data.
     * @return url path.
     */
    @GetMapping(value = "/movie")
    public final String getAddMovie(final Model model) {
        LOGGER.debug("GetAddMovie({})", model);
        model.addAttribute("movie", new Movie());
        model.addAttribute("isNew", true);
        return "movie";

    }

    /**
     * Post movie.
     * @param movie - new movie.
     * @param result - validation result
     * @param model - model of data.
     * @return url path.
     */
    @PostMapping(value = "/movie")
    public final String addMovie(@Valid final Movie movie,
                                    final BindingResult result,
                                    final Model model) {
        LOGGER.debug("PostAddMovie({}, {})", movie, result);
        if (result.hasErrors()) {
            model.addAttribute("movie", movie);
            model.addAttribute("isNew", true);
            return "movie";
        } else {
            movieService.addMovie(movie);
            return "redirect:/movies";
        }
    }

    /**
     * Get-update controller for movie.
     * @param id - movie ID for update.
     * @param model - model of data.
     * @return url path.
     */
    @GetMapping(value = "/movie/{id}")
    public final String getUpdateMovie(
            @PathVariable final int id, final Model model) {
        LOGGER.debug("GetUpdateMovie({}, {})", id, model);
        model.addAttribute("movie", movieService.getMovieById(id));
        model.addAttribute("isNew", false);
        return "movie";
    }

    /**
     * Post update movie.
     * @param movie - movie for update.
     * @param result - validation result.
     * @param model model of data.
     * @return url path.
     */
    @PostMapping(value = "/movie/{id}")
    public final String updateMovie(@Valid final Movie movie,
                                    final BindingResult result,
                                    final Model model) {
        LOGGER.debug("PostUpdateMovie({}, {})", movie, result);
        if (result.hasErrors()) {
            model.addAttribute("movie", movie);
            model.addAttribute("isNew", false);
            return "movie";
        } else {
            movieService.updateMovie(movie);
            return "redirect:/movies";
        }
    }

    /**
     * Remove movie.
     * @param id - id of movie for delete.
     * @return url path.
     * @throws Exception exception handling.
     */
    @GetMapping(value = "/movie/{id}/delete")
    public final String deleteMovie(@PathVariable final int id)
            throws Exception {
        LOGGER.debug("deleteMovie({})", id);
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }
}
