package ua.com.integrity.bpm.camunda.study.dto.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "maintenance")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintenanceDto implements Serializable {
    @XmlAttribute
    private Long id;
    @NotBlank
    @XmlElement(required = true)
    private String title;
    @XmlElementWrapper(name = "consumption-rates")
    @XmlElement(name = "rate-id")
    @JsonProperty("consumptionRates")
    private Set<Long> consumptionRates;
}
