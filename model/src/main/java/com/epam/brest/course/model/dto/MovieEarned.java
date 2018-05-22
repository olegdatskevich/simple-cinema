package com.epam.brest.course.model.dto;

/**
 * DTO for Movie earning.
 */
public class MovieEarned {
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
    private int earned;
    /**
     *
     */
    private boolean movieActive;

    private boolean haveSeance;

    /**
     *
     */
    public MovieEarned() {
    }

    /**
     *
     * @param movieId
     * @param movieName
     * @param earned
     * @param movieActive
     */
    public MovieEarned(final int movieId, final String movieName,
                       final int earned, final boolean movieActive,
                       final boolean haveSeance) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.earned = earned;
        this.movieActive = movieActive;
        this.haveSeance = haveSeance;
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

    /**
     *
     * @return
     */
    public final int getEarned() {
        return earned;
    }

    /**
     *
     * @param earned
     */
    public final void setEarned(final int earned) {
        this.earned = earned;
    }

    /**
     *
     * @return
     */
    public final boolean isMovieActive() {
        return movieActive;
    }

    /**
     *
     * @param movieActive
     */
    public final void setMovieActive(final boolean movieActive) {
        this.movieActive = movieActive;
    }

    /**
     * Getter for haveSeance.
     * @return will be a seance.
     */
    public final boolean isHaveSeance() {
        return haveSeance;
    }

    /**
     * Setter for haveSeance.
     * @param haveSeance movie has seance?
     */
    public final void setHaveSeance(final boolean haveSeance) {
        this.haveSeance = haveSeance;
    }

    @Override
    public final String toString() {
        return "\n\tMovieEarned{"
                + "Id=" + movieId
                + ", Name='" + movieName + '\''
                + ", Earned=" + earned
                + ", Active=" + movieActive
                + ", Have seance=" + haveSeance
                + '}';
    }
}
