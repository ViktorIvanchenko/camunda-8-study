package ua.com.integrity.bpm.camunda.study.service.decomission;

import ua.com.integrity.bpm.camunda.study.api.jms.NotificationProducer;
import ua.com.integrity.bpm.camunda.study.dao.equipment.EquipmentDao;
import ua.com.integrity.bpm.camunda.study.dto.equipment.decommissioning.EquipmentDecommissioningNotificationDto;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class EquipmentDecommissionNotificationService {

    @Inject
    private EquipmentDao equipmentDao;
    @Inject
    private NotificationProducer notificationProducer;

    @Schedule(hour = "2")
    public void createDecommissioningNotification() {
        List<String> equipmentList = equipmentDao.findEquipmentForDecommissioning();
        for (String serialNumber : equipmentList) {
            EquipmentDecommissioningNotificationDto notification = new EquipmentDecommissioningNotificationDto(serialNumber);
            notificationProducer.addNotification(notification);
        }
    }
}
