package ua.com.integrity.taskvacationimpl.integration;


import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;

import java.util.List;

public interface IEmployeeService {
    EmployeeDto getEmployeeByLogin(String login);

    List<EmployeeDto> getAllEmployees();
}
