package ua.com.integrity.bpm.camunda.study.api.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ua.com.integrity.bpm.camunda.study.dto.equipment.decommissioning.EquipmentDecommissioningNotificationDto;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jms.*;

public class NotificationProducerImpl implements NotificationProducer {

    @Resource(lookup = "java:jboss/activemq/equipment-decommissioning-notifications")
    private Destination destination;

    @Inject
    @JMSConnectionFactory("java:jboss/activemq/xa-cf")
    private JMSContext jmsContext;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void addNotification(EquipmentDecommissioningNotificationDto notification) {
        String body = objectMapper.writeValueAsString(notification);
        JMSProducer producer = jmsContext.createProducer();
        producer.send(destination, body);
    }
}
