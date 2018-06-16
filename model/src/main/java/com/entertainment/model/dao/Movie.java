package com.entertainment.model.dao;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieid")
    private int movieId;

    @NotEmpty(message = "Movie title can not be empty.")
    @Size (min = 2, max = 50, message
            = "Movie title must be between 2 and 50 characters.")
    @Column(name = "moviename")
    private String movieName;

    @NotEmpty(message = "Movie description can not be empty.")
    @Size (min = 2, max = 50, message
            = "Movie description must be between 2 and 50 characters.")
    @Column(name = "moviedescription")
    private String movieDescription;

    @Column(name = "movieactive")
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

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(final Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(final String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public boolean isMovieActive() {
        return movieActive;
    }

    public void setMovieActive(final boolean movieActive) {
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
