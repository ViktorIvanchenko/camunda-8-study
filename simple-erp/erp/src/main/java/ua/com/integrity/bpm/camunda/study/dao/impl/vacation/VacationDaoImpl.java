package ua.com.integrity.bpm.camunda.study.dao.impl.vacation;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.vacation.Vacation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class VacationDaoImpl implements GenericDao<Vacation, Long> {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<Vacation> find(Long id) {
        return Optional.ofNullable(em.find(Vacation.class, id));
    }

    @Override
    public List<Vacation> findAll() {
        return em.createNamedQuery("findAllVacation", Vacation.class).getResultList();
    }

    @Override
    public Vacation create(Vacation vacation) {
        em.persist(vacation);
        return vacation;
    }

    @Override
    public void update(Vacation vacation) {
        Long id = vacation.getId();
        Optional<Vacation> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(vacation);
        } else {
            throw new IllegalArgumentException("Cannot find vacation with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Vacation> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }
}
