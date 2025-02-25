package ua.com.integrity.bpm.camunda.study.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.dto.utils.JaxbLocalDateAdapter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto implements Serializable {

    @XmlAttribute
    private Long id;

    @NotBlank
    private String login;

    @NotBlank
    @XmlElement(name = "first-name", required = true)
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank
    @XmlElement(name = "last-name", required = true)
    @JsonProperty("lastName")
    private String lastName;

    @NotNull
    @XmlElement(name = "birth-date", required = true)
    @JsonProperty("birthDate")
    @XmlJavaTypeAdapter(JaxbLocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull
    @XmlElement(name = "hire-date", required = true)
    @JsonProperty("hireDate")
    @XmlJavaTypeAdapter(JaxbLocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @XmlElement(name = "fire-date")
    @JsonProperty("fireDate")
    @XmlJavaTypeAdapter(JaxbLocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fireDate;

    private Long position;

}
