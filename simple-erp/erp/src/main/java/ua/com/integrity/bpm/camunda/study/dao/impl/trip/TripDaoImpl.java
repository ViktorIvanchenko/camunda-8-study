package ua.com.integrity.bpm.camunda.study.dao.impl.trip;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.trip.Trip;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class TripDaoImpl implements GenericDao<Trip, Long> {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<Trip> find(Long id) {
        return Optional.ofNullable(em.find(Trip.class, id));
    }

    @Override
    public List<Trip> findAll() {
        return em.createNamedQuery("findAllTrips", Trip.class).getResultList();
    }

    @Override
    public Trip create(Trip trip) {
        em.persist(trip);
        return trip;
    }

    @Override
    public void update(Trip trip) {
        Long id = trip.getId();
        Optional<Trip> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(trip);
        } else {
            throw new IllegalArgumentException("Cannot find Trip with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Trip> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }
}
