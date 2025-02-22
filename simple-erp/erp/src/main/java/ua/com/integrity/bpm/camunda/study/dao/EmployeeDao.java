package ua.com.integrity.bpm.camunda.study.dao;

import ua.com.integrity.bpm.camunda.study.domain.Employee;

import java.util.Optional;

public interface EmployeeDao extends GenericDao<Employee, Long> {
    Optional<Employee> findByLogin(String login);
}
