package ua.com.integrity.bpm.camunda.study.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;

import static ua.com.integrity.bpm.camunda.study.domain.DBConstants.ORG_STRUCTURE_SEQUENCE;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee")
@NamedQuery(name = "findAllEmployee", query = "SELECT o FROM Employee o")
@NamedQuery(name = "findEmployeeByLogin", query = "SELECT o FROM Employee o WHERE o.login = :login")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ORG_STRUCTURE_SEQUENCE)
    private Long id;

    private String login;

    @Column(name = "f_name")
    private String firstName;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "fire_date")
    private LocalDate fireDate;

    @OneToOne
    @JoinColumn(name = "position_id")
    private Position position;

}
