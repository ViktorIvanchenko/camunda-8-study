package ua.com.integrity.taskvacationimpl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUnitDto implements Serializable {

    private Long id;

    @NotBlank
    private String title;
    @JsonProperty("ascendant")
    private Long ascendant;

    @JsonProperty("descendants")
    private List<Long> descendants;

    @JsonProperty("positions")
    private List<Long> positions;
}
