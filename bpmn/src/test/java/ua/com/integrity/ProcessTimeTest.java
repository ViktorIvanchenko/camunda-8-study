package ua.com.integrity;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivateJobsResponse;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.extension.ZeebeProcessTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;

@ZeebeProcessTest
public class ProcessTimeTest {
    public ZeebeClient zeebeClient;
    public ZeebeTestEngine engine;

    static boolean deployed;

    @BeforeEach
    void deploy() {
        if (!deployed) {
            zeebeClient.newDeployResourceCommand()
                    .addResourceFromClasspath("bpmn/process-timer-test.bpmn")
                    .send().join();
        }
    }

    @SneakyThrows
//    @Test
    @RepeatedTest(10)
    void testHappyPath() {
        var processInstance = startProcess("process-timer-test");
        engine.waitForIdleState(Duration.ofSeconds(1));

        assertThat(processInstance)
                .isStarted()
                .hasPassedElement("se")
                .isWaitingAtElements("te");

        engine.increaseTime(Duration.ofMinutes(6));
        engine.waitForBusyState(Duration.ofSeconds(3));
        engine.waitForIdleState(Duration.ofSeconds(3));

        assertThat(processInstance)
                .hasPassedElementsInOrder(
                        "te",
                        "a1",
                        "ee1"
                ).isCompleted();
    }

    private ProcessInstanceEvent startProcess(String bpmnProcessId) {
        return zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .send().join();
    }

    private void completeJob(String jobType) {
        ActivateJobsResponse response = zeebeClient.newActivateJobsCommand()
                .jobType(jobType)
                .maxJobsToActivate(1)
                .timeout(Duration.ofSeconds(1))
                .send()
                .join();
        for (ActivatedJob job : response.getJobs()) {
            zeebeClient.newCompleteCommand(job.getKey())
                    .send().join();
        }
    }

}
