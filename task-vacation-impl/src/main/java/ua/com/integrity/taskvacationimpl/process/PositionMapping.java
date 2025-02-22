package ua.com.integrity.taskvacationimpl.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;
import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;
import ua.com.integrity.taskvacationimpl.dto.PositionDto;
import ua.com.integrity.taskvacationimpl.integration.IOrgUnitService;

import java.util.Map;

@Component
public class PositionMapping {

    private final IOrgUnitService iOrgUnitService;

    public PositionMapping(IOrgUnitService iOrgUnitService) {
        this.iOrgUnitService = iOrgUnitService;
    }

    @JobWorker(type = "positionMapping", fetchAllVariables = true)
    public Map<String, Long> execute(final ActivatedJob job, @Variable EmployeeDto employee) {
        long position = employee.getPosition();
        PositionDto positionDto = iOrgUnitService.getPosition(position);
        long department = positionDto.getOrgUnit();
        return Map.of("department", department);
    }
}
