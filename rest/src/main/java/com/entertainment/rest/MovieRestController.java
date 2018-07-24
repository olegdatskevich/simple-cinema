package com.entertainment.rest;

import com.entertainment.model.dao.Movie;
import com.entertainment.model.dto.MovieEarned;
import com.entertainment.model.dto.MoviesTitles;
import com.entertainment.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MovieRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/movies")
    public final Collection<MovieEarned> movies() {
        LOGGER.debug("REST-server moviesEarned()");
        return movieService.moviesEarned();
    }

    @GetMapping(value = "/moviestitles")
    public final Collection<MoviesTitles> moviesTitles() {
        LOGGER.debug("REST-server moviesTitles()");
        return movieService.getMoviesTitles();
    }

    @GetMapping(value = "/movies/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public final Movie movieById(@PathVariable(value = "id") final int id) {
        LOGGER.debug("REST-server movieById()");
        return movieService.getMovieById(id);
    }

    @PostMapping(value = "/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public final int addMovie(@RequestBody final Movie movie) {
        LOGGER.debug("REST-server addMovie({})", movie);
        return movieService.addMovie(movie);
    }

    @PutMapping(value = "/movies")
    public final void updateMovie(@RequestBody final Movie movie) {
        LOGGER.debug("REST-server updateMovie({})", movie);
        movieService.updateMovie(movie);
    }

    @PutMapping(value = "/movies/{id}")
    public final void deleteMovie(@PathVariable(value = "id") final int id) {
        LOGGER.debug("REST-server deleteMovie({})", id);
        movieService.deleteMovie(id);
    }
}
