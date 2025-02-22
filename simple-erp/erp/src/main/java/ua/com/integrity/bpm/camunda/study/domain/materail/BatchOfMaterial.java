package ua.com.integrity.bpm.camunda.study.domain.materail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.MATERIAL_BATCHES_SEQUENCE;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "material_batches")
@SequenceGenerator(name = MATERIAL_BATCHES_SEQUENCE, sequenceName = "material_batches_seq", initialValue = 166000, allocationSize = 1)
@NamedQuery(name = "findAllBatchOfMaterial", query = "SELECT o FROM BatchOfMaterial o")
@NamedQuery(name = "findAllBatchesForMaterial", query = "SELECT o FROM BatchOfMaterial o WHERE o.material.id = :materialId")
public class BatchOfMaterial {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = MATERIAL_BATCHES_SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_amount")
    private BigDecimal purchaseAmount;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "remaining_amount")
    private BigDecimal remainingAmount;
}
