package ua.com.integrity.bpm.camunda.study.dto.trip;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.dto.utils.JaxbLocalDateAdapter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "trip")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripDto {
    @XmlAttribute
    private Long id;
    @NotNull
    @XmlElement(required = true)
    private Long employee;
    @NotNull
    @XmlElement(name = "start-date", required = true)
    @JsonProperty("startDate")
    @XmlJavaTypeAdapter(JaxbLocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @XmlElement(required = true)
    private Integer duration;
    @NotNull
    @XmlElement(required = true)
    private TripType type;
    private String departureCountry;
    @NotBlank
    @XmlElement(required = true)
    private String departureCity;
    private String arrivalCountry;
    @NotBlank
    @XmlElement(required = true)
    private String arrivalCity;
    @NotBlank
    @XmlElement(required = true)
    private String subject;
    @NotBlank
    @XmlElement(required = true)
    private String totalCost;
}
