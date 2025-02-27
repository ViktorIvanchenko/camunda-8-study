package ua.com.integrity;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.command.FinalCommandStep;
import io.camunda.zeebe.client.api.response.ActivateJobsResponse;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.process.test.extension.ZeebeProcessTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.*;

@ZeebeProcessTest
public class ProcessCompensationTest {
    public ZeebeClient zeebeClient;
    public ZeebeTestEngine engine;

    static boolean deployed;

    @BeforeEach
    void deploy() {
        if (!deployed) {
            zeebeClient.newDeployResourceCommand()
                    .addResourceFromClasspath("bpmn/process-compensation-test.bpmn")
                    .addResourceFromClasspath("bpmn/process-compensation-test-with-workers.bpmn")
                    .send().join();
        }
    }

    @SneakyThrows
    @Test
    void testProcessExecutionFlagIsTrue() {
        ProcessInstanceEvent processInstance = startProcess(
                "process-compensation-test",
                true
        );

        engine.waitForIdleState(Duration.ofSeconds(3));
        assertThat(processInstance)
                .isStarted()
                .hasPassedElementsInOrder(
                        "se1",
                        "a1",
                        "a2",
                        "g1",
                        "a3",
                        "cb1",
                        "cb2",
                        "ca1",
                        "ca2",
                        "ce1",
                        "a4",
                        "ee1"
                )
                .hasNotPassedElement("a5")
                .hasNotPassedElement("cb4")
                .isCompleted();
    }

    @SneakyThrows
    @Test
    void testProcessExecutionFlagIsFalse() {
        ProcessInstanceEvent processInstance = startProcess(
                "process-compensation-test",
                false
        );

        engine.waitForIdleState(Duration.ofSeconds(3));
        assertThat(processInstance)
                .isStarted()
                .hasPassedElementsInOrder(
                        "se1",
                        "a1",
                        "a2",
                        "g1",
                        "a5",
                        "a6",
                        "ee2"
                )
                .hasNotPassedElement("a3")
                .isCompleted();
    }

    @SneakyThrows
    @Test
    void testProcessExecutionWithWorkersWhenFlagIsTrue() {
        ProcessInstanceEvent processInstance = startProcess(
                "process-compensation-test-with-workers",
                true
        );

        engine.waitForIdleState(Duration.ofSeconds(3));
        assertThat(processInstance)
                .isStarted()
                .hasPassedElementsInOrder(
                        "se1",
                        "a1",
                        "a2",
                        "g1",
                        "a3",
                        "cb1",
                        "cb2"
                ).isWaitingAtElements(
                        "ca1",
                        "ca2",
                "ce1"
                );
        completeJob("ca2-job");
        engine.waitForIdleState(Duration.ofSeconds(1));
        completeJob("ca1-job");
        engine.waitForIdleState(Duration.ofSeconds(1));
        assertThat(processInstance)
                .hasPassedElementsInOrder(
                        "ca2",
                        "ca1",
                        "ce1",
                        "a4",
                        "ee1"
                );
    }

    private ProcessInstanceEvent startProcess(String bpmnProcessId, boolean flag) {
        return zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .variable("flag", flag)
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
