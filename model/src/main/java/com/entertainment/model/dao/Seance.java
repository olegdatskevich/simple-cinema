package com.entertainment.model.dao;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Entity
@Table(name = "seance")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seanceid")
    @Getter
    @Setter
    private int seanceId;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "EEE MMM dd HH:mm:ss z yyyy")
    @Column(name = "seancedate")
    @Getter
    @Setter
    private Date seanceDate;

    @PositiveOrZero(message = "Cost can not be negative.")
    @Column(name = "seancecost")
    @Getter
    @Setter
    private int seanceCost;

    @PositiveOrZero(message = "Sold can not be negative.")
    @Column(name = "seancesold")
    @Getter
    @Setter
    private int seanceSold;

    @Column(name = "seanceactive")
    @Getter
    @Setter
    private boolean seanceActive;

    @Column(name = "movieid")
    @Getter
    @Setter
    private int movieId;

    public Seance(final Date seanceDate, final int seanceCost,
                  final int seanceSold, final boolean seanceActive,
                  final int movieId) {
        this.seanceDate = seanceDate;
        this.seanceCost = seanceCost;
        this.seanceSold = seanceSold;
        this.seanceActive = seanceActive;
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
