package ua.com.integrity.bpm.camunda.study.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.domain.equipment.SupplyRate;

import javax.persistence.*;

import java.util.List;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.ORG_STRUCTURE_SEQUENCE;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "org_position")
@NamedQuery(name = "findAllPosition", query = "SELECT o FROM Position o")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ORG_STRUCTURE_SEQUENCE)
    private Long id;

    private String title;
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "org_unit_id")
    private OrgUnit orgUnit;

    @OneToOne(mappedBy = "position")
    private Employee employee;

    @ManyToMany
    @JoinTable(
            name = "positions_supply_rates",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "sup_rate_id")
    )
    private List<SupplyRate> supplyRates;
}
