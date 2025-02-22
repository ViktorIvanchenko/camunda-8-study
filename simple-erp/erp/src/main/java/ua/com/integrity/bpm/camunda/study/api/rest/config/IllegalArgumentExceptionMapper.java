package ua.com.integrity.bpm.camunda.study.api.rest.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import ua.com.integrity.bpm.camunda.study.dto.api.ApiErrorResponse;

import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        ApiErrorResponse response = new ApiErrorResponse(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }
}
