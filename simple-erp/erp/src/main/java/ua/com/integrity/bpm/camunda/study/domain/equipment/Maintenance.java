package ua.com.integrity.bpm.camunda.study.domain.equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.domain.materail.ConsumptionRate;

import javax.persistence.*;

import java.util.Set;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.MAINTENANCE_SEQUENCE;


@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "maintenance")
@SequenceGenerator(name = MAINTENANCE_SEQUENCE, sequenceName = "maintenance_seq", initialValue = 116000, allocationSize = 1)
@NamedQuery(name = "findAllMaintenance", query = "SELECT o FROM Maintenance o")
public class Maintenance {

    @Id @GeneratedValue(generator = MAINTENANCE_SEQUENCE, strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "maintenance_consumption_rate",
            joinColumns = @JoinColumn(name = "maintenance_type"),
            inverseJoinColumns = @JoinColumn(name = "consumption_rate")
    )
    private Set<ConsumptionRate> consumptionRates;
}
