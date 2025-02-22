package ua.com.integrity.bpm.camunda.study.domain.materail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.dto.materail.MeasureUnit;

import javax.persistence.*;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.MATERIALS_SEQUENCE;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "materials")
@SequenceGenerator(name = MATERIALS_SEQUENCE, sequenceName = "materials_seq", initialValue = 99000, allocationSize = 1)
@NamedQuery(name = "findAllMaterial", query = "SELECT o FROM Material o")
public class Material {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = MATERIALS_SEQUENCE)
    private Long id;

    private String title;

    @Column(name = "measure_unit")
    @Enumerated(EnumType.STRING)
    private MeasureUnit measureUnit;
}
