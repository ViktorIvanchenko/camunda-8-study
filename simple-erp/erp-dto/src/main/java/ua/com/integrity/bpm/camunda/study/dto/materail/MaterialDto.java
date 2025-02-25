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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "material")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialDto implements Serializable {
    @XmlAttribute
    private Long id;
    @NotBlank
    @XmlElement(required = true)
    private String title;
    @NotNull
    @XmlElement(name = "measure-unit", required = true)
    @JsonProperty("measureUnit")
    private MeasureUnit measureUnit;
}
