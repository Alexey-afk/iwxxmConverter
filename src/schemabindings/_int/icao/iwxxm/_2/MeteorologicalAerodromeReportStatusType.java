//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.27 at 12:41:52 PM MSK 
//


package _int.icao.iwxxm._2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MeteorologicalAerodromeReportStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MeteorologicalAerodromeReportStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NORMAL"/>
 *     &lt;enumeration value="CORRECTION"/>
 *     &lt;enumeration value="MISSING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MeteorologicalAerodromeReportStatusType")
@XmlEnum
public enum MeteorologicalAerodromeReportStatusType {


    /**
     * Normal report status: not a correction of an earlier report
     * 
     */
    NORMAL,

    /**
     * A correction of an earlier report
     * 
     */
    CORRECTION,

    /**
     * The report is missing ("NIL" from ICAO Annex 3 / WMO No. 49)
     * 
     */
    MISSING;

    public String value() {
        return name();
    }

    public static MeteorologicalAerodromeReportStatusType fromValue(String v) {
        return valueOf(v);
    }

}
