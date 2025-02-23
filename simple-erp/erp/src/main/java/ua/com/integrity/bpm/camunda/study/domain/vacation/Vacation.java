package ua.com.integrity.bpm.camunda.study.domain.vacation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.domain.DBConstants;
import ua.com.integrity.bpm.camunda.study.domain.Employee;
import ua.com.integrity.bpm.camunda.study.dto.vacation.VacationType;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vacations")
@NamedQuery(name = "findAllVacation", query = "SELECT o FROM Vacation o")
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DBConstants.ORG_STRUCTURE_SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "start_date")
    private LocalDate startDate;

    private Integer duration;

    @Enumerated(EnumType.STRING)
    private VacationType type;
}
