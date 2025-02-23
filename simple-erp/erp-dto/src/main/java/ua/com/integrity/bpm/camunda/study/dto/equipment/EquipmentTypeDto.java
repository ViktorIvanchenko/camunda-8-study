package ua.com.integrity.bpm.camunda.study.dto.equipment;

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
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "equipment-type")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentTypeDto  implements Serializable {
    @XmlAttribute
    private Long id;

    @NotBlank
    @XmlElement(required = true)
    private String title;

    private String description;

    @NotNull
    @XmlElement(name = "life-time", required = true)
    @JsonProperty("lifeTimeInMonth")
    private Integer lifeTimeInMonth;

    @XmlElementWrapper(name = "maintenance-set")
    @XmlElement(name = "maintenance-id")
    @JsonProperty("maintenanceSet")
    private Set<Long> maintenanceSet;

    @XmlElementWrapper(name = "consumption-rates")
    @XmlElement(name = "rate-id")
    @JsonProperty("consumptionRates")
    private Set<Long> consumptionRates;
}
