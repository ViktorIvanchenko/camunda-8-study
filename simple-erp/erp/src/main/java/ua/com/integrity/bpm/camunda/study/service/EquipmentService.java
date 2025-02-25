package ua.com.integrity.bpm.camunda.study.service;

import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentTypeDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.MaintenanceDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.SupplyRateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EquipmentService {

    Optional<EquipmentDto> getEquipment(String equipmentId);
    List<EquipmentDto> getEquipmentByType(Long typeId, Boolean withDecommissioned);
    List<EquipmentDto> getAllEquipment(boolean includeDecommissioned);
    EquipmentDto newEquipment(EquipmentDto equipmentDto);
    EquipmentDto commissionEquipment(String equipmentId, LocalDate commissioningDate);
    EquipmentDto decommissionEquipment(String equipmentId, LocalDate decommissioningDate);
    EquipmentDto assignEquipment(String equipmentId, Long employeeId);
    EquipmentDto retrieveEquipment(String equipmentId);

    Optional<EquipmentTypeDto> getEquipmentType(Long typeId);
    List<EquipmentTypeDto> getAllEquipmentType();
    EquipmentTypeDto newEquipmentType(EquipmentTypeDto equipmentTypeDto);
    EquipmentTypeDto addMaintenance(Long typeId, Long maintenanceId);
    EquipmentTypeDto removeMaintenance(Long typeId, Long maintenanceId);
    EquipmentTypeDto addConsumptionRate(Long typeId, Long rateId);
    EquipmentTypeDto removeConsumptionRate(Long typeId, Long rateId);

    Optional<MaintenanceDto> getMaintenance(Long id);
    List<MaintenanceDto> getAllMaintenances();
    MaintenanceDto newMaintenance(MaintenanceDto maintenanceDto);
    MaintenanceDto addConsumptionRateToMaintenance(Long maintenanceId, Long rateId);
    MaintenanceDto removeConsumptionRateFromMaintenance(Long maintenanceId, Long rateId);

        Optional<SupplyRateDto> findSupplyRate(Long id);
    List<SupplyRateDto> getAllSupplyRates();
    List<SupplyRateDto> getAllSupplyRatesForType(Long typeId);
    SupplyRateDto newSupplyRate(SupplyRateDto supplyRateDto);
    SupplyRateDto updateSupplyRate(SupplyRateDto supplyRateDto);

}
