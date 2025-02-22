package ua.com.integrity.taskvacationimpl.integration.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;
import ua.com.integrity.taskvacationimpl.integration.ErpApiClient;
import ua.com.integrity.taskvacationimpl.integration.IEmployeeService;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final ErpApiClient erpApiClient;

    @Autowired
    public EmployeeServiceImpl(ErpApiClient erpApiClient) {
        this.erpApiClient = erpApiClient;
    }

    @Override
    @SneakyThrows
    public EmployeeDto getEmployeeByLogin(String login) {
        return erpApiClient.apiRestOrgStructureEmployeeLoginLoginGet(login);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return erpApiClient.apiRestOrgStructureEmployeeGet();
    }
}
