package ua.com.integrity.bpm.camunda.study.dto.vacation;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum VacationType {
    ANNUALLY,
    ADDITIONAL,
    @XmlEnumValue("AT-OWN-EXPENSE") AT_OWN_EXPENSE,
    @XmlEnumValue("FOR-FAMILY-REASONS") FOR_FAMILY_REASONS
}
