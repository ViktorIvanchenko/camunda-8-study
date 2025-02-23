package ua.com.integrity.bpm.camunda.study.api.rest;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import ua.com.integrity.bpm.camunda.study.dto.api.BatchWriteOffRequestDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.BatchOfMaterialDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.ConsumptionRateDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.MaterialDto;
import ua.com.integrity.bpm.camunda.study.service.MaterialsService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Path("material")
@Produces(MediaType.APPLICATION_JSON)
public class MaterialsRest {

    @Inject
    private MaterialsService materialsService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public MaterialDto addMaterial(@Valid MaterialDto materialDto){
        return materialsService.addMaterial(materialDto);
    }

    @GET
    public List<MaterialDto> allMaterials() {
        return materialsService.allMaterials();
    }

    @GET
    @Path("batch/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return batch data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = BatchOfMaterialDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Batch not exist"
            )
    })
    public Response getBatch(@PathParam("id") Long id) {
        Optional<BatchOfMaterialDto> batch = materialsService.getBatch(id);
        if (batch.isPresent()) {
            return Response.ok(batch.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("batch")
    public List<BatchOfMaterialDto> getAllBatches(@QueryParam("material") Long materialId) {
        if (materialId != null) {
            return materialsService.getBatchesByMaterial(materialId);
        }
        return materialsService.getAllBatches();
    }

    @POST
    @Path("batch")
    @Consumes(MediaType.APPLICATION_JSON)
    public BatchOfMaterialDto addNewBatch(@Valid BatchOfMaterialDto batchOfMaterialDto) {
        return materialsService.addNewBatch(batchOfMaterialDto);
    }

    @DELETE
    @Path("batch/{id}")
    public void deleteBatch(@PathParam("id") Long id) {
        materialsService.deleteBatch(id);
    }

    @PATCH
    @Path("batch/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void writeOffFromBatch(@PathParam("id") Long batchId, @Valid BatchWriteOffRequestDto request) {
        if (batchId.equals(request.getBatchId())) {
            try {
                BigDecimal amount = new BigDecimal(request.getAmount());
                materialsService.writeOffFromBatch(batchId, amount);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid amount format");
            }
        } else {
            throw new IllegalArgumentException("Batch id not match with request body");
        }
    }

    @GET
    @Path("consumption-rate/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return consumption rate data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ConsumptionRateDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Consumption rate not exist"
            )
    })
    public Response getRate(@PathParam("id") Long id) {
        Optional<ConsumptionRateDto> rate = materialsService.getRate(id);
        if (rate.isPresent()) {
            return Response.ok(rate.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("consumption-rate")
    public List<ConsumptionRateDto> getAllRates(@QueryParam("material") Long materialId) {
        if (materialId != null) {
            return materialsService.getAllRatesByMaterial(materialId);
        }
        return materialsService.getAllRates();
    }

    @POST
    @Path("consumption-rate")
    @Consumes(MediaType.APPLICATION_JSON)
    public ConsumptionRateDto newRate(@Valid ConsumptionRateDto consumptionRateDto) {
        return materialsService.newRate(consumptionRateDto);
    }

    @PUT
    @Path("consumption-rate/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRate(@PathParam("id") Long id, @Valid ConsumptionRateDto consumptionRateDto) {
        if (id.equals(consumptionRateDto.getId())) {
            materialsService.updateRate(consumptionRateDto);
        } else {
            throw new IllegalArgumentException("ConsumptionRate id not math with request body");
        }
    }

//    @DELETE
//    @Path("consumption-rate/{id}")
//    public void deleteRate(@PathParam("id") Long id) {
//        materialsService.deleteRate(id);
//    }


}
