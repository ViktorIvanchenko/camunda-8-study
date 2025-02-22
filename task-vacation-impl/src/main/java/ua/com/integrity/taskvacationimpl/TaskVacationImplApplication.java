package ua.com.integrity.taskvacationimpl;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@Deployment(resources = "classpath*:/bpmn/*")
@EnableFeignClients
public class TaskVacationImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskVacationImplApplication.class, args);
    }

}
