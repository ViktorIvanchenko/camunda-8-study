package ua.com.integrity.bpm.camunda.study.dao.equipment;

import ua.com.integrity.bpm.camunda.study.dao.GenericDao;
import ua.com.integrity.bpm.camunda.study.domain.equipment.Equipment;

import java.util.List;

public interface EquipmentDao extends GenericDao<Equipment, String> {

    <S> List<Equipment> findEquipmentByType(S typeId, Boolean withDecommissioned);

    List<Equipment> findAllNonDecommissionedEquipment();

    List<String> findEquipmentForDecommissioning();
}
