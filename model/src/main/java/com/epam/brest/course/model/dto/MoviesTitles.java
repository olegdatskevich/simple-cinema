package com.epam.brest.course.model.dto;

/**
 * DTO for Movie earning.
 */
public class MoviesTitles {
    /**
     *
     */
    private int movieId;
    /**
     *
     */
    private String movieName;

    /**
     *
     */
    public MoviesTitles() {
    }

    /**
     *
     * @param movieId
     * @param movieName
     */
    public MoviesTitles(final int movieId, final String movieName) {
        this.movieId = movieId;
        this.movieName = movieName;
    }

    /**
     *
     * @return
     */
    public final int getMovieId() {
        return movieId;
    }

    /**
     *
     * @param movieId
     */
    public final void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    /**
     *
     * @return
     */
    public final String getMovieName() {
        return movieName;
    }

    /**
     *
     * @param movieName
     */
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
