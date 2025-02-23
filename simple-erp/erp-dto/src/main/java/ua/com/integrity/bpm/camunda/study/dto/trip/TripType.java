package ua.com.integrity.bpm.camunda.study.dto.trip;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum TripType {
    INTERNAL,
    FOREIGN
}
