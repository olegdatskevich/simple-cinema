package com.entertainment.client;

/**
 * ServerDataAccessException class.
 */
public class ServerDataAccessException extends RuntimeException {

    /**
     * Constructor for ServerDataAccessException.
     * @param message - for output.
     */
    public ServerDataAccessException(final String message) {
        super(message);
    }
}
