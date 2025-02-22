package ua.com.integrity.bpm.camunda.study.api.rest;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import ua.com.integrity.bpm.camunda.study.dto.EmployeeDto;
import ua.com.integrity.bpm.camunda.study.dto.OrgUnitDto;
import ua.com.integrity.bpm.camunda.study.dto.PositionDto;
import ua.com.integrity.bpm.camunda.study.dto.equipment.SupplyRateDto;
import ua.com.integrity.bpm.camunda.study.dto.vacation.VacationDto;
import ua.com.integrity.bpm.camunda.study.service.OrgStructureService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("org-structure")
@Produces(MediaType.APPLICATION_JSON)
public class OrgStructureRest {

    @Inject
    private OrgStructureService orgStructureService;

    @GET
    @Path("unit")
    public List<OrgUnitDto> getUnits(){
        return orgStructureService.getUnits();
    }

    @GET
    @Path("unit/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return unit data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = OrgUnitDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Unit not exist"
            )
    })
    public Response getUnit(@PathParam("id") Long unitId){
        Optional<OrgUnitDto> unit = orgStructureService.getUnit(unitId);
        if (unit.isPresent()) {
            return Response.ok(unit.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("unit")
    @Consumes(MediaType.APPLICATION_JSON)
    public OrgUnitDto createUnit(@Valid OrgUnitDto orgUnitDto) {
        return orgStructureService.createUnit(orgUnitDto);
    }

    @GET
    @Path("position/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return position data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = PositionDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Position not exist"
            )
    })
    public Response getPosition(@PathParam("id") Long positionId) {
        Optional<PositionDto> position = orgStructureService.getPosition(positionId);
        if (position.isPresent()) {
            return Response.ok(position.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("position")
    public List<PositionDto> getPositions(){
        return orgStructureService.getPositions();
    }

    @POST
    @Path("position")
    @Consumes(MediaType.APPLICATION_JSON)
    public PositionDto createPosition(@Valid @NotNull PositionDto positionDto){
        return orgStructureService.createPosition(positionDto);
    }


    @PATCH
    @Path("position/{id}/supply-rate")
    @Consumes(MediaType.APPLICATION_JSON)
    public PositionDto addSupplyRate(@PathParam("id") Long positionId, SupplyRateDto supplyRateDto) {
        return orgStructureService.addSupplyRate(positionId, supplyRateDto.getId());
    }

    @DELETE
    @Path("position/{id}/supply-rate/{rateId}")
    public PositionDto removeSupplyRate(@PathParam("id") Long positionId, @PathParam("rateId") Long rateId) {
        return orgStructureService.removeSupplyRate(positionId, rateId);
    }

    @GET
    @Path("employee/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return employee data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = EmployeeDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Employee not exist"
            )
    })
    public Response getEmployee(@PathParam("id") Long id) {
        Optional<EmployeeDto> employee = orgStructureService.getEmployee(id);
        if (employee.isPresent()) {
            return Response.ok(employee.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("employee/login/{login}")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Return employee data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = EmployeeDto.class
                            )
                    )
            ),
            @APIResponse(responseCode = "404",
                    description = "Employee not exist"
            )
    })
    public Response getEmployee(@PathParam("login") String login) {
        Optional<EmployeeDto> employee = orgStructureService.getEmployee(login);
        if (employee.isPresent()) {
            return Response.ok(employee.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("employee")
    public List<EmployeeDto> getEmployees() {
        return orgStructureService.getEmployees();
    }


    @POST
    @Path("employee")
    @Consumes(MediaType.APPLICATION_JSON)
    public EmployeeDto newEmployee(@Valid EmployeeDto employeeDto) {
        return orgStructureService.newEmployee(employeeDto);
    }

    @PUT
    @Path("employee/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEmployee(@PathParam("id") Long id, @Valid EmployeeDto employeeDto) {
        if (id.equals(employeeDto.getId())) {
            orgStructureService.updateEmployee(employeeDto);
        } else {
            throw new IllegalArgumentException("Employee id not matches with update body");
        }
    }

    @GET
    @Path("vacation")
    public List<VacationDto> getVacations() {
        return orgStructureService.getVacations();
    }

    @POST
    @Path("vacation")
    @Consumes(MediaType.APPLICATION_JSON)
    public VacationDto newVacation(@Valid VacationDto vacationDto) {
        return orgStructureService.newVacation(vacationDto);
    }

    @DELETE
    @Path("vacation/{id}")
    public void cancelVacation(@PathParam("id") Long id) {
        orgStructureService.cancelVacation(id);
    }

}
