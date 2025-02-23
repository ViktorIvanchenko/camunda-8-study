package ua.com.integrity.bpm.camunda.study.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.*;

@Entity
@Table(name = "org_unit")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@SequenceGenerator(name = ORG_STRUCTURE_SEQUENCE, sequenceName = "org_structure_seq", allocationSize = 1, initialValue = 5000)
@NamedQuery(name = "findAllOrgUnit", query = "SELECT o FROM OrgUnit o")
public class OrgUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ORG_STRUCTURE_SEQUENCE)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ascendant")
    private OrgUnit ascendant;

    @OneToMany(mappedBy = "ascendant", fetch = FetchType.LAZY)
    private List<OrgUnit> descendants;

    @OneToMany(mappedBy = "orgUnit", fetch = FetchType.LAZY)
    private List<Position> positions;
}
