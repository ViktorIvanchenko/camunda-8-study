package ua.com.integrity.taskvacationimpl.process;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;
import ua.com.integrity.taskvacationimpl.dto.vacation.VacationDto;
import ua.com.integrity.taskvacationimpl.integration.IVacationService;

@Component
public class DeleteRequest {

    private final IVacationService iVacationService;

    public DeleteRequest(IVacationService iVacationService) {
        this.iVacationService = iVacationService;
    }

    @JobWorker(type = "deleteRequest", fetchAllVariables = true)
    public void execute(@Variable("vacation") VacationDto vacationDto) {
        if (vacationDto != null) {
            iVacationService.deleteVacation(vacationDto);
        }
    }
}
