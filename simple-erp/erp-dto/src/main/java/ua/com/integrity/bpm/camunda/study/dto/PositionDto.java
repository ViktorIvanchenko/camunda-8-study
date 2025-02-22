package ua.com.integrity.bpm.camunda.study.dto;

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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "position")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionDto  implements Serializable {

    @XmlAttribute
    private Long id;

    @NotBlank
    @XmlElement(required = true)
    private String title;

    @NotNull
    @XmlElement(required = true)
    private Integer index;

    @NotNull
    @XmlElement(name = "unit", required = true)
    @JsonProperty("orgUnit")
    private Long orgUnit;

    private Long employee;

    @XmlElementWrapper(name = "supply-rates")
    @XmlElement(name = "rate")
    @JsonProperty("supplyRates")
    private List<Long> supplyRates;
}
