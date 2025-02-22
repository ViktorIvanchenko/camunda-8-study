package ua.com.integrity.taskvacationimpl.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;
import ua.com.integrity.taskvacationimpl.integration.IEmployeeService;

import java.util.List;
import java.util.Map;


@Component
public class FindIAllUsers {

    private final IEmployeeService employeeService;

    @Autowired
    public FindIAllUsers(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @JobWorker(type = "findAllUsers")
    public Map<String, List<Map<String, String>>> execute(final ActivatedJob job) {
        List<EmployeeDto> allEmployees = employeeService.getAllEmployees();
        List<Map<String, String>> list = allEmployees.stream()
                .map(this::mapEmployee)
                .toList();
        return Map.of("employees", list);
    }

    private Map<String, String> mapEmployee(EmployeeDto employeeDto) {
        String userName = employeeDto.getFirstName() + " " + employeeDto.getLastName();
        return Map.of("label", userName, "value", employeeDto.getLogin());
    }
}
