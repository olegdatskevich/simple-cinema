package com.entertainment.model.dao;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

/**
 * POJO Seance.
 */
@Entity
@Table(name = "seance")
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seanceid")
    private int seanceId;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "EEE MMM dd HH:mm:ss z yyyy")
    @Column(name = "seancedate")
    private Date seanceDate;

    @PositiveOrZero(message = "Cost can not be negative.")
    @Column(name = "seancecost")
    private int seanceCost;

    @PositiveOrZero(message = "Sold can not be negative.")
    @Column(name = "seancesold")
    private int seanceSold;

    @Column(name = "seanceactive")
    private boolean seanceActive;

    @Column(name = "movieid")
    private int movieId;

    public Seance() {
    }

    public Seance(final Date seanceDate, final int seanceCost,
                  final int seanceSold, final boolean seanceActive,
                  final int movieId) {
        this.seanceDate = seanceDate;
        this.seanceCost = seanceCost;
        this.seanceSold = seanceSold;
        this.seanceActive = seanceActive;
        this.movieId = movieId;
    }

    /**
     * Getter for Seance ID.
     * @return seance ID.
     */
    public int getSeanceId() {
        return seanceId;
    }

    /**
     * Setter for Seance ID
     * @param seanceId - ID.
     */
    public void setSeanceId(final int seanceId) {
        this.seanceId = seanceId;
    }

    /**
     * Getter for Seance date.
     * @return seance date.
     */
    public Date getSeanceDate() {
        return seanceDate;
    }

    /**
     * Setter for seanceDate.
     * @param seanceDate - seance date.
     */
    public void setSeanceDate(final Date seanceDate) {
        this.seanceDate = seanceDate;
    }

    /**
     * Getter for Seance cost.
     * @return seance cost.
     */
    public int getSeanceCost() {
        return seanceCost;
    }

    /**
     * Setter for seanceCost.
     * @param seanceCost - cost of seance.
     */
    public void setSeanceCost(final int seanceCost) {
        this.seanceCost = seanceCost;
    }

    /**
     * Getter for Seance sold.
     * @return seance sold.
     */
    public int getSeanceSold() {
        return seanceSold;
    }

    /**
     * Setter  for seanceSold.
     * @param seanceSold - sold tickets.
     */
    public void setSeanceSold(final int seanceSold) {
        this.seanceSold = seanceSold;
    }

    /**
     * Getter for Seance activity.
     * @return activity.
     */
    public boolean isSeanceActive() {
        return seanceActive;
    }

    /**
     * Setter for seanceActive.
     * @param seanceActive - activity of seance.
     */
    public void setSeanceActive(final boolean seanceActive) {
        this.seanceActive = seanceActive;
    }

    /**
     * Getter for movie ID.
     * @return movie ID.
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Setter of movie ID.
     * @param movieId - movie ID.
     */
    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    @Override
    public final String toString() {
        return "\n\tSeance{"
                + "Id=" + seanceId
                + ", Date=" + seanceDate
                + ", Cost=" + seanceCost
                + ", Sold=" + seanceSold
                + ", Active=" + seanceActive
                + ", movieId=" + movieId
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

        Seance seance = (Seance) o;

        if (seanceId != seance.seanceId) {
            return false;
        }
        return seanceDate != null
                ? seanceDate.equals(seance.seanceDate)
                : seance.seanceDate == null;
    }

    @Override
    public final int hashCode() {
        int result = seanceId;
        result = 31 * result + (seanceDate != null ? seanceDate.hashCode() : 0);
        return result;
    }
}
