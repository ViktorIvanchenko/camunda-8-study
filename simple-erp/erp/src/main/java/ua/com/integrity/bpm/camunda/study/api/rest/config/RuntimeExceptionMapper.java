package ua.com.integrity.bpm.camunda.study.api.rest.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import ua.com.integrity.bpm.camunda.study.dto.api.ApiErrorResponse;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = exception.toString();
        }
        ApiErrorResponse response = new ApiErrorResponse(message);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
    }
}
