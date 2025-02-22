package ua.com.integrity.bpm.camunda.study.dao.impl.material;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.materail.Material;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class MaterialDaoImpl implements GenericDao<Material, Long> {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<Material> find(Long id) {
        return Optional.ofNullable(em.find(Material.class, id));
    }

    @Override
    public List<Material> findAll() {
        return em.createNamedQuery("findAllMaterial", Material.class).getResultList();
    }

    @Override
    public Material create(Material material) {
        em.persist(material);
        return material;
    }

    @Override
    public void update(Material material) {
        Long id = material.getId();
        Optional<Material> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(material);
        } else {
            throw new IllegalArgumentException("Cannot find Material with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Material> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }
}
