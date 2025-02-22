package ua.com.integrity.bpm.camunda.study.domain.materail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.RATES_SEQUENCE;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "consumption_rates")
@NamedQuery(name = "findAllConsumptionRate", query = "SELECT o FROM ConsumptionRate o")
@NamedQuery(name = "findAllRatesByMaterial", query = "SELECT o FROM ConsumptionRate o WHERE o.material.id = :materialId")
public class ConsumptionRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = RATES_SEQUENCE)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "duration_unit")
    @Enumerated(EnumType.STRING)
    private ChronoUnit durationUnit;

    @Column(name = "duration_amount")
    private Long durationAmount;

    @Column(name = "amount")
    private BigDecimal amount;
}
