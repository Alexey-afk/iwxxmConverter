//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.06 at 11:11:25 PM MSK 
//


package schemabindings31.aero.aixm.schema._5_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NonMovementAreaTimeSlicePropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NonMovementAreaTimeSlicePropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.aixm.aero/schema/5.1.1}NonMovementAreaTimeSlice"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opengis.net/gml/3.2}OwnershipAttributeGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonMovementAreaTimeSlicePropertyType", propOrder = {
    "nonMovementAreaTimeSlice"
})
public class NonMovementAreaTimeSlicePropertyType {

    @XmlElement(name = "NonMovementAreaTimeSlice", required = true)
    protected NonMovementAreaTimeSliceType nonMovementAreaTimeSlice;
    @XmlAttribute(name = "owns")
    protected Boolean owns;

    /**
     * Gets the value of the nonMovementAreaTimeSlice property.
     * 
     * @return
     *     possible object is
     *     {@link NonMovementAreaTimeSliceType }
     *     
     */
    public NonMovementAreaTimeSliceType getNonMovementAreaTimeSlice() {
        return nonMovementAreaTimeSlice;
    }

    /**
     * Sets the value of the nonMovementAreaTimeSlice property.
     * 
     * @param value
     *     allowed object is
     *     {@link NonMovementAreaTimeSliceType }
     *     
     */
    public void setNonMovementAreaTimeSlice(NonMovementAreaTimeSliceType value) {
        this.nonMovementAreaTimeSlice = value;
    }

    public boolean isSetNonMovementAreaTimeSlice() {
        return (this.nonMovementAreaTimeSlice!= null);
    }

    /**
     * Gets the value of the owns property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isOwns() {
        if (owns == null) {
            return false;
        } else {
            return owns;
        }
    }

    /**
     * Sets the value of the owns property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOwns(boolean value) {
        this.owns = value;
    }

    public boolean isSetOwns() {
        return (this.owns!= null);
    }

    public void unsetOwns() {
        this.owns = null;
    }

}