package ua.com.integrity.bpm.camunda.study.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiInvalidRequestValueResponse {
    private String message;
    private String invalidValue;
}
