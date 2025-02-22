package ua.com.integrity.bpm.camunda.study.api.rest;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import ua.com.integrity.bpm.camunda.study.dto.EmployeeDto;
import ua.com.integrity.bpm.camunda.study.dto.api.EquipmentAssigningRequest;
import ua.com.integrity.bpm.camunda.study.dto.api.EquipmentCommissioningRequest;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.EquipmentTypeDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.MaintenanceDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.SupplyRateDto;
import ua.com.integrity.bpm.camunda.study.dto.materail.ConsumptionRateDto;
import ua.com.integrity.bpm.camunda.study.service.EquipmentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Path("equipment")
@Produces(MediaType.APPLICATION_JSON)
public class EquipmentRest {

    @Inject
    private EquipmentService equipmentService;

    @GET
    @Path("{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return equipment data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = EquipmentDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Equipment not exist"
            )
    })
    public Response getEquipment(@PathParam("id") String equipmentId) {
        Optional<EquipmentDto> equipment = equipmentService.getEquipment(equipmentId);
        if (equipment.isPresent()) {
            return Response.ok(equipment.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public List<EquipmentDto> getEquipmentByType(
        @QueryParam("type") Long typeId,
        @QueryParam("withDecommissioned") @DefaultValue("false") Boolean withDecommissioned
    ) {
        if (typeId != null) {
            return equipmentService.getEquipmentByType(typeId, withDecommissioned);
        }
        return equipmentService.getAllEquipment(withDecommissioned);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public EquipmentDto newEquipment(@Valid EquipmentDto equipmentDto) {
        return equipmentService.newEquipment(equipmentDto);
    }

    @PATCH
    @Path("{id}/commission")
    @Consumes(MediaType.APPLICATION_JSON)
    public EquipmentDto commissionEquipment(@PathParam("id") String equipmentId, @Valid EquipmentCommissioningRequest request) {
        if (equipmentId.equals(request.getSerialNumber())) {
            try {
                LocalDate date = LocalDate.parse(request.getDate());
                return equipmentService.commissionEquipment(equipmentId, date);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid commissioning date format");
            }
        } else {
            throw new IllegalArgumentException("Equipment s/n not match with request body");
        }
    }

    @PATCH
    @Path("{id}/decommission")
    @Consumes(MediaType.APPLICATION_JSON)
    public EquipmentDto decommissionEquipment(@PathParam("id") String equipmentId, @Valid EquipmentCommissioningRequest request) {
        if (equipmentId.equals(request.getSerialNumber())) {
            try {
                LocalDate date = LocalDate.parse(request.getDate());
                return equipmentService.decommissionEquipment(equipmentId, date);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid decommissioning date format");
            }
        } else {
            throw new IllegalArgumentException("Equipment s/n not match with request body");
        }
    }

    @PATCH
    @Path("{id}/assign")
    @Consumes(MediaType.APPLICATION_JSON)
    public EquipmentDto assignEquipment(@PathParam("id") String equipmentId, @Valid EquipmentAssigningRequest request) {
        if (equipmentId.equals(request.getSerialNumber())) {
            return equipmentService.assignEquipment(equipmentId, request.getEmployeeId());
        } else {
            throw new IllegalArgumentException("Equipment s/n not match with request body");
        }
    }

    @PATCH
    @Path("{id}/retrieve")
    public EquipmentDto retrieveEquipment(@PathParam("id") String equipmentId) {
        return equipmentService.retrieveEquipment(equipmentId);
    }

    @GET
    @Path("type/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return equipment type data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = EquipmentTypeDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Equipment type not exist"
            )
    })
    public Response getEquipmentType(@PathParam("id") Long typeId) {
        Optional<EquipmentTypeDto> equipmentType = equipmentService.getEquipmentType(typeId);
        if (equipmentType.isPresent()) {
            return Response.ok(equipmentType.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("type")
    public List<EquipmentTypeDto> getAllEquipmentType() {
        return equipmentService.getAllEquipmentType();
    }

    @POST
    @Path("type")
    @Consumes(MediaType.APPLICATION_JSON)
    public EquipmentTypeDto newEquipmentType(@Valid EquipmentTypeDto equipmentTypeDto) {
        return equipmentService.newEquipmentType(equipmentTypeDto);
    }

    @PUT
    @Path("type/{id}/maintenance")
    @Consumes(MediaType.APPLICATION_JSON)
    public EquipmentTypeDto addMaintenance(@PathParam("id") Long typeId, MaintenanceDto maintenanceDto) {
        return equipmentService.addMaintenance(typeId, maintenanceDto.getId());
    }

    @DELETE
    @Path("type/{id}/maintenance/{maintenanceId}")
    public EquipmentTypeDto removeMaintenance(@PathParam("id") Long typeId, @PathParam("maintenanceId")  Long maintenanceId) {
        return equipmentService.removeMaintenance(typeId,maintenanceId);
    }

    @PUT
    @Path("type/{id}/consumption-rate")
    @Consumes(MediaType.APPLICATION_JSON)
    public EquipmentTypeDto addConsumptionRate(@PathParam("id") Long typeId, ConsumptionRateDto rateDto) {
        return equipmentService.addConsumptionRate(typeId, rateDto.getId());
    }

    @DELETE
    @Path("type/{id}/consumption-rate/{rateId}")
    public EquipmentTypeDto removeConsumptionRate(@PathParam("id") Long typeId, @PathParam("rateId") Long rateId) {
        return equipmentService.removeConsumptionRate(typeId, rateId);
    }

    @GET
    @Path("maintenance/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return maintenance data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = MaintenanceDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Maintenance not exist"
            )
    })
    public Response getMaintenance(@PathParam("id") Long id) {
        Optional<MaintenanceDto> maintenance = equipmentService.getMaintenance(id);
        if (maintenance.isPresent()) {
            return Response.ok(maintenance.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("maintenance")
    public List<MaintenanceDto> getAllMaintenances() {
        return equipmentService.getAllMaintenances();
    }

    @POST
    @Path("maintenance")
    @Consumes(MediaType.APPLICATION_JSON)
    public MaintenanceDto newMaintenance(@Valid MaintenanceDto maintenanceDto) {
        return equipmentService.newMaintenance(maintenanceDto);
    }

    @PUT
    @Path("maintenance/{id}/consumption-rate")
    @Consumes(MediaType.APPLICATION_JSON)
    public MaintenanceDto addConsumptionRateToMaintenance(@PathParam("id") Long maintenanceId, ConsumptionRateDto rateDto){
        return equipmentService.addConsumptionRateToMaintenance(maintenanceId, rateDto.getId());
    }

    @DELETE
    @Path("maintenance/{id}/consumption-rate/{rateId}")
    public MaintenanceDto removeConsumptionRateFromMaintenance(@PathParam("id") Long maintenanceId, @PathParam("rateId") Long rateId){
        return equipmentService.removeConsumptionRateFromMaintenance(maintenanceId, rateId);
    }

    @GET
    @Path("supply-rate/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return supply rate data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = SupplyRateDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Supply rate not exist"
            )
    })
    public Response findSupplyRate(@PathParam("id") Long id) {
        Optional<SupplyRateDto> supplyRate = equipmentService.findSupplyRate(id);
        if (supplyRate.isPresent()) {
            return Response.ok(supplyRate.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("supply-rate")
    public List<SupplyRateDto> getAllSupplyRates(@QueryParam("type") Long typeId) {
        if (typeId != null) {
            return equipmentService.getAllSupplyRatesForType(typeId);
        }
        return equipmentService.getAllSupplyRates();
    }

    @POST
    @Path("supply-rate")
    @Consumes(MediaType.APPLICATION_JSON)
    public SupplyRateDto newSupplyRate(@Valid SupplyRateDto supplyRateDto) {
        return equipmentService.newSupplyRate(supplyRateDto);
    }

    @PUT
    @Path("supply-rate/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public SupplyRateDto updateSupplyRate(@PathParam("id") Long id, @Valid SupplyRateDto dto) {
        if (id.equals(dto.getId())) {
            return equipmentService.updateSupplyRate(dto);
        } else {
            throw new IllegalArgumentException("Supply rate id not match with body");
        }
    }

}
