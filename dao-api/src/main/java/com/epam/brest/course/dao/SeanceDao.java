package com.epam.brest.course.dao;

import com.epam.brest.course.model.dao.Seance;

import java.util.Collection;
import java.util.Date;

/**
 * Seance DAO interface.
 */
public interface SeanceDao {

    /**
     * Getting seance.
     * @return collection of seance.
     */
    Collection<Seance> getSeances();

    /**
     * Getting one seance by id.
     * @param seanceId - seance id.
     * @return seance.
     */
    Seance getSeanceById(final int seanceId);

    /**
     * Add seance in DB.
     * @param seance - seance for adding.
     * @return added seance.
     */
    Seance addSeance(final Seance seance);

    /**
     * Update seance.
     * @param seance - seance for updating
     */
    void updateSeance(final Seance seance);

    /**
     * Delete seance by seance id.
     * @param seanceId - seance id for deleting.
     */
    void deleteSeance(final int seanceId);

    /**
     * Filter seance by dates.
     * @param fromDate - start date.
     * @param toDate - end date.
     * @return collection of seance.
     */
    Collection<Seance> filterSeanceByDate(final Date fromDate,
                                          final Date toDate);
}
