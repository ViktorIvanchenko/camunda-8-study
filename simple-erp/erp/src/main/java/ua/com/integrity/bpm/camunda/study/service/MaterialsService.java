package ua.com.integrity.bpm.camunda.study.service;

import ua.com.integrity.bpm.camunda.study.dto.materail.BatchOfMaterialDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.ConsumptionRateDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.MaterialDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MaterialsService {

    MaterialDto addMaterial(MaterialDto materialDto);
    List<MaterialDto> allMaterials();

    Optional<BatchOfMaterialDto> getBatch(Long id);
    List<BatchOfMaterialDto> getAllBatches();
    List<BatchOfMaterialDto> getBatchesByMaterial(Long materialId);
    BatchOfMaterialDto addNewBatch(BatchOfMaterialDto batchOfMaterialDto);
    void deleteBatch(Long id);
    void writeOffFromBatch(Long batchId, BigDecimal amount);

    Optional<ConsumptionRateDto> getRate(Long id);
    List<ConsumptionRateDto> getAllRates();
    List<ConsumptionRateDto> getAllRatesByMaterial(Long materialId);
    ConsumptionRateDto newRate(ConsumptionRateDto consumptionRateDto);
    void updateRate(ConsumptionRateDto consumptionRateDto);
    void deleteRate(Long rateId);

}
