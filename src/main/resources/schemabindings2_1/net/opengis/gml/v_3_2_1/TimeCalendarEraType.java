//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.27 at 12:41:52 PM MSK 
//


package net.opengis.gml.v_3_2_1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeCalendarEraType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeCalendarEraType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}DefinitionType">
 *       &lt;sequence>
 *         &lt;element name="referenceEvent" type="{http://www.opengis.net/gml/3.2}StringOrRefType"/>
 *         &lt;element name="referenceDate" type="{http://www.opengis.net/gml/3.2}CalDate"/>
 *         &lt;element name="julianReference" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="epochOfUse" type="{http://www.opengis.net/gml/3.2}TimePeriodPropertyType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeCalendarEraType", propOrder = {
    "referenceEvent",
    "referenceDate",
    "julianReference",
    "epochOfUse"
})
public class TimeCalendarEraType
    extends DefinitionType
{

    @XmlElement(required = true)
    protected StringOrRefType referenceEvent;
    @XmlElement(required = true)
    protected String referenceDate;
    @XmlElement(required = true)
    protected BigDecimal julianReference;
    @XmlElement(required = true)
    protected TimePeriodPropertyType epochOfUse;

    /**
     * Gets the value of the referenceEvent property.
     * 
     * @return
     *     possible object is
     *     {@link StringOrRefType }
     *     
     */
    public StringOrRefType getReferenceEvent() {
        return referenceEvent;
    }

    /**
     * Sets the value of the referenceEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringOrRefType }
     *     
     */
    public void setReferenceEvent(StringOrRefType value) {
        this.referenceEvent = value;
    }

    public boolean isSetReferenceEvent() {
        return (this.referenceEvent!= null);
    }

    /**
     * Gets the value of the referenceDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceDate() {
        return referenceDate;
    }

    /**
     * Sets the value of the referenceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceDate(String value) {
        this.referenceDate = value;
    }

    public boolean isSetReferenceDate() {
        return (this.referenceDate!= null);
    }

    /**
     * Gets the value of the julianReference property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getJulianReference() {
        return julianReference;
    }

    /**
     * Sets the value of the julianReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setJulianReference(BigDecimal value) {
        this.julianReference = value;
    }

    public boolean isSetJulianReference() {
        return (this.julianReference!= null);
    }

    /**
     * Gets the value of the epochOfUse property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriodPropertyType }
     *     
     */
    public TimePeriodPropertyType getEpochOfUse() {
        return epochOfUse;
    }

    /**
     * Sets the value of the epochOfUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriodPropertyType }
     *     
     */
    public void setEpochOfUse(TimePeriodPropertyType value) {
        this.epochOfUse = value;
    }

    public boolean isSetEpochOfUse() {
        return (this.epochOfUse!= null);
    }

}
