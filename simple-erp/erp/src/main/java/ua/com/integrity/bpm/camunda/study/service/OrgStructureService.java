package ua.com.integrity.bpm.camunda.study.service;

import ua.com.integrity.bpm.camunda.study.dto.EmployeeDto;
import ua.com.integrity.bpm.camunda.study.dto.OrgUnitDto;
import ua.com.integrity.bpm.camunda.study.dto.PositionDto;
import ua.com.integrity.bpm.camunda.study.dto.vacation.VacationDto;

import java.util.List;
import java.util.Optional;

public interface OrgStructureService {
    Optional<OrgUnitDto> getUnit(Long id);
    List<OrgUnitDto> getUnits();
    OrgUnitDto createUnit(OrgUnitDto orgUnitDto);

    Optional<PositionDto> getPosition(Long id);
    List<PositionDto> getPositions();
    PositionDto createPosition(PositionDto positionDto);
    PositionDto addSupplyRate(Long positionId, Long supplyRateId);
    PositionDto removeSupplyRate(Long positionId, Long supplyRateId);

    Optional<EmployeeDto> getEmployee(Long id);
    Optional<EmployeeDto> getEmployee(String login);
    List<EmployeeDto> getEmployees();
    EmployeeDto newEmployee(EmployeeDto employeeDto);
    void updateEmployee(EmployeeDto employeeDto);

    List<VacationDto> getVacations();
    VacationDto newVacation(VacationDto vacationDto);
    void cancelVacation(Long vacationId);
}
