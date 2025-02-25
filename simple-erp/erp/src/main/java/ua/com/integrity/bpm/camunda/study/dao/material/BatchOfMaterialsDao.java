package ua.com.integrity.bpm.camunda.study.dao.material;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.materail.BatchOfMaterial;

import java.util.List;

public interface BatchOfMaterialsDao extends GenericDao<BatchOfMaterial, Long> {

    <S> List<BatchOfMaterial> findAllForMaterial(S materialId);
}
