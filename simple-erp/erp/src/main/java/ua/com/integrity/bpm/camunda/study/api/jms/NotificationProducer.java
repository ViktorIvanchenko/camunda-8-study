package ua.com.integrity.bpm.camunda.study.api.jms;

import ua.com.integrity.bpm.camunda.study.dto.equipment.decommissioning.EquipmentDecommissioningNotificationDto;

public interface NotificationProducer {
    void addNotification(EquipmentDecommissioningNotificationDto notification);
}
