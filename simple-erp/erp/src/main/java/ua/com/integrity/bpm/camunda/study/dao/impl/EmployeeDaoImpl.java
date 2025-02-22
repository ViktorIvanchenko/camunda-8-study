package ua.com.integrity.bpm.camunda.study.dao.impl;

import ua.com.integrity.bpm.camunda.study.dao.EmployeeDao;
import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.Employee;
import ua.com.integrity.bpm.camunda.study.dto.EmployeeDto;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    @PersistenceContext(unitName = "erpPersistenceUnit")
    EntityManager em;

    @Override
    public Optional<Employee> find(Long id) {
        return Optional.ofNullable(em.find(Employee.class, id));
    }

    @Override
    public List<Employee> findAll() {
        return em.createNamedQuery("findAllEmployee", Employee.class).getResultList();
    }

    @Override
    public Employee create(Employee employee) {
        em.persist(employee);
        return employee;
    }

    @Override
    public void update(Employee employee) {
        Long id = employee.getId();
        Optional<Employee> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.merge(employee);
        } else {
            throw new IllegalArgumentException("Cannot find employee with id " + id);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Employee> fromDb = this.find(id);
        if (fromDb.isPresent()) {
            em.remove(fromDb.get());
        }
    }

    @Override
    public Optional<Employee> findByLogin(String login) {
        try {
            Employee employee = em.createNamedQuery("findEmployeeByLogin", Employee.class)
                    .setParameter("login", login)
                    .getSingleResult();
            return Optional.ofNullable(employee);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
