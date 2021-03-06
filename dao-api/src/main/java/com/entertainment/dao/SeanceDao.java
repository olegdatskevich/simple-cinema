package com.entertainment.dao;

import com.entertainment.model.dao.Seance;

import java.util.Collection;
import java.util.Date;

public interface SeanceDao {

    Collection<Seance> getSeances();
    Seance getSeanceById(final int seanceId);
    int addSeance(final Seance seance);
    void updateSeance(final Seance seance);
    void deleteSeance(final int seanceId);
    Collection<Seance> filterSeanceByDate(final Date fromDate,
                                          final Date toDate);
}
