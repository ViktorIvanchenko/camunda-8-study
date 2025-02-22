package ua.com.integrity.bpm.camunda.study.dto.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "supply-rate")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplyRateDto implements Serializable {
    @XmlAttribute
    private Long id;
    @NotBlank
    @XmlElement(required = true)
    private String title;
    @NotNull
    @XmlElement(name = "equipment-type", required = true)
    @JsonProperty("equipmentType")
    private Long equipmentType;
    @NotNull
    @XmlElement(required = true)
    private Integer amount;
}
