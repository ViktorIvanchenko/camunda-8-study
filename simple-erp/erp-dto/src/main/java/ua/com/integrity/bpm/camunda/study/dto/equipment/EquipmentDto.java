package ua.com.integrity.bpm.camunda.study.dto.equipment;

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
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "equipment")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentDto  implements Serializable {

    @NotBlank
    @XmlElement(name = "serial-number", required = true)
    @JsonProperty("serialNumber")
    private String serialNumber;

    @NotNull
    @XmlElement(required = true)
    private Long type;

    @NotNull
    @XmlElement(name = "purchase-price", required = true)
    @JsonProperty("purchasePrice")
    private BigDecimal purchasePrice;

    @XmlElement(name = "balance-price")
    @JsonProperty("balancePrice")
    private BigDecimal balancePrice;

    @XmlElement(name = "commissioning-date")
    @JsonProperty("commissioningDate")
    @XmlJavaTypeAdapter(JaxbLocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate commissioningDate;

    @XmlElement(name = "decommissioning-date")
    @JsonProperty("decommissioningDate")
    @XmlJavaTypeAdapter(JaxbLocalDateAdapter.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate decommissioningDate;

    private Long user;
}
