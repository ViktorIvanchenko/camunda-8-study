package ua.com.integrity.taskvacationimpl.bpmn;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;
import ua.com.integrity.taskvacationimpl.dto.PositionDto;
import ua.com.integrity.taskvacationimpl.dto.vacation.VacationDto;
import ua.com.integrity.taskvacationimpl.dto.vacation.VacationType;
import ua.com.integrity.taskvacationimpl.integration.IEmployeeService;
import ua.com.integrity.taskvacationimpl.integration.IOrgUnitService;
import ua.com.integrity.taskvacationimpl.integration.IVacationService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;
import static io.camunda.zeebe.protocol.Protocol.USER_TASK_JOB_TYPE;
import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceCompleted;
import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceHasPassedElement;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


/**
 * Note that the test engines requires Java version >= 21.
 * If you cannot run on this Java version, you can use Testcontainers instead by choosing profile.
 * Testcontainers require that you have a Docker installation locally available on the developer machine.
 * <p>
 *  More tests example:
 *  https://github.com/camunda-community-hub/camunda-8-examples/
 * <p>
 *  Documentation:
 *  https://docs.camunda.io/docs/components/best-practices/development/testing-process-definitions/#technical-setup-using-spring
 */

@SpringBootTest
@ZeebeSpringTest
public class ProcessTest {

    public static final String PROCESS_ID = "task-vacation";

    @Autowired
    private ZeebeClient client;

    @Autowired
    private ZeebeTestEngine engine;

    @MockBean
    private IVacationService iVacationService;

    @MockBean
    private IEmployeeService employeeService;

    @MockBean
    private IOrgUnitService iOrgUnitService;

    @BeforeEach
    public void setup() {
        DeploymentEvent event = client.newDeployResourceCommand()
                .addResourceFromClasspath("bpmn/converted-c8-process.bpmn")
                .addResourceFromClasspath("bpmn/start.form")
                .addResourceFromClasspath("bpmn/type-and-duration-vacation.form")
                .addResourceFromClasspath("bpmn/application-approve.form")
                .addResourceFromClasspath("bpmn/withdraw-vacation.form")
                .send()
                .join();
        assertThat(event);

        EmployeeDto employeeDto = getEmployeeDto();
        when(employeeService.getEmployeeByLogin(anyString())).thenReturn(employeeDto);

        VacationDto vacationDto = getVacationDto();
        when(iVacationService.setVacation(any())).thenReturn(vacationDto);

        PositionDto positionDto = getPositionDto();
        when(iOrgUnitService.getPosition(anyLong())).thenReturn(positionDto);
    }

    @Test
    void testDeployment() {

    }

    @Test
    void createProcessInstanceTest() {
        ProcessInstanceEvent processInstance = client.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_ID)
                .latestVersion()
                .send()
                .join();
        assertThat(processInstance).isStarted();
    }

    @Test
    void testHappyPath() throws InterruptedException, TimeoutException {
        ProcessInstanceEvent processInstance =
                startInstance(PROCESS_ID, Map.of());

        assertThat(processInstance)
                .isStarted();

        waitForProcessInstanceHasPassedElement(processInstance, "Search_All_Users");

        assertThat(processInstance)
                .isWaitingAtElements("Select_User");

        Map<String, Object> initiatorID = Map.of("initiatorID", "pprodaznyi");
        waitForUserTaskAndComplete("Select_User", initiatorID);

        waitForProcessInstanceHasPassedElement(processInstance, "Find_Initiator_Block");

        assertThat(processInstance)
                .hasPassedElement("Find_Initiator_Block");

        assertThat(processInstance)
                .isWaitingAtElements("Select_Vacation_Block");

        Map<String, Object> variables = Map.of(
                "vacation_start_date", "2024-01-01",
                "vacation_end_date", "2024-01-01",
                "vacationType", "ANNUALLY",
                "employee", getEmployeeDto()
        );
        waitForUserTaskAndComplete("Select_Vacation_Block", variables);

        waitForProcessInstanceHasPassedElement(processInstance, "Position_Maping_Block");

        assertThat(processInstance)
                .isWaitingAtElements("Management_block");

        Map<String, Object> vars = Map.of("isApprove", "true", "department", getPositionDto().getOrgUnit());
        waitForUserTaskAndComplete("Management_block", vars);

        assertThat(processInstance)
                .isWaitingAtElements("HR_Block");

        waitForUserTaskAndComplete("HR_Block", vars);

        waitForProcessInstanceHasPassedElement(processInstance, "Save_Vacation_Blank_Block");
        waitForProcessInstanceHasPassedElement(processInstance, "event_remind");

        assertThat(processInstance)
                .isWaitingAtElements("Cancel_Vacation_Block");

        Map<String, Object> isWithdrawVacation = Map.of("withdraw_vacation", "false");
        waitForUserTaskAndComplete("Cancel_Vacation_Block", isWithdrawVacation);

        waitForProcessInstanceHasPassedElement(processInstance, "Event_0xm4smn");

        waitForProcessInstanceCompleted(processInstance);
    }

    @Test
    void testWhenTradeNotApprovedRequest() {
        Map<String, Object> isApproved = Map.of("isApprove", "false");
        ProcessInstanceEvent processInstance = startInstanceBefore(PROCESS_ID, "Gateway_0zmk78v", isApproved);

        waitForProcessInstanceHasPassedElement(processInstance, "Event_06jrsiu");

        assertThat(processInstance).hasPassedElement("Event_06jrsiu");

        waitForProcessInstanceCompleted(processInstance);

        assertThat(processInstance).hasNotPassedElement("Event_0xm4smn");
    }

    @Test
    void testWhenVacationIsCanceled() throws InterruptedException, TimeoutException {
        ProcessInstanceEvent processInstance = startInstanceBefore(PROCESS_ID, "Cancel_Vacation_Block", Map.of(
                "vacation", VacationDto.builder().id(100500L).build()
        ));

        assertThat(processInstance).isWaitingAtElements("Cancel_Vacation_Block");

        Map<String, Object> withdrawVacation = Map.of("withdraw_vacation", "true");
        waitForUserTaskAndComplete("Cancel_Vacation_Block", withdrawVacation);

        waitForProcessInstanceHasPassedElement(processInstance, "Delete_Vacation_Black_Block");

        assertThat(processInstance).hasPassedElement("Event_0xm4smn");
    }

    private static EmployeeDto getEmployeeDto() {
        return EmployeeDto.builder()
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

    private static VacationDto getVacationDto() {
        return VacationDto.builder()
                .employee(123L)
                .startDate(LocalDate.of(2024, 6, 1))
                .duration(10)
                .type(VacationType.ANNUALLY)
                .build();
    }

    private ProcessInstanceEvent startInstance(String id, Map<String, Object> variables) {
        return client.newCreateInstanceCommand()
                .bpmnProcessId(id)
                .latestVersion()
                .variables(variables)
                .send()
                .join();
    }

    private ProcessInstanceEvent startInstanceBefore(String id, String beforeElementId, Map<String, Object> variables) {
        ProcessInstanceEvent processInstance = client.newCreateInstanceCommand()
                .bpmnProcessId(id)
                .latestVersion()
                .variables(variables)
                .startBeforeElement(beforeElementId)
                .send().join();
        BpmnAssert.assertThat(processInstance)
                .isStarted();
        return processInstance;
    }

    private void waitForUserTaskAndComplete(String userTaskId, Map<String, Object> variables)
            throws InterruptedException, TimeoutException {
        // Let the workflow engine do whatever it needs to do
        engine.waitForIdleState(Duration.ofSeconds(3));

        // Now get all user tasks
        List<ActivatedJob> jobs =
                client
                        .newActivateJobsCommand()
                        .jobType(USER_TASK_JOB_TYPE)
                        .maxJobsToActivate(1)
                        .workerName("waitForUserTaskAndComplete")
                        .send()
                        .join()
                        .getJobs();

        // Should be only one
        assertFalse(jobs.isEmpty(), "Job for user task '" + userTaskId + "' does not exist");
        ActivatedJob userTaskJob = jobs.get(0);
        // Make sure it is the right one
        if (userTaskId != null) {
            assertEquals(userTaskId, userTaskJob.getElementId());
        }

        // And complete it passing the variables
        if (variables != null && !variables.isEmpty()) {
            client.newCompleteCommand(userTaskJob.getKey()).variables(variables).send().join();
        } else {
            client.newCompleteCommand(userTaskJob.getKey()).send().join();
        }
    }

    private static PositionDto getPositionDto() {
        return PositionDto.builder()
                .orgUnit(1L)
                .build();
    }

}
