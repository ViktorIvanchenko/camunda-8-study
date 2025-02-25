package ua.com.integrity.bpm.camunda.study.domain.equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.domain.Employee;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "equipment")
@NamedQuery(name = "findAllEquipment", query = "SELECT o FROM Equipment o")
@NamedQuery(name = "findAllNonDecommissioned", query = "SELECT o FROM Equipment o WHERE o.decommissioningDate IS NULL")
@NamedQuery(name = "findActiveEquipmentByType", query = "SELECT o FROM Equipment o WHERE o.type.id = :typeId AND o.decommissioningDate IS NULL")
@NamedQuery(name = "findEquipmentByTypeWithDecommissioned", query = "SELECT o FROM Equipment o WHERE o.type.id = :typeId")
public class Equipment {

    @Id
    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "type")
    private EquipmentType type;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "balance_price")
    private BigDecimal balancePrice;

    @Column(name = "commissioning_date")
    private LocalDate commissioningDate;

    @Column(name = "decommissioning_date")
    private LocalDate decommissioningDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee user;
}
