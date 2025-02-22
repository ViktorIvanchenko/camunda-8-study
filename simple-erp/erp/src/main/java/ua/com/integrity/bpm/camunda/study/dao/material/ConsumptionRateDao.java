package ua.com.integrity.bpm.camunda.study.dao.material;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.materail.ConsumptionRate;

import java.util.List;

public interface ConsumptionRateDao extends GenericDao<ConsumptionRate, Long> {

    <S> List<ConsumptionRate> findAllForMaterial(S materialId);
}
