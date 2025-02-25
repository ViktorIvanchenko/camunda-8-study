package ua.com.integrity.bpm.camunda.study.dao.equipment;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.equipment.SupplyRate;

import java.util.List;

public interface SupplyRateDao extends GenericDao<SupplyRate, Long> {

    <S> List<SupplyRate> findRatesForType(S typeId);
}
