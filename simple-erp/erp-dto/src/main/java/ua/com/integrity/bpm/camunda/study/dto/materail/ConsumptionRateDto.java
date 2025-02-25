package ua.com.integrity.bpm.camunda.study.dto.materail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "consumption-rate")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumptionRateDto implements Serializable {
    @XmlAttribute
    private Long id;
    @NotBlank
    @XmlElement(required = true)
    private String title;
    @NotNull
    @XmlElement(required = true)
    private MaterialDto material;
    @XmlElement(name = "duration-unit")
    @JsonProperty("durationUnit")
    private ChronoUnit durationUnit;
    @XmlElement(name = "duration-amount")
    @JsonProperty("durationAmount")
    private Long durationAmount;
    @XmlElement
    private BigDecimal amount;
}
