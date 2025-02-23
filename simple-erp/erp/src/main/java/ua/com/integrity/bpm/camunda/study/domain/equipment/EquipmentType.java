package ua.com.integrity.bpm.camunda.study.domain.equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.domain.materail.ConsumptionRate;

import jakarta.persistence.*;

import java.util.Set;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.EQUIPMENT_TYPES_SEQUENCE;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "equipment_type")
@SequenceGenerator(name = EQUIPMENT_TYPES_SEQUENCE, sequenceName = "equipment_types_seq", initialValue = 33000, allocationSize = 1)
@NamedQuery(name = "findAllEquipmentType", query = "SELECT o FROM EquipmentType o")
public class EquipmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = EQUIPMENT_TYPES_SEQUENCE)
    private Long id;
    private String title;
    private String description;
    @Column(name = "life_time")
    private Integer lifeTimeInMonth;

    @ManyToMany
    @JoinTable(
            name = "equipment_maintenances",
            joinColumns = @JoinColumn(name = "equipment_type"),
            inverseJoinColumns = @JoinColumn(name = "maintenance_type")
    )
    private Set<Maintenance> maintenanceSet;

    @ManyToMany
    @JoinTable(
            name = "equipment_consumption_rates",
            joinColumns = @JoinColumn(name = "equipment_type"),
            inverseJoinColumns = @JoinColumn(name = "consumption_rate")
    )
    private Set<ConsumptionRate> consumptionRates;
}
