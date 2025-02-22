package ua.com.integrity.taskvacationimpl.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;
import ua.com.integrity.taskvacationimpl.dto.OrgUnitDto;
import ua.com.integrity.taskvacationimpl.dto.PositionDto;
import ua.com.integrity.taskvacationimpl.dto.trip.TripDto;
import ua.com.integrity.taskvacationimpl.dto.vacation.VacationDto;

import java.util.List;

@FeignClient(name = "defaultApi", url = "${erp.baseUrl}")
public interface ErpApiClient {


    @GetMapping(value = "/api/rest/org-structure/employee")
    List<EmployeeDto> apiRestOrgStructureEmployeeGet();

    /**
     * Similar to <code>apiRestOrgStructureEmployeeGet</code> but it also returns the http response headers .
     *
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/org-structure/employee")
    ResponseEntity<List<EmployeeDto>> apiRestOrgStructureEmployeeGetWithHttpInfo();

    /**
     * @PathVariable id (required)
     * @return EmployeeDto
     */
    @GetMapping("/api/rest/org-structure/employee/{id}")
    EmployeeDto apiRestOrgStructureEmployeeIdGet(@PathVariable("id") Long id);

    /**
     * Similar to <code>apiRestOrgStructureEmployeeIdGet</code> but it also returns the http response headers .
     *
     * @PathVariable id (required)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/org-structure/employee/{id}")
    ResponseEntity<EmployeeDto> apiRestOrgStructureEmployeeIdGetWithHttpInfo(@PathVariable("id") Long id);

    /**
     * @PathVariable id          (required)
     * @PathVariable employeeDto (optional)
     */
    @PutMapping("/api/rest/org-structure/employee/{id}")
    void apiRestOrgStructureEmployeeIdPut(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto);

    /**
     * Similar to <code>apiRestOrgStructureEmployeeIdPut</code> but it also returns the http response headers .
     *
     * @PathVariable id          (required)
     * @PathVariable employeeDto (optional)
     */
    @PutMapping("/api/rest/org-structure/employee/{id}")
    ResponseEntity<Void> apiRestOrgStructureEmployeeIdPutWithHttpInfo(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto);


    /**
     * @PathVariable login (required)
     * @return EmployeeDto
     */
    @GetMapping("/api/rest/org-structure/employee/login/{login}")
    EmployeeDto apiRestOrgStructureEmployeeLoginLoginGet(@PathVariable("login") String login);

    /**
     * Similar to <code>apiRestOrgStructureEmployeeLoginLoginGet</code> but it also returns the http response headers .
     *
     * @PathVariable login (required)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/org-structure/employee/login/{login}")
    ResponseEntity<EmployeeDto> apiRestOrgStructureEmployeeLoginLoginGetWithHttpInfo(@PathVariable("login") String login);

    /**
     * @PathVariable employeeDto (optional)
     * @return EmployeeDto
     */
    @PostMapping("/api/rest/org-structure/employee")
    EmployeeDto apiRestOrgStructureEmployeePost(@RequestBody EmployeeDto employeeDto);

    /**
     * Similar to <code>apiRestOrgStructureEmployeePost</code> but it also returns the http response headers .
     *
     * @PathVariable employeeDto (optional)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @PostMapping("/api/rest/org-structure/employee")
    ResponseEntity<EmployeeDto> apiRestOrgStructureEmployeePostWithHttpInfo(@RequestBody EmployeeDto employeeDto);


    /**
     * @return List&lt;PositionDto&gt;
     */
    @GetMapping("/api/rest/org-structure/position")
    List<PositionDto> apiRestOrgStructurePositionGet();

    /**
     * Similar to <code>apiRestOrgStructurePositionGet</code> but it also returns the http response headers .
     *
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/org-structure/position")
    ResponseEntity<List<PositionDto>> apiRestOrgStructurePositionGetWithHttpInfo();

    /**
     * @PathVariable id (required)
     * @return PositionDto
     */
    @GetMapping("/api/rest/org-structure/position/{id}")
    PositionDto apiRestOrgStructurePositionIdGet(@PathVariable("id") Long id);

    /**
     * Similar to <code>apiRestOrgStructurePositionIdGet</code> but it also returns the http response headers .
     *
     * @PathVariable id (required)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/org-structure/position/{id}")
    ResponseEntity<PositionDto> apiRestOrgStructurePositionIdGetWithHttpInfo(@PathVariable("id") Long id);


    /**
     * @PathVariable id     (required)
     * @PathVariable rateId (required)
     * @return PositionDto
     */
    @DeleteMapping("/api/rest/org-structure/position/{id}/supply-rate/{rateId}")
    PositionDto apiRestOrgStructurePositionIdSupplyRateRateIdDelete(@PathVariable("id") Long id, @PathVariable("rateId") Long rateId);

    /**
     * Similar to <code>apiRestOrgStructurePositionIdSupplyRateRateIdDelete</code> but it also returns the http response headers .
     *
     * @PathVariable id     (required)
     * @PathVariable rateId (required)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @DeleteMapping("/api/rest/org-structure/position/{id}/supply-rate/{rateId}")
    ResponseEntity<PositionDto> apiRestOrgStructurePositionIdSupplyRateRateIdDeleteWithHttpInfo(@PathVariable("id") Long id, @PathVariable("rateId") Long rateId);

    /**
     * @PathVariable positionDto (optional)
     * @return PositionDto
     */
    @PostMapping("/api/rest/org-structure/position")
    PositionDto apiRestOrgStructurePositionPost(@RequestBody PositionDto positionDto);

    /**
     * Similar to <code>apiRestOrgStructurePositionPost</code> but it also returns the http response headers .
     *
     * @PathVariable positionDto (optional)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @PostMapping("/api/rest/org-structure/position")
    ResponseEntity<PositionDto> apiRestOrgStructurePositionPostWithHttpInfo(@RequestBody PositionDto positionDto);


    /**
     * @return List&lt;OrgUnitDto&gt;
     */
    @GetMapping("/api/rest/org-structure/unit")
    List<OrgUnitDto> apiRestOrgStructureUnitGet();

    /**
     * Similar to <code>apiRestOrgStructureUnitGet</code> but it also returns the http response headers .
     *
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/org-structure/unit")
    ResponseEntity<List<OrgUnitDto>> apiRestOrgStructureUnitGetWithHttpInfo();


    /**
     * @PathVariable id (required)
     * @return OrgUnitDto
     */
    @GetMapping("/api/rest/org-structure/unit/{id}")
    OrgUnitDto apiRestOrgStructureUnitIdGet(@PathVariable("id") Long id);

    /**
     * Similar to <code>apiRestOrgStructureUnitIdGet</code> but it also returns the http response headers .
     *
     * @PathVariable id (required)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/org-structure/unit/{id}")
    ResponseEntity<OrgUnitDto> apiRestOrgStructureUnitIdGetWithHttpInfo(@PathVariable("id") Long id);

    /**
     * @PathVariable orgUnitDto (optional)
     * @return OrgUnitDto
     */
    @PostMapping("/api/rest/org-structure/unit")
    OrgUnitDto apiRestOrgStructureUnitPost(@RequestBody OrgUnitDto orgUnitDto);

    /**
     * Similar to <code>apiRestOrgStructureUnitPost</code> but it also returns the http response headers .
     *
     * @PathVariable orgUnitDto (optional)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @PostMapping("/api/rest/org-structure/unit")
    ResponseEntity<OrgUnitDto> apiRestOrgStructureUnitPostWithHttpInfo(@RequestBody OrgUnitDto orgUnitDto);

    /**
     * @return List&lt;VacationDto&gt;
     */
    @GetMapping("/api/rest/org-structure/vacation")
    List<VacationDto> apiRestOrgStructureVacationGet();

    /**
     * Similar to <code>apiRestOrgStructureVacationGet</code> but it also returns the http response headers .
     *
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/org-structure/vacation")
    ResponseEntity<List<VacationDto>> apiRestOrgStructureVacationGetWithHttpInfo();

    /**
     * @PathVariable id (required)
     */
    @DeleteMapping("/api/rest/org-structure/vacation/{id}")
    void apiRestOrgStructureVacationIdDelete(@PathVariable("id") Long id);

    /**
     * Similar to <code>apiRestOrgStructureVacationIdDelete</code> but it also returns the http response headers .
     *
     * @PathVariable id (required)
     */
    @DeleteMapping("/api/rest/org-structure/vacation/{id}")
    ResponseEntity<Void> apiRestOrgStructureVacationIdDeleteWithHttpInfo(@PathVariable("id") Long id);


    /**
     * @PathVariable vacationDto (optional)
     * @return VacationDto
     */
    @PostMapping("/api/rest/org-structure/vacation")
    VacationDto apiRestOrgStructureVacationPost(VacationDto vacationDto);

    /**
     * Similar to <code>apiRestOrgStructureVacationPost</code> but it also returns the http response headers .
     *
     * @PathVariable vacationDto (optional)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @PostMapping("/api/rest/org-structure/vacation")
    ResponseEntity<VacationDto> apiRestOrgStructureVacationPostWithHttpInfo(@RequestBody VacationDto vacationDto);

    /**
     * @return List&lt;TripDto&gt;
     */
    @GetMapping("/api/rest/trips")
    List<TripDto> apiRestTripsGet();

    /**
     * Similar to <code>apiRestTripsGet</code> but it also returns the http response headers .
     *
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @GetMapping("/api/rest/trips")
    ResponseEntity<List<TripDto>> apiRestTripsGetWithHttpInfo();

    /**
     * @PathVariable tripDto (optional)
     * @return TripDto
     */
    @PostMapping("/api/rest/trips")
    TripDto apiRestTripsPost(@RequestBody TripDto tripDto);

    /**
     * Similar to <code>apiRestTripsPost</code> but it also returns the http response headers .
     *
     * @PathVariable tripDto (optional)
     * @return A ResponseEntity that wraps the response boyd and the http headers.
     */
    @PostMapping("/api/rest/trips")
    ResponseEntity<TripDto> apiRestTripsPostWithHttpInfo(@RequestBody TripDto tripDto);
}
