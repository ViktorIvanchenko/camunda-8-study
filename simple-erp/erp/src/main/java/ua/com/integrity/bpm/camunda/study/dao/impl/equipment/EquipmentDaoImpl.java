package ua.com.integrity.bpm.camunda.study.dao.impl.equipment;

import ua.com.integrity.bpm.camunda.study.dao.equipment.EquipmentDao;
import ua.com.integrity.bpm.camunda.study.domain.equipment.Equipment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class EquipmentDaoImpl implements EquipmentDao {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<Equipment> find(String id) {
        return Optional.ofNullable(em.find(Equipment.class, id));
    }

    @Override
    public List<Equipment> findAll() {
        return em.createNamedQuery("findAllEquipment", Equipment.class).getResultList();
    }

    @Override
    public Equipment create(Equipment equipment) {
        em.persist(equipment);
        return equipment;
    }

    @Override
    public void update(Equipment equipment) {
        String id = equipment.getSerialNumber();
        Optional<Equipment> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(equipment);
        } else {
            throw new IllegalArgumentException("Cannot find Equipment with s/n " + id);
        }

    }

    @Override
    public void delete(String id) {
        Optional<Equipment> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }

    @Override
    public <S> List<Equipment> findEquipmentByType(S typeId, Boolean withDecommissioned) {
        String queryName = "findActiveEquipmentByType";
        if (withDecommissioned) {
            queryName = "findEquipmentByTypeWithDecommissioned";
        }
        return em.createNamedQuery(queryName, Equipment.class)
                .setParameter("typeId", typeId)
                .getResultList();
    }

    @Override
    public List<Equipment> findAllNonDecommissionedEquipment() {
        return em.createNamedQuery("findAllNonDecommissioned", Equipment.class)
                .getResultList();
    }

    @Override
    public List<String> findEquipmentForDecommissioning() {
        return em.createNamedQuery("findEquipmentForDecommissioning", String.class)
                .getResultList();
    }
}
