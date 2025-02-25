package ua.com.integrity.taskvacationimpl.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;
import ua.com.integrity.taskvacationimpl.dto.EmployeeDto;
import ua.com.integrity.taskvacationimpl.dto.vacation.VacationDto;
import ua.com.integrity.taskvacationimpl.dto.vacation.VacationType;
import ua.com.integrity.taskvacationimpl.integration.IVacationService;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Component
public class SaveRequest {

    private final IVacationService iVacationService;

    public SaveRequest(IVacationService iVacationService) {
        this.iVacationService = iVacationService;
    }

    @JobWorker(type = "saveRequest", fetchAllVariables = true)
    public Map<String, Serializable> execute(final ActivatedJob job,
                                             @Variable("vacation_start_date") Date startDate,
                                             @Variable("vacation_end_date") Date endDate,
                                             @Variable("employee") EmployeeDto employee,
                                             @Variable("vacationType") String vacationType
    ) throws ParseException {

        long duration = calcDuration(startDate, endDate);

        Date remandDate = getRemandDate(startDate);

        VacationDto vacation = VacationDto.builder()
                .employee(employee.getId())
                .type(VacationType.valueOf(vacationType))
                .startDate(dateToLocalDateTime(startDate).toLocalDate())
                .duration((int) duration)
                .build();

        VacationDto vacationDto = iVacationService.setVacation(vacation);

        return Map.of("vacation", vacationDto, "reminder_date", remandDate);
    }

    private Date getRemandDate(Date startDate) throws ParseException {

        LocalDateTime localStartDate = dateToLocalDateTime(startDate);
        LocalDateTime localReminderDate = localStartDate.minusDays(1).plusHours(10);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        return formatter.parse(localReminderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    }

    private long calcDuration(Date startDate, Date endDate) {
        LocalDateTime localStartDate = dateToLocalDateTime(startDate);
        LocalDateTime localEndDate = dateToLocalDateTime(endDate);
        return localStartDate.until(localEndDate, ChronoUnit.HOURS);
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

}
