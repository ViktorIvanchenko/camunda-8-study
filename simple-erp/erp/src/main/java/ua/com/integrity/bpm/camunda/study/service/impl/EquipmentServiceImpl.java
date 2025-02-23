package ua.com.integrity.bpm.camunda.study.service.impl;

import lombok.Setter;
import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.dao.equipment.EquipmentDao;
import ua.com.integrity.bpm.camunda.study.dao.equipment.SupplyRateDao;
import ua.com.integrity.bpm.camunda.study.domain.Employee;
import ua.com.integrity.bpm.camunda.study.domain.equipment.Equipment;
import ua.com.integrity.bpm.camunda.study.domain.equipment.EquipmentType;
import ua.com.integrity.bpm.camunda.study.domain.equipment.Maintenance;
import ua.com.integrity.bpm.camunda.study.domain.equipment.SupplyRate;
import ua.com.integrity.bpm.camunda.study.domain.materail.ConsumptionRate;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentTypeDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.MaintenanceDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.SupplyRateDto;
import ua.com.integrity.bpm.camunda.study.mapper.ErpMapper;
import ua.com.integrity.bpm.camunda.study.service.EquipmentService;

import jakarta.inject.Inject;
import jakarta.interceptor.InterceptorBinding;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Setter
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Inject
    private EquipmentDao equipmentDao;
    @Inject
    private GenericDao<EquipmentType, Long> equipmentTypeDao;
    @Inject
    private SupplyRateDao supplyRateDao;
    @Inject
    private GenericDao<Maintenance, Long> maintenanceDao;
    @Inject
    private GenericDao<Employee, Long> employeeDao;
    @Inject
    private GenericDao<ConsumptionRate, Long> consumptionRateDao;
    @Inject
    private ErpMapper mapper;

    @Override
    public Optional<EquipmentDto> getEquipment(String equipmentId) {
        Optional<Equipment> optional = equipmentDao.find(equipmentId);
        if (optional.isPresent()) {
            return Optional.of(mapper.equipmentsToDto(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<EquipmentDto> getEquipmentByType(Long typeId, Boolean withDecommissioned) {
        List<Equipment> equipment = equipmentDao.findEquipmentByType(typeId, withDecommissioned);
        return mapper.equipmentsToDto(equipment);
    }

    @Override
    public List<EquipmentDto> getAllEquipment(boolean includeDecommissioned) {
        List<Equipment> equipment;
        if (includeDecommissioned) {
            equipment = equipmentDao.findAll();
        } else {
            equipment = equipmentDao.findAllNonDecommissionedEquipment();
        }
        return mapper.equipmentsToDto(equipment);
    }

    @Override
    public EquipmentDto newEquipment(EquipmentDto equipmentDto) {
        Long type = equipmentDto.getType();
        Optional<EquipmentType> equipmentTypeOpt = equipmentTypeDao.find(type);
        if (equipmentTypeOpt.isPresent()) {
            EquipmentType equipmentType = equipmentTypeOpt.get();
            Equipment equipment = mapper.equipmentFromDto(equipmentDto);
            equipment.setType(equipmentType);
            Equipment equipmentNew = equipmentDao.create(equipment);
            return mapper.equipmentsToDto(equipmentNew);
        } else {
            throw new IllegalArgumentException("Cannot create new equipment of unknown type");
        }
    }

    @Override
    public EquipmentDto commissionEquipment(String equipmentId, LocalDate commissioningDate) {
        Optional<Equipment> optional = equipmentDao.find(equipmentId);
        if (optional.isPresent()) {
            Equipment equipment = optional.get();
            equipment.setCommissioningDate(commissioningDate);
            equipmentDao.update(equipment);
            return mapper.equipmentsToDto(equipment);
        } else {
            throw new IllegalArgumentException("Equipment with such s/n not exist");
        }
    }

    @Override
    public EquipmentDto decommissionEquipment(String equipmentId, LocalDate decommissioningDate) {
        Optional<Equipment> optional = equipmentDao.find(equipmentId);
        if (optional.isPresent()) {
            Equipment equipment = optional.get();
            equipment.setDecommissioningDate(decommissioningDate);
            equipmentDao.update(equipment);
            return mapper.equipmentsToDto(equipment);
        } else {
            throw new IllegalArgumentException("Equipment with such s/n not exist");
        }
    }

    @Override
    public EquipmentDto assignEquipment(String equipmentId, Long employeeId) {
        Optional<Equipment> optional = equipmentDao.find(equipmentId);
        if (optional.isPresent()) {
            Optional<Employee> employeeOptional = employeeDao.find(employeeId);
            if (employeeOptional.isPresent()) {
                Equipment equipment = optional.get();
                Employee employee = employeeOptional.get();
                equipment.setUser(employee);
                equipmentDao.update(equipment);
                return mapper.equipmentsToDto(equipment);
            } else {
                throw new IllegalArgumentException("Employee not exist");
            }
        } else {
            throw new IllegalArgumentException("Equipment with such s/n not exist");
        }
    }

    @Override
    public EquipmentDto retrieveEquipment(String equipmentId) {
        Optional<Equipment> optional = equipmentDao.find(equipmentId);
        if (optional.isPresent()) {
            Equipment equipment = optional.get();
            equipment.setUser(null);
            equipmentDao.update(equipment);
            return mapper.equipmentsToDto(equipment);
        } else {
            throw new IllegalArgumentException("Equipment with such s/n not exist");
        }
    }

    @Override
    public Optional<EquipmentTypeDto> getEquipmentType(Long typeId) {
        Optional<EquipmentType> optional = equipmentTypeDao.find(typeId);
        if (optional.isPresent()) {
            return Optional.of(mapper.equipmentTypeToDto(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<EquipmentTypeDto> getAllEquipmentType() {
        List<EquipmentType> allTypes = equipmentTypeDao.findAll();
        return mapper.equipmentTypesToDto(allTypes);
    }

    @Override
    public EquipmentTypeDto newEquipmentType(EquipmentTypeDto equipmentTypeDto) {
        EquipmentType equipmentType = mapper.equipmentTypeFromDto(equipmentTypeDto);
        EquipmentType equipmentTypeNew = equipmentTypeDao.create(equipmentType);
        return mapper.equipmentTypeToDto(equipmentTypeNew);
    }

    @Override
    public EquipmentTypeDto addMaintenance(Long typeId, Long maintenanceId) {
        Optional<EquipmentType> optional = equipmentTypeDao.find(typeId);
        if (optional.isPresent()) {
            EquipmentType equipmentType = optional.get();
            Optional<Maintenance> maintenanceOpt = maintenanceDao.find(maintenanceId);
            if (maintenanceOpt.isPresent()) {
                Maintenance maintenance = maintenanceOpt.get();
                if (equipmentType.getMaintenanceSet() == null) {
                    equipmentType.setMaintenanceSet(new HashSet<>());
                }
                if (!equipmentType.getMaintenanceSet().contains(maintenance)) {
                    equipmentType.getMaintenanceSet().add(maintenance);
                    equipmentTypeDao.update(equipmentType);
                }
            } else {
                throw new IllegalArgumentException("Maintenance not exist");
            }
            return mapper.equipmentTypeToDto(equipmentType);
        } else {
            throw new IllegalArgumentException("EquipmentType not exist");
        }
    }

    @Override
    public EquipmentTypeDto removeMaintenance(Long typeId, Long maintenanceId) {
        Optional<EquipmentType> optional = equipmentTypeDao.find(typeId);
        if (optional.isPresent()) {
            EquipmentType equipmentType = optional.get();
            Optional<Maintenance> maintenanceOpt = maintenanceDao.find(maintenanceId);
            if (maintenanceOpt.isPresent()) {
                Maintenance maintenance = maintenanceOpt.get();
                if (equipmentType.getMaintenanceSet() != null && equipmentType.getMaintenanceSet().contains(maintenance)) {
                    equipmentType.getMaintenanceSet().remove(maintenance);
                    equipmentTypeDao.update(equipmentType);
                }
            } else {
                throw new IllegalArgumentException("Maintenance not exist");
            }
            return mapper.equipmentTypeToDto(equipmentType);
        } else {
            throw new IllegalArgumentException("EquipmentType not exist");
        }
    }

    @Override
    public EquipmentTypeDto addConsumptionRate(Long typeId, Long rateId) {
        Optional<EquipmentType> optional = equipmentTypeDao.find(typeId);
        if (optional.isPresent()) {
            EquipmentType equipmentType = optional.get();
            Optional<ConsumptionRate> rateOptional = consumptionRateDao.find(rateId);
            if (rateOptional.isPresent()) {
                ConsumptionRate consumptionRate = rateOptional.get();
                if (equipmentType.getConsumptionRates() == null) {
                    equipmentType.setConsumptionRates(new HashSet<>());
                }
                if (!equipmentType.getConsumptionRates().contains(consumptionRate)) {
                    equipmentType.getConsumptionRates().add(consumptionRate);
                    equipmentTypeDao.update(equipmentType);
                }
            } else {
                throw new IllegalArgumentException("ConsumptionRate not exist");
            }
            return mapper.equipmentTypeToDto(equipmentType);
        } else {
            throw new IllegalArgumentException("EquipmentType not exist");
        }
    }

    @Override
    public EquipmentTypeDto removeConsumptionRate(Long typeId, Long rateId) {
        Optional<EquipmentType> optional = equipmentTypeDao.find(typeId);
        if (optional.isPresent()) {
            EquipmentType equipmentType = optional.get();
            Optional<ConsumptionRate> rateOptional = consumptionRateDao.find(rateId);
            if (rateOptional.isPresent()) {
                ConsumptionRate consumptionRate = rateOptional.get();
                if (equipmentType.getConsumptionRates()!= null && equipmentType.getConsumptionRates().contains(consumptionRate)) {
                    equipmentType.getConsumptionRates().remove(consumptionRate);
                    equipmentTypeDao.update(equipmentType);
                }
            } else {
                throw new IllegalArgumentException("ConsumptionRate not exist");
            }
            return mapper.equipmentTypeToDto(equipmentType);
        } else {
            throw new IllegalArgumentException("EquipmentType not exist");
        }
    }

    @Override
    public Optional<MaintenanceDto> getMaintenance(Long id) {
        Optional<Maintenance> maintenance = maintenanceDao.find(id);
        if (maintenance.isPresent()) {
            return Optional.of(mapper.maintenanceToDto(maintenance.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<MaintenanceDto> getAllMaintenances() {
        List<Maintenance> maintenanceList = maintenanceDao.findAll();
        return mapper.maintenancesToDto(maintenanceList);
    }

    @Override
    public MaintenanceDto newMaintenance(MaintenanceDto maintenanceDto) {
        Maintenance maintenance = mapper.maintenanceFromDto(maintenanceDto);
        Maintenance maintenanceNew = maintenanceDao.create(maintenance);
        return mapper.maintenanceToDto(maintenanceNew);
    }

    @Override
    public MaintenanceDto addConsumptionRateToMaintenance(Long maintenanceId, Long rateId) {
        Optional<Maintenance> optional = maintenanceDao.find(maintenanceId);
        if (optional.isPresent()) {
            Maintenance maintenance = optional.get();
            Optional<ConsumptionRate> rateOptional = consumptionRateDao.find(rateId);
            if (rateOptional.isPresent()) {
                ConsumptionRate consumptionRate = rateOptional.get();
                if (maintenance.getConsumptionRates() == null) {
                    maintenance.setConsumptionRates(new HashSet<>());
                }
                if (!maintenance.getConsumptionRates().contains(consumptionRate)) {
                    maintenance.getConsumptionRates().add(consumptionRate);
                    maintenanceDao.update(maintenance);
                }
            } else {
                throw new IllegalArgumentException("ConsumptionRate not exist");
            }
            return mapper.maintenanceToDto(maintenance);
        } else {
            throw new IllegalArgumentException("Maintenance not exist");
        }
    }

    @Override
    public MaintenanceDto removeConsumptionRateFromMaintenance(Long maintenanceId, Long rateId) {
        Optional<Maintenance> optional = maintenanceDao.find(maintenanceId);
        if (optional.isPresent()) {
            Maintenance maintenance = optional.get();
            Optional<ConsumptionRate> rateOptional = consumptionRateDao.find(rateId);
            if (rateOptional.isPresent()) {
                ConsumptionRate consumptionRate = rateOptional.get();
                if (maintenance.getConsumptionRates() != null && maintenance.getConsumptionRates().contains(consumptionRate)) {
                    maintenance.getConsumptionRates().remove(consumptionRate);
                    maintenanceDao.update(maintenance);
                }
            } else {
                throw new IllegalArgumentException("ConsumptionRate not exist");
            }
            return mapper.maintenanceToDto(maintenance);
        } else {
            throw new IllegalArgumentException("Maintenance not exist");
        }
    }

    @Override
    public Optional<SupplyRateDto> findSupplyRate(Long id) {
        Optional<SupplyRate> optional = supplyRateDao.find(id);
        if (optional.isPresent()) {
            return Optional.of(mapper.supplyRateToDto(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<SupplyRateDto> getAllSupplyRates() {
        List<SupplyRate> rates = supplyRateDao.findAll();
        return mapper.supplyRatesToDto(rates);
    }

    @Override
    public List<SupplyRateDto> getAllSupplyRatesForType(Long typeId) {
        List<SupplyRate> rates = supplyRateDao.findRatesForType(typeId);
        return mapper.supplyRatesToDto(rates);
    }

    @Override
    public SupplyRateDto newSupplyRate(SupplyRateDto supplyRateDto) {
        Long type = supplyRateDto.getEquipmentType();
        Optional<EquipmentType> typeOptional = equipmentTypeDao.find(type);
        if (typeOptional.isPresent()) {
            SupplyRate supplyRate = mapper.supplyRateFromDto(supplyRateDto);
            supplyRate.setEquipmentType(typeOptional.get());
            SupplyRate newSupplyRate = supplyRateDao.create(supplyRate);
            return mapper.supplyRateToDto(newSupplyRate);
        } else {
            throw new IllegalArgumentException("EquipmentType not exist");
        }
    }

    @Override
    public SupplyRateDto updateSupplyRate(SupplyRateDto supplyRateDto) {
        Long id = supplyRateDto.getId();
        Optional<SupplyRate> optional = supplyRateDao.find(id);
        if (optional.isPresent()) {
            Optional<EquipmentType> equipmentTypeOpt = equipmentTypeDao.find(supplyRateDto.getEquipmentType());
            if (equipmentTypeOpt.isPresent()) {
                SupplyRate supplyRate = mapper.supplyRateFromDto(supplyRateDto);
                supplyRateDao.update(supplyRate);
                return supplyRateDto;
            } else {
                throw new IllegalArgumentException("EquipmentType not exist");
            }
        } else {
            throw new IllegalArgumentException("SupplyRate not exist");
        }
    }
}
