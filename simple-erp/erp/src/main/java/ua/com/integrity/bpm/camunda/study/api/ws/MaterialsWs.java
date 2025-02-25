package ua.com.integrity.bpm.camunda.study.api.ws;

import ua.com.integrity.bpm.camunda.study.dto.api.BatchWriteOffRequestDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.BatchOfMaterialDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.ConsumptionRateDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.MaterialDto;
import ua.com.integrity.bpm.camunda.study.service.MaterialsService;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebService(serviceName = "materials", portName = "materials")
public class MaterialsWs {

    @Inject
    private MaterialsService materialsService;

    @WebMethod(operationName = "add-material")
    @WebResult(name = "material")
    public MaterialDto addMaterial(@WebParam(name = "material-data") MaterialDto materialDto){
        return materialsService.addMaterial(materialDto);
    }

    @WebMethod(operationName = "get-all-materials")
    @WebResult(name = "materials")
    public List<MaterialDto> allMaterials() {
        return materialsService.allMaterials();
    }

    @WebMethod(operationName = "get-batch")
    @WebResult(name = "batch")
    public BatchOfMaterialDto getBatch(@WebParam(name = "batch-id") Long id) {
        Optional<BatchOfMaterialDto> batch = materialsService.getBatch(id);
        if (batch.isPresent()) {
            return batch.get();
        }
        throw new IllegalArgumentException("Batch not exist");
    }

    @WebMethod(operationName = "find-batches")
    @WebResult(name = "batches")
    public List<BatchOfMaterialDto> getAllBatches(@WebParam(name = "material-id") Long materialId) {
        if (materialId != null) {
            return materialsService.getBatchesByMaterial(materialId);
        }
        return materialsService.getAllBatches();
    }

    @WebMethod(operationName = "new-batch")
    @WebResult(name = "batch")
    public BatchOfMaterialDto addNewBatch(@WebParam(name = "batch-data") BatchOfMaterialDto batchOfMaterialDto) {
        return materialsService.addNewBatch(batchOfMaterialDto);
    }

    @WebMethod(operationName = "delete-batch")
    public void deleteBatch(@WebParam(name = "batch-id") Long id) {
        materialsService.deleteBatch(id);
    }

    @WebMethod(operationName = "write-off-from-batch")
    public void writeOffFromBatch(@WebParam(name = "write-off-data") BatchWriteOffRequestDto request) {
        try {
            BigDecimal amount = new BigDecimal(request.getAmount());
            materialsService.writeOffFromBatch(request.getBatchId(), amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format");
        }
    }

    @WebMethod(operationName = "get-consumption-rate")
    @WebResult(name = "consumption-rate")
    public ConsumptionRateDto getRate(@WebParam(name = "rate-id") Long id) {
        Optional<ConsumptionRateDto> rate = materialsService.getRate(id);
        if (rate.isPresent()) {
            return rate.get();
        }
        throw new IllegalArgumentException("Rate not found");
    }

    @WebMethod(operationName = "get-all-consumption-rates")
    @WebResult(name = "consumption-rates")
    public List<ConsumptionRateDto> getAllRates(@WebParam(name = "material-id") Long materialId) {
        if (materialId != null) {
            return materialsService.getAllRatesByMaterial(materialId);
        }
        return materialsService.getAllRates();
    }

    @WebMethod(operationName = "add-consumption-rate")
    @WebResult(name = "consumption-rate")
    public ConsumptionRateDto newRate(@WebParam(name = "rate-data") ConsumptionRateDto consumptionRateDto) {
        return materialsService.newRate(consumptionRateDto);
    }

    @WebMethod(operationName = "update-consumption-rate")
    public void updateRate(@WebParam(name = "rate-data") ConsumptionRateDto consumptionRateDto) {
        materialsService.updateRate(consumptionRateDto);
    }

    @WebMethod(operationName = "delete-consumption-rate")
    public void deleteRate(@WebParam(name = "rate-id") Long id) {
        materialsService.deleteRate(id);
    }


}
