package ua.com.integrity.bpm.camunda.study.service.impl;

import lombok.Setter;
import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.dao.material.BatchOfMaterialsDao;
import ua.com.integrity.bpm.camunda.study.dao.material.ConsumptionRateDao;
import ua.com.integrity.bpm.camunda.study.domain.materail.BatchOfMaterial;
import ua.com.integrity.bpm.camunda.study.domain.materail.ConsumptionRate;
import ua.com.integrity.bpm.camunda.study.domain.materail.Material;
import ua.com.integrity.bpm.camunda.study.dto.materail.BatchOfMaterialDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.ConsumptionRateDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.MaterialDto;
import ua.com.integrity.bpm.camunda.study.mapper.ErpMapper;
import ua.com.integrity.bpm.camunda.study.service.MaterialsService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Setter
@Transactional
public class MaterialsServiceImpl implements MaterialsService {

    @Inject
    private ErpMapper mapper;
    @Inject
    private GenericDao<Material, Long> materialDao;
    @Inject
    private BatchOfMaterialsDao batchesDao;
    @Inject
    private ConsumptionRateDao consumptionRateDao;

    @Override
    public MaterialDto addMaterial(MaterialDto materialDto) {
        Material material = mapper.materialFromDto(materialDto);
        Material newMaterial = materialDao.create(material);
        return mapper.materialToDto(newMaterial);
    }

    @Override
    public List<MaterialDto> allMaterials() {
        List<Material> materials = materialDao.findAll();
        return mapper.materialsToDto(materials);
    }

    @Override
    public Optional<BatchOfMaterialDto> getBatch(Long id) {
        Optional<BatchOfMaterial> batch = batchesDao.find(id);
        if (batch.isPresent()) {
            return Optional.of(mapper.batchOfMaterialToDto(batch.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<BatchOfMaterialDto> getAllBatches() {
        List<BatchOfMaterial> batches = batchesDao.findAll();
        return mapper.batchOfMaterialsToDto(batches);
    }

    @Override
    public List<BatchOfMaterialDto> getBatchesByMaterial(Long materialId) {
        List<BatchOfMaterial> batches = batchesDao.findAllForMaterial(materialId);
        return mapper.batchOfMaterialsToDto(batches);
    }

    @Override
    public BatchOfMaterialDto addNewBatch(BatchOfMaterialDto batchOfMaterialDto) {
        Long materialId = batchOfMaterialDto.getMaterial().getId();
        Optional<Material> material = materialDao.find(materialId);
        if (material.isPresent()) {
            BatchOfMaterial batch = mapper.batchOfMaterialFromDto(batchOfMaterialDto);
            if (batch.getRemainingAmount() == null) {
                batch.setRemainingAmount(batch.getPurchaseAmount());
            }
            BatchOfMaterial newBatch = batchesDao.create(batch);
            return mapper.batchOfMaterialToDto(newBatch);
        } else {
            throw new IllegalArgumentException("No such material");
        }
    }

    @Override
    public void deleteBatch(Long id) {
        Optional<BatchOfMaterial> batch = batchesDao.find(id);
        if (batch.isPresent()) {
            batchesDao.delete(id);
        }
    }

    @Override
    public void writeOffFromBatch(Long batchId, BigDecimal amount) {
        Optional<BatchOfMaterial> batchOpt = batchesDao.find(batchId);
        if(batchOpt.isPresent()) {
            BatchOfMaterial batch = batchOpt.get();
            BigDecimal remaining = batch.getRemainingAmount();
            if (remaining == null) {
                remaining = batch.getPurchaseAmount();
            }
            if (remaining.compareTo(amount) >= 0) {
                BigDecimal remainder = remaining.subtract(amount);
                batch.setRemainingAmount(remainder);
                batchesDao.update(batch);
            } else {
                String msg = String.format("Amount to withdraw %s is greater that amount remaining in batch (%s)", amount.toString(), remaining.toString());
                throw new IllegalArgumentException(msg);
            }
        } else {
            throw new IllegalArgumentException("Cannot find batch with such id");
        }
    }

    @Override
    public Optional<ConsumptionRateDto> getRate(Long id) {
        Optional<ConsumptionRate> consumptionRate = consumptionRateDao.find(id);
        if (consumptionRate.isPresent()){
            return Optional.of(mapper.consumptionRateToDto(consumptionRate.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<ConsumptionRateDto> getAllRates() {
        List<ConsumptionRate> rates = consumptionRateDao.findAll();
        return mapper.consumptionRatesToDto(rates);
    }

    @Override
    public List<ConsumptionRateDto> getAllRatesByMaterial(Long materialId) {
        List<ConsumptionRate> rates = consumptionRateDao.findAllForMaterial(materialId);
        return mapper.consumptionRatesToDto(rates);
    }

    @Override
    public ConsumptionRateDto newRate(ConsumptionRateDto consumptionRateDto) {
        Long materialId = consumptionRateDto.getMaterial().getId();
        Optional<Material> material = materialDao.find(materialId);
        if (material.isPresent()) {
            ConsumptionRate rate = mapper.consumptionRateFromDto(consumptionRateDto);
            ConsumptionRate newRate = consumptionRateDao.create(rate);
            return mapper.consumptionRateToDto(newRate);
        }
        throw new IllegalArgumentException("No such material");
    }

    @Override
    public void updateRate(ConsumptionRateDto consumptionRateDto) {
        Long id = consumptionRateDto.getId();
        Optional<ConsumptionRate> optional = consumptionRateDao.find(id);
        if (optional.isPresent()) {
            Long materialId = consumptionRateDto.getMaterial().getId();
            Optional<Material> material = materialDao.find(materialId);
            if (material.isPresent()) {
                ConsumptionRate rate = mapper.consumptionRateFromDto(consumptionRateDto);
                consumptionRateDao.update(rate);
            } else {
                throw new IllegalArgumentException("No such material");
            }
        } else {
            throw new IllegalArgumentException("Cannot find consumption rate with id " + id);
        }
    }

    @Override
    public void deleteRate(Long rateId) {
        Optional<ConsumptionRate> optional = consumptionRateDao.find(rateId);
        if (optional.isPresent()) {
            consumptionRateDao.delete(rateId);
        }
    }
}
