package ua.com.integrity.bpm.camunda.study.dto.materail;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MeasureUnit {
    METER,
    @XmlEnumValue("SQUARE-METER") SQUARE_METER,
    KILOGRAMME,
    LITER,
    PCS,
    PKG
}
