package com.epam.brest.course.model.dao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * POJO Movie.
 */
public class Movie {

    /**
     * Movie ID.
     */
    private int movieId;

    /**
     * Movie title.
     */
    @NotEmpty(message = "Movie title can not be empty.")
    @Size (min = 2, max = 50, message
            = "Movie title must be between 2 and 50 characters.")
    private String movieName;

    /**
     * Movie description.
     */
    @NotEmpty(message = "Movie description can not be empty.")
    @Size (min = 2, max = 50, message
            = "Movie description must be between 2 and 50 characters.")
    private String movieDescription;

    /**
     * Delete or no movie.
     */
    private boolean movieActive;

    /**
     * Default constructor.
     */
    public Movie() {
    }

    /**
     * Constructor with 3 param without ID.
     * @param movieName - title.
     * @param movieDescription - description.
     * @param movieActive - active?
     */
    public Movie(final String movieName,
                 final String movieDescription,
                 final boolean movieActive) {
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieActive = movieActive;
    }

    /**
     * Getter for movieId.
     * @return movieId.
     */
    public final Integer getMovieId() {
        return movieId;
    }

    /**
     * Setter for movieId.
     * @param movieId - movie ID.
     */
    public final void setMovieId(final Integer movieId) {
        this.movieId = movieId;
    }

    /**
     * Getter for movieName.
     * @return movie title.
     */
    public final String getMovieName() {
        return movieName;
    }

    /**
     * Setter for title.
     * @param movieName movie title.
     */
    public final void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    /**
     * Getter for movieDescription.
     * @return movieDescription.
     */
    public final String getMovieDescription() {
        return movieDescription;
    }

    /**
     * Setter for movieDescription.
     * @param movieDescription - description of movie.
     */
    public final void setMovieDescription(final String movieDescription) {
        this.movieDescription = movieDescription;
    }

    /**
     * Getter for movieActive.
     * @return - activity of movie.
     */
    public final boolean isMovieActive() {
        return movieActive;
    }

    /**
     * Setter for movieActive.
     * @param movieActive - activity of movie.
     */
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
