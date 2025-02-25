package ua.com.integrity.bpm.camunda.study.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@XmlRootElement(name = "equipment-assign-request")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentAssigningRequest {
    @NotBlank
    @XmlElement(name = "serial-number", required = true)
    @JsonProperty("serialNumber")
    private String serialNumber;
    @NotNull
    @XmlElement(name = "employee-id", required = true)
    @JsonProperty("employeeId")
    private Long employeeId;
}
