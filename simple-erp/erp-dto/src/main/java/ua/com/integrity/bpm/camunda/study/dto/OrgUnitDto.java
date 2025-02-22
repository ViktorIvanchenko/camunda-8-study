package ua.com.integrity.bpm.camunda.study.dto;

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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "unit")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUnitDto implements Serializable {
    @XmlAttribute
    private Long id;

    @NotBlank
    @XmlElement(required = true)
    private String title;
    @XmlElement(name = "ascendant-unit")
    @JsonProperty("ascendant")
    private Long ascendant;

    @XmlElementWrapper(name = "descendant-units")
    @XmlElement(name = "unit")
    @JsonProperty("descendants")
    private List<Long> descendants;

    @XmlElementWrapper(name = "positions")
    @XmlElement(name = "position")
    @JsonProperty("positions")
    private List<Long> positions;
}
