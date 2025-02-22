package ua.com.integrity.bpm.camunda.study.domain.trip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.integrity.bpm.camunda.study.domain.DBConstants;
import ua.com.integrity.bpm.camunda.study.domain.Employee;
import ua.com.integrity.bpm.camunda.study.dto.trip.TripType;
import ua.com.integrity.bpm.camunda.study.dto.vacation.VacationType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trips")
@NamedQuery(name = "findAllTrips", query = "SELECT o FROM Trip o")
public class Trip {

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
    private TripType type;

    @Column(name = "departure_country")
    private String departureCountry;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "arrival_country")
    private String arrivalCountry;

    @Column(name = "arrival_city")
    private String arrivalCity;

    @Column(name = "subject")
    private String subject;

    @Column(name = "total_cost")
    private String totalCost;
}
