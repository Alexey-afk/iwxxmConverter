//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.27 at 12:33:13 PM MSK 
//


package _int.wmo.def.opm._2013;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatisticalQualifierPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatisticalQualifierPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://def.wmo.int/opm/2013}StatisticalQualifier"/>
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
@XmlType(name = "StatisticalQualifierPropertyType", propOrder = {
    "statisticalQualifier"
})
public class StatisticalQualifierPropertyType {

    @XmlElement(name = "StatisticalQualifier", required = true)
    protected StatisticalQualifierType statisticalQualifier;
    @XmlAttribute(name = "owns")
    protected Boolean owns;

    /**
     * Gets the value of the statisticalQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link StatisticalQualifierType }
     *     
     */
    public StatisticalQualifierType getStatisticalQualifier() {
        return statisticalQualifier;
    }

    /**
     * Sets the value of the statisticalQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatisticalQualifierType }
     *     
     */
    public void setStatisticalQualifier(StatisticalQualifierType value) {
        this.statisticalQualifier = value;
    }

    public boolean isSetStatisticalQualifier() {
        return (this.statisticalQualifier!= null);
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
