package ua.com.integrity.bpm.camunda.study.dao.impl;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.Position;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class PositionDaoImpl implements GenericDao<Position, Long> {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<Position> find(Long id) {
        return Optional.ofNullable(em.find(Position.class, id));
    }

    @Override
    public List<Position> findAll() {
        return em.createNamedQuery("findAllPosition", Position.class).getResultList();
    }

    @Override
    public Position create(Position position) {
        em.persist(position);
        return position;
    }

    @Override
    public void update(Position position) {
        Long id = position.getId();
        Optional<Position> positionFromDb = this.find(id);
        if (positionFromDb.isPresent()) {
            em.merge(position);
        } else {
            throw new IllegalArgumentException("Cannot find position with id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Position> position = this.find(id);
        if (position.isPresent()) {
            em.remove(position.get());
        }
    }
}
