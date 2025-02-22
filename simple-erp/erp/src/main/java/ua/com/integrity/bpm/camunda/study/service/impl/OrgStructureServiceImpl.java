package ua.com.integrity.bpm.camunda.study.service.impl;

import lombok.Setter;
import ua.com.integrity.bpm.camunda.study.dao.EmployeeDao;
import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.Employee;
import ua.com.integrity.bpm.camunda.study.domain.OrgUnit;
import ua.com.integrity.bpm.camunda.study.domain.Position;
import ua.com.integrity.bpm.camunda.study.domain.equipment.SupplyRate;
import ua.com.integrity.bpm.camunda.study.domain.vacation.Vacation;
import ua.com.integrity.bpm.camunda.study.dto.EmployeeDto;
import ua.com.integrity.bpm.camunda.study.dto.OrgUnitDto;
import ua.com.integrity.bpm.camunda.study.dto.PositionDto;
import ua.com.integrity.bpm.camunda.study.dto.vacation.VacationDto;
import ua.com.integrity.bpm.camunda.study.mapper.ErpMapper;
import ua.com.integrity.bpm.camunda.study.service.OrgStructureService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Setter
@Transactional
public class OrgStructureServiceImpl implements OrgStructureService {

    @Inject
    private GenericDao<OrgUnit, Long> orgUnitDao;
    @Inject
    private GenericDao<Position, Long> positionDao;
    @Inject
    private EmployeeDao employeeDao;
    @Inject
    private GenericDao<Vacation, Long> vacationDao;
    @Inject
    private GenericDao<SupplyRate, Long> supplyRateDao;
    @Inject
    private ErpMapper mapper;

    @Override
    public Optional<OrgUnitDto> getUnit(Long id) {
        Optional<OrgUnit> orgUnit = orgUnitDao.find(id);
        if (orgUnit.isPresent()) {
            return Optional.of(mapper.orgUnitToDto(orgUnit.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<OrgUnitDto> getUnits() {
        List<OrgUnit> orgUnits = orgUnitDao.findAll();
        return mapper.orgUnitsToDto(orgUnits);
    }

    @Override
    public OrgUnitDto createUnit(OrgUnitDto orgUnitDto) {
        OrgUnit orgUnit = mapper.orgUnitFromDto(orgUnitDto);
        OrgUnit orgUnitCreated = orgUnitDao.create(orgUnit);
        return mapper.orgUnitToDto(orgUnitCreated);
    }

    @Override
    public Optional<PositionDto> getPosition(Long id) {
        Optional<Position> position = positionDao.find(id);
        if (position.isPresent()) {
            return Optional.of(mapper.positionToDto(position.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<PositionDto> getPositions() {
        List<Position> positions = positionDao.findAll();
        return mapper.positionsToDto(positions);
    }

    @Override
    public PositionDto createPosition(PositionDto positionDto) {
        Position position = mapper.positionFromDto(positionDto);
        Position positionCreated = positionDao.create(position);
        return mapper.positionToDto(positionCreated);
    }

    @Override
    public PositionDto addSupplyRate(Long positionId, Long supplyRateId) {
        Optional<Position> positionOpt = positionDao.find(positionId);
        if (positionOpt.isPresent()) {
            Position position = positionOpt.get();
            boolean supplyRateLinked = position.getSupplyRates().stream()
                    .anyMatch(rate -> rate.getId().equals(supplyRateId));
            if (!supplyRateLinked) {
                Optional<SupplyRate> supplyRate = supplyRateDao.find(supplyRateId);
                if (supplyRate.isPresent()) {
                    position.getSupplyRates().add(supplyRate.get());
                    positionDao.update(position);
                    return mapper.positionToDto(position);
                } else {
                    throw new IllegalArgumentException("No such supply rate");
                }
            } else {
                throw new IllegalArgumentException("Supply rate already linked to position.");
            }
        } else {
            throw new IllegalArgumentException("No such position");
        }
    }

    @Override
    public PositionDto removeSupplyRate(Long positionId, Long supplyRateId) {
        Optional<Position> positionOpt = positionDao.find(positionId);
        if (positionOpt.isPresent()) {
            Position position = positionOpt.get();
            Optional<SupplyRate> supplyRate = position.getSupplyRates().stream()
                    .filter(rate -> rate.getId().equals(supplyRateId))
                    .findAny();
            if (supplyRate.isPresent()) {
                position.getSupplyRates().remove(supplyRate.get());
            }
            return mapper.positionToDto(position);
        } else {
            throw new IllegalArgumentException("No such position");
        }
    }

    @Override
    public Optional<EmployeeDto> getEmployee(Long id) {
        Optional<Employee> employee = employeeDao.find(id);
        if (employee.isPresent()) {
            return Optional.of(mapper.employeeToDto(employee.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeDto> getEmployee(String login) {
        Optional<Employee> employee = employeeDao.findByLogin(login);
        if (employee.isPresent()) {
            return Optional.of(mapper.employeeToDto(employee.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<EmployeeDto> getEmployees() {
        List<Employee> employees = employeeDao.findAll();
        return mapper.employeesToDto(employees);
    }

    @Override
    public EmployeeDto newEmployee(EmployeeDto employeeDto) {
        Long positionId = employeeDto.getPosition();
        Optional<Position> positionOpt = positionDao.find(positionId);
        if (positionOpt.isPresent()) {
            Position position = positionOpt.get();
            if (position.getEmployee() == null) {
                Optional<Employee> persistedEmloyee = employeeDao.findByLogin(employeeDto.getLogin());
                if (persistedEmloyee.isPresent()) {
                    throw new IllegalArgumentException("Employee with same login exists");
                }
                Employee employee = mapper.employeeFromDto(employeeDto);
                employee.setPosition(position);
                Employee employeeCreated = employeeDao.create(employee);
                return mapper.employeeToDto(employeeCreated);
            } else {
                throw new IllegalArgumentException("Position is already linked to employee.");
            }
        } else {
            throw new IllegalArgumentException("Cannot create new employee with unknown position");
        }
    }

    @Override
    public void updateEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employee = employeeDao.find(employeeDto.getId());
        if (employee.isPresent()) {
            Optional<Employee> byLogin = employeeDao.findByLogin(employeeDto.getLogin());
            if (byLogin.isPresent() && !byLogin.get().getId().equals(employee.get().getId())) {
                throw new IllegalArgumentException("New login for employee already used by another employee");
            }
            employeeDao.update(mapper.employeeFromDto(employeeDto));
        } else {
            throw new IllegalArgumentException("Employee not exist.");
        }
    }

    @Override
    public List<VacationDto> getVacations() {
        List<Vacation> vacations = vacationDao.findAll();
        return mapper.vacationsToDto(vacations);
    }

    @Override
    public VacationDto newVacation(VacationDto vacationDto) {
        Long employeeId = vacationDto.getEmployee();
        Optional<Employee> employee = employeeDao.find(employeeId);
        if (employee.isPresent()) {
            Vacation vacationCreated = vacationDao.create(mapper.vacationFromDto(vacationDto));
            return mapper.vacationToDto(vacationCreated);
        } else {
            throw new IllegalArgumentException("Cannot create vacation for not existing employee");
        }
    }

    @Override
    public void cancelVacation(Long vacationId) {
        Optional<Vacation> vacationOpt = vacationDao.find(vacationId);
        if (vacationOpt.isPresent()) {
            vacationDao.delete(vacationId);
        }
    }
}
