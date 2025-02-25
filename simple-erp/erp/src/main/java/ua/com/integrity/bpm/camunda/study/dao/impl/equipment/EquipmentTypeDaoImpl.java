package ua.com.integrity.bpm.camunda.study.dao.impl.equipment;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.equipment.EquipmentType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class EquipmentTypeDaoImpl implements GenericDao<EquipmentType, Long> {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<EquipmentType> find(Long id) {
        return Optional.ofNullable(em.find(EquipmentType.class, id));
    }

    @Override
    public List<EquipmentType> findAll() {
        return em.createNamedQuery("findAllEquipmentType", EquipmentType.class).getResultList();
    }

    @Override
    public EquipmentType create(EquipmentType equipmentType) {
        em.persist(equipmentType);
        return equipmentType;
    }

    @Override
    public void update(EquipmentType equipmentType) {
        Long id = equipmentType.getId();
        Optional<EquipmentType> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(equipmentType);
        } else {
            throw new IllegalArgumentException("Cannot find equipmentType with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<EquipmentType> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }
}
