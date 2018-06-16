package com.entertainment.model.dto;

/**
 * DTO for Movie earning.
 */
public class MoviesTitles {

    private int movieId;
    private String movieName;

    public MoviesTitles() {
    }

    public MoviesTitles(final int movieId, final String movieName) {
        this.movieId = movieId;
        this.movieName = movieName;
    }

    public final int getMovieId() {
        return movieId;
    }

    public final void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    public final String getMovieName() {
        return movieName;
    }

    public final void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    @Override
    public final String toString() {
        return "\n\tMoviesTitles{"
                + "Id=" + movieId
                + ", Name='" + movieName + '\''
                + '}';
    }
}
