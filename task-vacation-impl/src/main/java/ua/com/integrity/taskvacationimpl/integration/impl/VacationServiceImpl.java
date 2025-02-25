package ua.com.integrity.taskvacationimpl.integration.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import ua.com.integrity.taskvacationimpl.dto.vacation.VacationDto;
import ua.com.integrity.taskvacationimpl.integration.ErpApiClient;
import ua.com.integrity.taskvacationimpl.integration.IVacationService;


@Service
public class VacationServiceImpl implements IVacationService {

    private final ErpApiClient erpApiClient;

    public VacationServiceImpl(ErpApiClient erpApiClient) {
        this.erpApiClient = erpApiClient;
    }

    @Override
    public VacationDto setVacation(VacationDto vacation) {
        ResponseEntity<VacationDto> responseEntity = erpApiClient.apiRestOrgStructureVacationPostWithHttpInfo(vacation);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new IllegalArgumentException("Failed to set vacation. Status code: " + responseEntity.getStatusCode());
        }
    }

    @Override
    public void deleteVacation(VacationDto vacation) {
        ResponseEntity<Void> responseEntity = erpApiClient.apiRestOrgStructureVacationIdDeleteWithHttpInfo(
                vacation.getId());

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new IllegalArgumentException("Failed to delete vacation. Status code: " + responseEntity.getStatusCode());
        }
    }
}
