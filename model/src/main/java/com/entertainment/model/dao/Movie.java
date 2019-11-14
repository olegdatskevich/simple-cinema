package com.entertainment.model.dao;

import com.entertainment.model.dto.MovieEarned;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "movie")
@SqlResultSetMapping
        (name = "mappingMovieEarn", classes = {@ConstructorResult(targetClass = MovieEarned.class,
                columns = {@ColumnResult(name = "movieId", type = Integer.class),
                           @ColumnResult(name = "movieName", type = String.class),
                           @ColumnResult(name = "movieActive", type = Boolean.class),
                           @ColumnResult(name = "earned", type = Integer.class),
                           @ColumnResult(name = "haveSeance", type = Boolean.class)}
        )}
        )
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieid")
    @Getter
    @Setter
    private int movieId;

    @NotEmpty(message = "Movie title can not be empty.")
    @Size (min = 2, max = 50, message = "Movie title must be between 2 and 50 characters.")
    @Column(name = "moviename")
    @Getter
    @Setter
    private String movieName;

    @NotEmpty(message = "Movie description can not be empty.")
    @Size (min = 2, max = 50, message = "Movie description must be between 2 and 50 characters.")
    @Column(name = "moviedescription")
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private String movieDescription;

    @Column(name = "movieactive")
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private boolean movieActive;

    public Movie(final String movieName,
                 final String movieDescription,
                 final boolean movieActive) {
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieActive = movieActive;
    }
}
