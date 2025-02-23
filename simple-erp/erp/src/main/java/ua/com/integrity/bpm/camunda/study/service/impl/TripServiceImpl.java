package ua.com.integrity.bpm.camunda.study.service.impl;

import lombok.Setter;
import ua.com.integrity.bpm.camunda.study.dao.EmployeeDao;
import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.Employee;
import ua.com.integrity.bpm.camunda.study.domain.trip.Trip;
import ua.com.integrity.bpm.camunda.study.dto.trip.TripDto;
import ua.com.integrity.bpm.camunda.study.mapper.ErpMapper;
import ua.com.integrity.bpm.camunda.study.service.TripService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Setter
@Transactional
public class TripServiceImpl implements TripService {

    @Inject
    private GenericDao<Trip, Long> tripDao;
    @Inject
    private EmployeeDao employeeDao;
    @Inject
    private ErpMapper mapper;

    @Override
    public List<TripDto> getAllTrips() {
        return mapper.map(tripDao.findAll());
    }

    @Override
    public TripDto newTrip(TripDto tripDto) {
        Long employeeId = tripDto.getEmployee();
        Optional<Employee> employee = employeeDao.find(employeeId);
        if (employee.isPresent()) {
            tripDto.setId(null);
            Trip createdTrip = tripDao.create(mapper.map(tripDto));
            return mapper.map(createdTrip);
        } else {
            throw new IllegalArgumentException("Cannot create trip for not existing employee");
        }
    }
}
