package ua.com.integrity.taskvacationimpl.integration.impl;

import org.springframework.stereotype.Service;
import ua.com.integrity.taskvacationimpl.dto.PositionDto;
import ua.com.integrity.taskvacationimpl.integration.ErpApiClient;
import ua.com.integrity.taskvacationimpl.integration.IOrgUnitService;

@Service
public class OrgUnitServiceImpl implements IOrgUnitService {

    private final ErpApiClient erpApiClient;

    public OrgUnitServiceImpl(ErpApiClient erpApiClient) {
        this.erpApiClient = erpApiClient;
    }


    @Override
    public PositionDto getPosition(long position) {
        return erpApiClient.apiRestOrgStructurePositionIdGet(position);
    }
}
