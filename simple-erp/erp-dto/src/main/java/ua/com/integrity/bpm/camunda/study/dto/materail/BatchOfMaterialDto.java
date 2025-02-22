package ua.com.integrity.bpm.camunda.study.dto.materail;

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

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "batch-of-material")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchOfMaterialDto implements Serializable {
    @XmlAttribute
    private Long id;
    @NotNull
    @XmlElement(required = true)
    private MaterialDto material;
    @NotNull
    @XmlElement(name = "purchase-date", required = true)
    @JsonProperty("purchaseDate")
    @XmlJavaTypeAdapter(JaxbLocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @NotNull
    @XmlElement(name = "purchase-amount", required = true)
    @JsonProperty("purchaseAmount")
    private BigDecimal purchaseAmount;

    @NotNull
    @XmlElement(required = true)
    private BigDecimal price;

    @XmlElement(name = "remaining-amount")
    @JsonProperty("remainingAmount")
    private BigDecimal remainingAmount;
}
