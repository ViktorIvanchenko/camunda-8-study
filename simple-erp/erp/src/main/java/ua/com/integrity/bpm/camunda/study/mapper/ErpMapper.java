package ua.com.integrity.bpm.camunda.study.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.integrity.bpm.camunda.study.domain.Employee;
import ua.com.integrity.bpm.camunda.study.domain.OrgUnit;
import ua.com.integrity.bpm.camunda.study.domain.Position;
import ua.com.integrity.bpm.camunda.study.domain.equipment.Equipment;
import ua.com.integrity.bpm.camunda.study.domain.equipment.EquipmentType;
import ua.com.integrity.bpm.camunda.study.domain.equipment.Maintenance;
import ua.com.integrity.bpm.camunda.study.domain.equipment.SupplyRate;
import ua.com.integrity.bpm.camunda.study.domain.materail.BatchOfMaterial;
import ua.com.integrity.bpm.camunda.study.domain.materail.ConsumptionRate;
import ua.com.integrity.bpm.camunda.study.domain.materail.Material;
import ua.com.integrity.bpm.camunda.study.domain.trip.Trip;
import ua.com.integrity.bpm.camunda.study.domain.vacation.Vacation;
import ua.com.integrity.bpm.camunda.study.dto.EmployeeDto;
import ua.com.integrity.bpm.camunda.study.dto.OrgUnitDto;
import ua.com.integrity.bpm.camunda.study.dto.PositionDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentTypeDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.MaintenanceDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.SupplyRateDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.BatchOfMaterialDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.ConsumptionRateDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.MaterialDto;
import ua.com.integrity.bpm.camunda.study.dto.trip.TripDto;
import ua.com.integrity.bpm.camunda.study.dto.vacation.VacationDto;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "cdi")
public interface ErpMapper {

    OrgUnitDto orgUnitToDto(OrgUnit orgUnit);
    OrgUnit orgUnitFromDto(OrgUnitDto orgUnitDto);
    List<OrgUnitDto> orgUnitsToDto(List<OrgUnit> orgUnits);
    PositionDto positionToDto(Position position);
    Position positionFromDto(PositionDto positionDto);
    List<PositionDto> positionsToDto(List<Position> positions);
    EmployeeDto employeeToDto(Employee employee);
    Employee employeeFromDto(EmployeeDto employeeDto);
    List<EmployeeDto> employeesToDto(List<Employee> employees);

    @Mapping(target = "id")
    OrgUnit orgUnitById(Long id);

    @Mapping(target = "id")
    Position positionById(Long id);
    @Mapping(target = "id")
    Employee employeeById(Long id);

    default Long map(OrgUnit orgUnit) {
        if (orgUnit != null) {
            return orgUnit.getId();
        }
        return null;
    }
    default Long map(Position position) {
        if (position != null) {
            return position.getId();
        }
        return null;
    }

    default Long map(Employee employee) {
        if (employee != null) {
            return employee.getId();
        }
        return null;
    }


    SupplyRateDto supplyRateToDto(SupplyRate supplyRate);
    SupplyRate supplyRateFromDto(SupplyRateDto supplyRateDto);
    List<SupplyRateDto> supplyRatesToDto(List<SupplyRate> supplyRates);
    EquipmentDto equipmentsToDto(Equipment equipment);
    Equipment equipmentFromDto(EquipmentDto equipmentDto);
    List<EquipmentDto> equipmentsToDto(List<Equipment> equipments);
    EquipmentTypeDto equipmentTypeToDto(EquipmentType equipmentType);
    EquipmentType equipmentTypeFromDto(EquipmentTypeDto equipmentTypeDto);
    List<EquipmentTypeDto> equipmentTypesToDto(List<EquipmentType> equipmentTypes);
    MaintenanceDto maintenanceToDto(Maintenance maintenance);
    Maintenance maintenanceFromDto(MaintenanceDto maintenanceDto);
    List<MaintenanceDto> maintenancesToDto(List<Maintenance> maintenances);

    Set<Long> mapMaintenanceSet(Set<Maintenance> maintenances);

    @Mapping(target = "id")
    SupplyRate supplyRateById(Long id);
    @Mapping(target = "id")
    EquipmentType equipmentTypeById(Long id);
    @Mapping(target = "id")
    Maintenance maintenanceById(Long id);

    default Long map(SupplyRate supplyRate) {
        if (supplyRate != null) {
            return supplyRate.getId();
        }
        return null;
    }
    default Long map(EquipmentType equipmentType) {
        if (equipmentType != null) {
            return equipmentType.getId();
        }
        return null;
    }
    default Long map(Maintenance maintenance) {
        if (maintenance != null) {
            return maintenance.getId();
        }
        return null;
    }

    BatchOfMaterialDto batchOfMaterialToDto(BatchOfMaterial batchOfMaterial);
    BatchOfMaterial batchOfMaterialFromDto(BatchOfMaterialDto batchOfMaterialDto);
    List<BatchOfMaterialDto> batchOfMaterialsToDto(List<BatchOfMaterial> batchOfMaterials);
    ConsumptionRate consumptionRateFromDto(ConsumptionRateDto consumptionRateDto);
    ConsumptionRateDto consumptionRateToDto(ConsumptionRate consumptionRate);
    List<ConsumptionRateDto> consumptionRatesToDto(List<ConsumptionRate> consumptionRates);
    MaterialDto materialToDto(Material material);
    Material materialFromDto(MaterialDto material);
    List<MaterialDto> materialsToDto(List<Material> materials);

    @Mapping(target = "id")
    ConsumptionRate consumptionRateById(Long id);
    @Mapping(target = "id")
    BatchOfMaterial batchOfMaterialById(Long id);

    default Long map(BatchOfMaterial batchOfMaterial){
        if (batchOfMaterial != null) {
            return batchOfMaterial.getId();
        }
        return null;
    }
    default Long map(ConsumptionRate consumptionRate) {
        if (consumptionRate != null) {
            return consumptionRate.getId();
        }
        return null;
    }

    VacationDto vacationToDto(Vacation vacation);
    Vacation vacationFromDto(VacationDto vacationDto);
    List<VacationDto> vacationsToDto(List<Vacation> vacations);

    TripDto map(Trip trip);
    List<TripDto> map(List<Trip> trips);
    Trip map(TripDto tripDto);
}
