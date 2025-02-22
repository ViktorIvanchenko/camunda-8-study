package ua.com.integrity.taskvacationimpl.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;
import ua.com.integrity.taskvacationimpl.integration.IEmployeeService;

import java.io.Serializable;
import java.util.Map;


@Component
public class FindInitiator {

    private IEmployeeService employeeService;

    @Autowired
    public FindInitiator(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @JobWorker(type = "findInitiator")
    public Map<String, Serializable> execute(final ActivatedJob job) {
        String login = (String) job.getVariable("initiatorID");
        EmployeeDto employee = employeeService.getEmployeeByLogin(login);
        String fullName = employee.getLastName() + " " + employee.getFirstName();
        return Map.of("employee", employee, "vacation_full_name", fullName);
    }
}
