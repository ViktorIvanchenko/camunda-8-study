package ua.com.integrity.bpm.camunda.study.dao.impl;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.OrgUnit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class OrgUnitDaoImpl implements GenericDao<OrgUnit, Long> {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<OrgUnit> find(Long id) {
        return Optional.ofNullable(em.find(OrgUnit.class, id));
    }

    @Override
    public List<OrgUnit> findAll() {
        return em.createNamedQuery("findAllOrgUnit", OrgUnit.class).getResultList();
    }

    @Override
    public OrgUnit create(OrgUnit orgUnit) {
        em.persist(orgUnit);
        return orgUnit;
    }

    @Override
    public void update(OrgUnit orgUnit) {
        Long id = orgUnit.getId();
        Optional<OrgUnit> unit = this.find(id);
        if (unit.isPresent()) {
            em.merge(orgUnit);
        } else {
            throw new IllegalArgumentException("Cannot find unit with id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<OrgUnit> orgUnit = this.find(id);
        if (orgUnit.isPresent()){
            em.remove(orgUnit.get());
        }
    }
}
