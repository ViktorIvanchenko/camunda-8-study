package ua.com.integrity.bpm.camunda.study.dto.materail;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MeasureUnit {
    METER,
    @XmlEnumValue("SQUARE-METER") SQUARE_METER,
    KILOGRAMME,
    LITER,
    PCS,
    PKG
}
