package ua.com.integrity.taskvacationimpl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionDto  implements Serializable {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private Integer index;

    @NotNull
    @JsonProperty("orgUnit")
    private Long orgUnit;

    private Long employee;

    @JsonProperty("supplyRates")
    private List<Long> supplyRates;
}
