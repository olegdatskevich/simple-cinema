package com.epam.brest.course.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * HandleException method handle Exception.
     *
     * @param ex Exception
     * @return exception message.
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String HandleException(EmptyResultDataAccessException ex) {
        LOGGER.debug("Exception: {}", ex.getMessage());

        return ex.getLocalizedMessage();
    }
}
