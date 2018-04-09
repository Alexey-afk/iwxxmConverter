//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.27 at 12:41:52 PM MSK 
//


package net.opengis.om._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.opengis.gml.v_3_2_1.ReferenceType;


/**
 *  The class 'NamedValue' provides for a generic soft-typed parameter
 * 				value. NamedValue shall support two attributes. 
 * 
 * <p>Java class for NamedValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NamedValueType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.opengis.net/gml/3.2}ReferenceType"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamedValueType", propOrder = {
    "name",
    "value"
})
public class NamedValueType {

    @XmlElement(required = true)
    protected ReferenceType name;
    @XmlElement(required = true)
    protected Object value;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setName(ReferenceType value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isSetValue() {
        return (this.value!= null);
    }

}
