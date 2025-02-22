package ua.com.integrity.taskvacationimpl.integration;

import ua.com.integrity.taskvacationimpl.dto.PositionDto;

public interface IOrgUnitService {
    PositionDto getPosition(long position);
}
