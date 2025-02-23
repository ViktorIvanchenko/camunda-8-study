package ua.com.integrity.bpm.camunda.study.api.rest.config;

import ua.com.integrity.bpm.camunda.study.dto.api.ApiInvalidRequestValueResponse;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ApiInvalidRequestValueResponse> response = exception.getConstraintViolations().stream()
                .map(cv -> {
                    String value = null;
                    Object invalidValue = cv.getInvalidValue();
                    if (invalidValue instanceof String) {
                        value = (String) invalidValue;
                    }
                    return new ApiInvalidRequestValueResponse(cv.getMessage(), value);
                }).collect(Collectors.toList());
        GenericEntity<List<ApiInvalidRequestValueResponse>> entity = new GenericEntity<List<ApiInvalidRequestValueResponse>>(response){};
        return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
    }
}
