package ua.com.integrity.taskvacationimpl.bpmn;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;
import ua.com.integrity.taskvacationimpl.integration.IEmployeeService;
import ua.com.integrity.taskvacationimpl.process.FindInitiator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@MockitoSettings
class WorkerTest {

    @Mock
    ActivatedJob mockedJob;

    @Mock
    private IEmployeeService employeeService;

    @BeforeEach
    public void init() {
        when(employeeService.getEmployeeByLogin(anyString())).thenReturn(employeeDto);
        when(mockedJob.getVariable(anyString())).thenReturn("oglavniy");
    }

    @Test
    void testCustomerCreditWorker() {
        FindInitiator findInitiator = new FindInitiator(employeeService);

        Map<String, Serializable> variables = findInitiator.execute(mockedJob);
        assertThat(variables).contains(entry("employee", employeeDto));
    }

    EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .login("john_doe")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 15))
                .hireDate(LocalDate.of(2015, 10, 1))
                .fireDate(LocalDate.of(2022, 6, 30)) // Assuming employee is fired
                .position(11L) // Assuming position ID
                .build();

}
