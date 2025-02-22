package ua.com.integrity.taskvacationimpl.integration;


import ua.com.integrity.taskvacationimpl.dto.vacation.VacationDto;

public interface IVacationService {
    VacationDto setVacation(VacationDto vacation);
    void deleteVacation(VacationDto vacation);
}
