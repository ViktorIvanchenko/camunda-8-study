package ua.com.integrity.bpm.camunda.study.dao.impl.equipment;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.dao.equipment.SupplyRateDao;
import ua.com.integrity.bpm.camunda.study.domain.equipment.SupplyRate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class SupplyRateDaoImpl implements SupplyRateDao {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<SupplyRate> find(Long id) {
        return Optional.ofNullable(em.find(SupplyRate.class, id));
    }

    @Override
    public List<SupplyRate> findAll() {
        return em.createNamedQuery("findAllSupplyRate", SupplyRate.class).getResultList();
    }

    @Override
    public SupplyRate create(SupplyRate supplyRate) {
        em.persist(supplyRate);
        return supplyRate;
    }

    @Override
    public void update(SupplyRate supplyRate) {
        Long id = supplyRate.getId();
        Optional<SupplyRate> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(supplyRate);
        } else {
            throw new IllegalArgumentException("Cannot find supplyRate with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<SupplyRate> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }

    @Override
    public <S> List<SupplyRate> findRatesForType(S typeId) {
        return em.createNamedQuery("findAllSupplyRateForType", SupplyRate.class)
                .setParameter("typeId", typeId)
                .getResultList();
    }
}
