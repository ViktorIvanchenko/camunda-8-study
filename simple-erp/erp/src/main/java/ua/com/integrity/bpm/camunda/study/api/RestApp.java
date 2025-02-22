package ua.com.integrity.bpm.camunda.study.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/rest")
@OpenAPIDefinition(
        info = @Info(
                version = "1.0",
                title = "REST API for study ERP system",
                contact = @Contact(
                        name = "Integrity Vision LLC",
                        url = "https://integrity.com.ua",
                        email = "vivanchenko@integrity.com.ua"
                )
        )
)
public class RestApp extends Application {


}
