package ua.com.integrity.bpm.camunda.study.dao.impl.material;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.dao.material.BatchOfMaterialsDao;
import ua.com.integrity.bpm.camunda.study.domain.materail.BatchOfMaterial;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class BatchOfMaterialDaoImpl implements BatchOfMaterialsDao {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<BatchOfMaterial> find(Long id) {
        return Optional.ofNullable(em.find(BatchOfMaterial.class, id));
    }

    @Override
    public List<BatchOfMaterial> findAll() {
        return em.createNamedQuery("findAllBatchOfMaterial", BatchOfMaterial.class).getResultList();
    }

    @Override
    public BatchOfMaterial create(BatchOfMaterial batchOfMaterial) {
        em.persist(batchOfMaterial);
        return batchOfMaterial;
    }

    @Override
    public void update(BatchOfMaterial batchOfMaterial) {
        Long id = batchOfMaterial.getId();
        Optional<BatchOfMaterial> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(batchOfMaterial);
        } else {
            throw new IllegalArgumentException("Cannot find BatchOfMaterial with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<BatchOfMaterial> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }

    @Override
    public <S> List<BatchOfMaterial> findAllForMaterial(S materialId) {
        return em.createNamedQuery("findAllBatchesForMaterial", BatchOfMaterial.class)
                .setParameter("materialId", materialId)
                .getResultList();
    }
}
