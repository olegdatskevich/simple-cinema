package com.epam.brest.course.client.rest;

import com.epam.brest.course.client.ServerDataAccessException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Custom error handler.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    /**
     * Response of handled error.
     */
    private ResponseErrorHandler errorHandler
            = new DefaultResponseErrorHandler();

    @Override
    public final boolean hasError(final ClientHttpResponse clientHttpResponse)
            throws IOException {
        return errorHandler.hasError(clientHttpResponse);
    }

    @Override
    public final void handleError(final ClientHttpResponse clientHttpResponse)
            throws IOException {
        throw new ServerDataAccessException(
                "ERROR" + clientHttpResponse.getStatusCode()
                + ": " + clientHttpResponse.getStatusText()
                + ": " + clientHttpResponse.getBody());
    }
}
