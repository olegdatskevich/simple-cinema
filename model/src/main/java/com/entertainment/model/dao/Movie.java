package com.entertainment.model.dao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Movie {

    private int movieId;

    @NotEmpty(message = "Movie title can not be empty.")
    @Size (min = 2, max = 50, message
            = "Movie title must be between 2 and 50 characters.")
    private String movieName;

    @NotEmpty(message = "Movie description can not be empty.")
    @Size (min = 2, max = 50, message
            = "Movie description must be between 2 and 50 characters.")
    private String movieDescription;

    private boolean movieActive;

    public Movie() {
    }

    public Movie(final String movieName,
                 final String movieDescription,
                 final boolean movieActive) {
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieActive = movieActive;
    }

    public final Integer getMovieId() {
        return movieId;
    }

    public final void setMovieId(final Integer movieId) {
        this.movieId = movieId;
    }

    public final String getMovieName() {
        return movieName;
    }

    public final void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    public final String getMovieDescription() {
        return movieDescription;
    }

    public final void setMovieDescription(final String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public final boolean isMovieActive() {
        return movieActive;
    }

    public final void setMovieActive(final boolean movieActive) {
        this.movieActive = movieActive;
    }

    @Override
    public final String toString() {
        return "\n\tMovie{"
                + "Id=" + movieId
                + ", Name='" + movieName + '\''
                + ", Description='" + movieDescription + '\''
                + ", Active=" + movieActive
                + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Movie movie = (Movie) o;

        if (movieId != movie.movieId) {
            return false;
        }
        return movieName != null
                ? movieName.equals(movie.movieName)
                : movie.movieName == null;
    }

    @Override
    public final int hashCode() {
        int result = movieId;
        result = 31 * result + (movieName != null ? movieName.hashCode() : 0);
        return result;
    }
}
