package ua.com.integrity.bpm.camunda.study.domain.equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.RATES_SEQUENCE;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "supply_rates")
@SequenceGenerator(name = RATES_SEQUENCE, sequenceName = "rates_seq", initialValue = 22000, allocationSize = 1)
@NamedQuery(name = "findAllSupplyRate", query = "SELECT o FROM SupplyRate o")
@NamedQuery(name = "findAllSupplyRateForType", query = "SELECT o FROM SupplyRate o WHERE o.equipmentType.id = :typeId")
public class SupplyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = RATES_SEQUENCE)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "equipment_type_id")
    private EquipmentType equipmentType;

    private Integer amount;
}
