package ua.com.integrity.taskvacationimpl.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;
import ua.com.integrity.taskvacationimpl.dto.vacation.VacationDto;

import java.util.Collections;
import java.util.Map;

@Component
public class SendMail {

    @JobWorker(type = "sendMail", fetchAllVariables = true)
    public void execute(final ActivatedJob job) {
    }
}
