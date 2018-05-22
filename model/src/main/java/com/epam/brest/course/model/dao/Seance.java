package com.epam.brest.course.model.dao;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

/**
 * POJO Seance.
 */
public class Seance {

    /**
     * Seance ID.
     */
    private int seanceId;

    /**
     * Date of the seance.
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "EEE MMM dd HH:mm:ss z yyyy")
    private Date seanceDate;

    /**
     * Cost of the seance.
     */
    @PositiveOrZero(message = "Cost can not be negative.")
    private int seanceCost;

    /**
     * Count of sold tickets.
     */
    @PositiveOrZero(message = "Sold can not be negative.")
    private int seanceSold;

    /**
     * Active or canceled seance.
     */
    private boolean seanceActive;

    /**
     * ID of movie for showing on the seance.
     */
    private int movieId;

    /**
     * Default constructor.
     */
    public Seance() {
    }

    /**
     * Constructor with parameters.
     * @param seanceDate - date.
     * @param seanceCost - cost.
     * @param seanceSold - count of sold tickets
     * @param seanceActive - activity.
     * @param movieId - movie ID.
     */
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
    public final int getSeanceId() {
        return seanceId;
    }

    /**
     * Setter for Seance ID
     * @param seanceId - ID.
     */
    public final void setSeanceId(final int seanceId) {
        this.seanceId = seanceId;
    }

    /**
     * Getter for Seance date.
     * @return seance date.
     */
    public final Date getSeanceDate() {
        return seanceDate;
    }

    /**
     * Setter for seanceDate.
     * @param seanceDate - seance date.
     */
    public final void setSeanceDate(final Date seanceDate) {
        this.seanceDate = seanceDate;
    }

    /**
     * Getter for Seance cost.
     * @return seance cost.
     */
    public final int getSeanceCost() {
        return seanceCost;
    }

    /**
     * Setter for seanceCost.
     * @param seanceCost - cost of seance.
     */
    public final void setSeanceCost(final int seanceCost) {
        this.seanceCost = seanceCost;
    }

    /**
     * Getter for Seance sold.
     * @return seance sold.
     */
    public final int getSeanceSold() {
        return seanceSold;
    }

    /**
     * Setter  for seanceSold.
     * @param seanceSold - sold tickets.
     */
    public final void setSeanceSold(final int seanceSold) {
        this.seanceSold = seanceSold;
    }

    /**
     * Getter for Seance activity.
     * @return activity.
     */
    public final boolean isSeanceActive() {
        return seanceActive;
    }

    /**
     * Setter for seanceActive.
     * @param seanceActive - activity of seance.
     */
    public final void setSeanceActive(final boolean seanceActive) {
        this.seanceActive = seanceActive;
    }

    /**
     * Getter for movie ID.
     * @return movie ID.
     */
    public final int getMovieId() {
        return movieId;
    }

    /**
     * Setter of movie ID.
     * @param movieId - movie ID.
     */
    public final void setMovieId(final int movieId) {
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
