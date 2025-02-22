package ua.com.integrity.bpm.camunda.study.dao.impl.equipment;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.equipment.Maintenance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class MaintenanceDaoImpl implements GenericDao<Maintenance, Long> {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<Maintenance> find(Long id) {
        return Optional.ofNullable(em.find(Maintenance.class, id));
    }

    @Override
    public List<Maintenance> findAll() {
        return em.createNamedQuery("findAllMaintenance", Maintenance.class).getResultList();
    }

    @Override
    public Maintenance create(Maintenance maintenance) {
        em.persist(maintenance);
        return maintenance;
    }

    @Override
    public void update(Maintenance maintenance) {
        Long id = maintenance.getId();
        Optional<Maintenance> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(maintenance);
        } else {
            throw new IllegalArgumentException("Cannot find maintenance with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Maintenance> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }
}
