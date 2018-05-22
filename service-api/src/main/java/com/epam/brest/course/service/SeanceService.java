package com.epam.brest.course.service;

import com.epam.brest.course.model.dao.Seance;

import java.util.Collection;
import java.util.Date;

/**
 * Service API for Seance.
 */
public interface SeanceService {

    /**
     * Getting all seance from DB.
     * @return - collection of seance.
     */
    Collection<Seance> getSeances();

    /**
     * Getting one seance from BD.
     * @param seanceId - id of seance
     * @return seance.
     */
    Seance getSeanceById(final int seanceId);

    /**
     * Add seance in DB.
     * @param seance - seance that need to add.
     * @return seance which was added.
     */
    Seance addSeance(final Seance seance);

    /**
     * Update seance.
     * @param seance which was updated
     */
    void updateSeance(final Seance seance);

    /**
     * Deactivate seance.
     * @param seanceId - id of seance which was deactivated.
     */
    void deleteSeance(final int seanceId);

    /**
     * Filter seances by dates.
     * @param fromDate - start date for filter.
     * @param toDate - end date for filter.
     * @return collection of seances.
     */
    Collection<Seance> filterSeanceByDate(final Date fromDate,
                                                   final Date toDate);
}
