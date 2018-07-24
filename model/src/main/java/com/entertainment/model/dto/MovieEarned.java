package com.entertainment.model.dto;

public class MovieEarned {

    private int movieId;
    private String movieName;
    private boolean movieActive;
    private int earned;
    private boolean haveSeance;

    public MovieEarned() {
    }

//    public MovieEarned(final int movieId, final String movieName,
//                       final int earned, final boolean movieActive,
//                       final boolean haveSeance) {
//        this.movieId = movieId;
//        this.movieName = movieName;
//        this.earned = earned;
//        this.movieActive = movieActive;
//        this.haveSeance = haveSeance;
//    }

    public MovieEarned(int movieId, String movieName, boolean movieActive, int earned, boolean haveSeance) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieActive = movieActive;
        this.earned = earned;
        this.haveSeance = haveSeance;
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

    public final int getEarned() {
        return earned;
    }

    public final void setEarned(final int earned) {
        this.earned = earned;
    }

    public final boolean isMovieActive() {
        return movieActive;
    }

    public final void setMovieActive(final boolean movieActive) {
        this.movieActive = movieActive;
    }

    public final boolean isHaveSeance() {
        return haveSeance;
    }

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
