package ua.com.integrity.bpm.camunda.study.api.ws;

import ua.com.integrity.bpm.camunda.study.dto.api.EquipmentAssigningRequest;
import ua.com.integrity.bpm.camunda.study.dto.api.EquipmentCommissioningRequest;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentTypeDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.MaintenanceDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.SupplyRateDto;
import ua.com.integrity.bpm.camunda.study.service.EquipmentService;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@WebService(serviceName = "equipments", portName = "equipments")
public class EquipmentWs {

    @Inject
    private EquipmentService equipmentService;

    @WebMethod(operationName = "get-equipment")
    @WebResult(name = "equipment")
    public EquipmentDto getEquipment(@WebParam(name = "equipment-id") String equipmentId) {
        Optional<EquipmentDto> equipment = equipmentService.getEquipment(equipmentId);
        if (equipment.isPresent()) {
            return equipment.get();
        }
        throw new IllegalArgumentException("Equipment not exist");
    }

    @WebMethod(operationName = "get-equipment-list")
    @WebResult(name = "equipment-list")
    public List<EquipmentDto> getEquipmentByType(
            @WebParam(name = "equipment-type-id") Long typeId,
            @WebParam(name = "include-decommissioned-equipment") Boolean withDecommissioned
    ) {
        if (withDecommissioned == null) {
            withDecommissioned = false;
        }
        if (typeId != null) {
            return equipmentService.getEquipmentByType(typeId, withDecommissioned);
        }
        return equipmentService.getAllEquipment(withDecommissioned);
    }

    @WebMethod(operationName = "add-equipment")
    @WebResult(name = "equipment")
    public EquipmentDto newEquipment(@WebParam(name = "equipment-data") EquipmentDto equipmentDto) {
        return equipmentService.newEquipment(equipmentDto);
    }

    @WebMethod(operationName = "commission-equipment")
    @WebResult(name = "equipment")
    public EquipmentDto commissionEquipment(@WebParam(name = "commissioning-data") EquipmentCommissioningRequest request) {
        try {
            LocalDate date = LocalDate.parse(request.getDate());
            return equipmentService.commissionEquipment(request.getSerialNumber(), date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid commissioning date format");
        }
    }

    @WebMethod(operationName = "decommission-equipment")
    @WebResult(name = "equipment")
    public EquipmentDto decommissionEquipment(@WebParam(name = "decommissioning-data") EquipmentCommissioningRequest request) {
        try {
            LocalDate date = LocalDate.parse(request.getDate());
            return equipmentService.decommissionEquipment(request.getSerialNumber(), date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid decommissioning date format");
        }
    }

    @WebMethod(operationName = "assing-equipment")
    @WebResult(name = "equipment")
    public EquipmentDto assignEquipment(@WebParam(name = "assigning-data") EquipmentAssigningRequest request) {
        return equipmentService.assignEquipment(request.getSerialNumber(), request.getEmployeeId());
    }

    @WebMethod(operationName = "retrieve-equipment")
    @WebResult(name = "equipment")
    public EquipmentDto retrieveEquipment(@WebParam(name = "serial-number") String equipmentId) {
        return equipmentService.retrieveEquipment(equipmentId);
    }

    @WebMethod(operationName = "get-equipment-type")
    @WebResult(name = "equipment-type")
    public EquipmentTypeDto getEquipmentType(@WebParam(name = "type-id") Long typeId) {
        Optional<EquipmentTypeDto> equipmentType = equipmentService.getEquipmentType(typeId);
        if (equipmentType.isPresent()) {
            return equipmentType.get();
        }
        throw new IllegalArgumentException("Equipment type not exist");
    }

    @WebMethod(operationName = "get-equipment-types")
    @WebResult(name = "equipment-types")
    public List<EquipmentTypeDto> getAllEquipmentType() {
        return equipmentService.getAllEquipmentType();
    }

    @WebMethod(operationName = "add-equipment-type")
    @WebResult(name = "equipment-type")
    public EquipmentTypeDto newEquipmentType(@WebParam(name = "type-data") EquipmentTypeDto equipmentTypeDto) {
        return equipmentService.newEquipmentType(equipmentTypeDto);
    }

    @WebMethod(operationName = "add-maintenance-to-equipment-type")
    @WebResult(name = "equipment-type")
    public EquipmentTypeDto addMaintenance(@WebParam(name = "type-id") Long typeId, @WebParam(name = "maintenance-id")  Long maintenanceId) {
        return equipmentService.addMaintenance(typeId, maintenanceId);
    }

    @WebMethod(operationName = "remove-maintenance-from-equipment-type")
    @WebResult(name = "equipment-type")
    public EquipmentTypeDto removeMaintenance(@WebParam(name = "type-id") Long typeId, @WebParam(name = "maintenance-id")  Long maintenanceId) {
        return equipmentService.removeMaintenance(typeId,maintenanceId);
    }

    @WebMethod(operationName = "add-consumption-rate-to-equipment-type")
    @WebResult(name = "equipment-type")
    public EquipmentTypeDto addConsumptionRate(@WebParam(name = "type-id") Long typeId, @WebParam(name = "rate-id")  Long rateId) {
        return equipmentService.addConsumptionRate(typeId, rateId);
    }

    @WebMethod(operationName = "remove-consumption-rate-from-equipment-type")
    @WebResult(name = "equipment-type")
    public EquipmentTypeDto removeConsumptionRate(@WebParam(name = "type-id") Long typeId, @WebParam(name = "rate-id")  Long rateId) {
        return equipmentService.removeConsumptionRate(typeId, rateId);
    }

    @WebMethod(operationName = "get-maintenance")
    @WebResult(name = "maintenance")
    public MaintenanceDto getMaintenance(@WebParam(name = "maintenance-id") Long id) {
        Optional<MaintenanceDto> maintenance = equipmentService.getMaintenance(id);
        if (maintenance.isPresent()) {
            return maintenance.get();
        }
        throw new IllegalArgumentException("Maintenance not exist");
    }

    @WebMethod(operationName = "get-maintenance-list")
    @WebResult(name = "maintenance-list")
    public List<MaintenanceDto> getAllMaintenances() {
        return equipmentService.getAllMaintenances();
    }

    @WebMethod(operationName = "add-maintenance")
    @WebResult(name = "maintenance")
    public MaintenanceDto newMaintenance(@WebParam(name = "maintenance-data") MaintenanceDto maintenanceDto) {
        return equipmentService.newMaintenance(maintenanceDto);
    }

    @WebMethod(operationName = "add-consumption-rate-to-maintenance")
    @WebResult(name = "maintenance")
    public MaintenanceDto addConsumptionRateToMaintenance(@WebParam(name = "maintenance-id") Long maintenanceId, @WebParam(name = "rate-id") Long rateId){
        return equipmentService.addConsumptionRateToMaintenance(maintenanceId, rateId);
    }

    @WebMethod(operationName = "remove-consumption-rate-from-maintenance")
    @WebResult(name = "maintenance")
    public MaintenanceDto removeConsumptionRateFromMaintenance(@WebParam(name = "maintenance-id") Long maintenanceId, @WebParam(name = "rate-id") Long rateId){
        return equipmentService.removeConsumptionRateFromMaintenance(maintenanceId, rateId);
    }

    @WebMethod(operationName = "get-supply-rate")
    @WebResult(name = "supply-rate")
    public SupplyRateDto findSupplyRate(@WebParam(name = "rate-id") Long id) {
        Optional<SupplyRateDto> supplyRate = equipmentService.findSupplyRate(id);
        if (supplyRate.isPresent()) {
            return supplyRate.get();
        }
        throw new IllegalArgumentException("Supply rate not exist");
    }

    @WebMethod(operationName = "get-supply-rates")
    @WebResult(name = "supply-rates")
    public List<SupplyRateDto> getAllSupplyRates(@WebParam(name = "equipment-type-id") Long typeId) {
        if (typeId != null) {
            return equipmentService.getAllSupplyRatesForType(typeId);
        }
        return equipmentService.getAllSupplyRates();
    }

    @WebMethod(operationName = "add-supply-rate")
    @WebResult(name = "supply-rate")
    public SupplyRateDto newSupplyRate(@WebParam(name = "supply-rate-data") SupplyRateDto supplyRateDto) {
        return equipmentService.newSupplyRate(supplyRateDto);
    }

    @WebMethod(operationName = "update-supply-rate")
    @WebResult(name = "supply-rate")
    public SupplyRateDto updateSupplyRate(@WebParam(name = "supply-rate-data") SupplyRateDto dto) {
        return equipmentService.updateSupplyRate(dto);
    }

}
