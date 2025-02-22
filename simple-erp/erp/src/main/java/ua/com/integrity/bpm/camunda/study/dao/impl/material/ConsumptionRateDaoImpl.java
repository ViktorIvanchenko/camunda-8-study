package ua.com.integrity.bpm.camunda.study.dao.impl.material;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.dao.material.ConsumptionRateDao;
import ua.com.integrity.bpm.camunda.study.domain.materail.ConsumptionRate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class ConsumptionRateDaoImpl implements ConsumptionRateDao {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<ConsumptionRate> find(Long id) {
        return Optional.ofNullable(em.find(ConsumptionRate.class, id));
    }

    @Override
    public List<ConsumptionRate> findAll() {
        return em.createNamedQuery("findAllConsumptionRate", ConsumptionRate.class).getResultList();
    }

    @Override
    public ConsumptionRate create(ConsumptionRate consumptionRate) {
        em.persist(consumptionRate);
        return consumptionRate;
    }

    @Override
    public void update(ConsumptionRate consumptionRate) {
        Long id = consumptionRate.getId();
        Optional<ConsumptionRate> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(consumptionRate);
        } else {
            throw new IllegalArgumentException("Cannot find ConsumptionRate with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<ConsumptionRate> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }

    @Override
    public <S> List<ConsumptionRate> findAllForMaterial(S materialId) {
        return em.createNamedQuery("findAllRatesByMaterial", ConsumptionRate.class)
                .setParameter("materialId", materialId)
                .getResultList();
    }
}
