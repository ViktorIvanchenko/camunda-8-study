package ua.com.integrity.bpm.camunda.study.api.ws;

import ua.com.integrity.bpm.camunda.study.dto.EmployeeDto;
import ua.com.integrity.bpm.camunda.study.dto.OrgUnitDto;
import ua.com.integrity.bpm.camunda.study.dto.PositionDto;
import ua.com.integrity.bpm.camunda.study.dto.vacation.VacationDto;
import ua.com.integrity.bpm.camunda.study.service.OrgStructureService;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import java.util.List;
import java.util.Optional;

@WebService(serviceName = "org-structure", portName = "org-structure")
public class OrgStructureWs {

    @Inject
    private OrgStructureService orgStructureService;

    @WebMethod(operationName = "get-units")
    @WebResult(name = "units")
    public List<OrgUnitDto> getUnits(){
        return orgStructureService.getUnits();
    }

    @WebMethod(operationName = "get-unit")
    @WebResult(name = "unit")
    public OrgUnitDto getUnits(@WebParam(name = "unit-id") Long unitId){
        Optional<OrgUnitDto> unit = orgStructureService.getUnit(unitId);
        if (unit.isPresent()) {
            return unit.get();
        } else {
            throw new IllegalArgumentException("Unit not exist");
        }
    }

    @WebMethod(operationName = "create-unit")
    @WebResult(name = "unit")
    public OrgUnitDto createUnit(@WebParam(name = "unit-data") OrgUnitDto orgUnitDto) {
        return orgStructureService.createUnit(orgUnitDto);
    }

    @WebMethod(operationName = "get-position")
    @WebResult(name = "position")
    public PositionDto getPosition(@WebParam(name = "position-id") Long positionId) {
        Optional<PositionDto> position = orgStructureService.getPosition(positionId);
        if (position.isPresent()) {
            return position.get();
        }
        throw new IllegalArgumentException("Position not exist");
    }

    @WebMethod(operationName = "all-positions")
    @WebResult(name = "positions")
    public List<PositionDto> getPositions(){
        return orgStructureService.getPositions();
    }

    @WebMethod(operationName = "create-position")
    @WebResult(name = "position")
    public PositionDto createPosition(@WebParam(name = "position-data") PositionDto positionDto){
        return orgStructureService.createPosition(positionDto);
    }


    @WebMethod(operationName = "add-supply-rate-to-position")
    @WebResult(name = "position")
    public PositionDto addSupplyRate(@WebParam(name = "position-id") Long positionId, @WebParam(name = "supply-rate-id") Long rateId) {
        return orgStructureService.addSupplyRate(positionId, rateId);
    }

    @WebMethod(operationName = "remove-supply-rate-from-position")
    @WebResult(name = "position")
    public PositionDto removeSupplyRate(@WebParam(name = "position-id") Long positionId, @WebParam(name = "supply-rate-id") Long rateId) {
        return orgStructureService.removeSupplyRate(positionId, rateId);
    }

    @WebMethod(operationName = "get-employee")
    @WebResult(name = "employee")
    public EmployeeDto getEmployee(@WebParam(name = "employee-id") Long id) {
        Optional<EmployeeDto> employee = orgStructureService.getEmployee(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new IllegalArgumentException("Employee not exist");
    }

    @WebMethod(operationName = "get-employee-by-login")
    @WebResult(name = "employee")
    public EmployeeDto getEmployee(@WebParam(name = "employee-login") String login) {
        Optional<EmployeeDto> employee = orgStructureService.getEmployee(login);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new IllegalArgumentException("Employee not exist");
    }

    @WebMethod(operationName = "get-all-employees")
    @WebResult(name = "employees")
    public List<EmployeeDto> getEmployees() {
        return orgStructureService.getEmployees();
    }

    @WebMethod(operationName = "new-employee")
    @WebResult(name = "employee")
    public EmployeeDto newEmployee(@WebParam(name = "employee-data") EmployeeDto employeeDto) {
        return orgStructureService.newEmployee(employeeDto);
    }

    @WebMethod(operationName = "update-employee")
    public void updateEmployee(@WebParam(name = "employee-data") EmployeeDto employeeDto) {
            orgStructureService.updateEmployee(employeeDto);
    }

    @WebMethod(operationName = "get-all-vacations")
    @WebResult(name = "vacations")
    public List<VacationDto> getVacations() {
        return orgStructureService.getVacations();
    }

    @WebMethod(operationName = "new-vacation")
    @WebResult(name = "vacation")
    public VacationDto newVacation(@WebParam(name = "vacation-data") VacationDto vacationDto) {
        return orgStructureService.newVacation(vacationDto);
    }

    @WebMethod(operationName = "cancel-vacation")
    public void cancelVacation(@WebParam(name = "vacation-id") Long id) {
        orgStructureService.cancelVacation(id);
    }

}
