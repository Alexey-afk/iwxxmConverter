//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.18 at 02:04:43 PM MSK 
//


package net.opengis.gml.v_3_2_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndirectEntryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndirectEntryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}DefinitionProxy"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirectEntryType", propOrder = {
    "definitionProxy"
})
public class IndirectEntryType {

    @XmlElement(name = "DefinitionProxy", required = true)
    protected DefinitionProxyType definitionProxy;

    /**
     * Gets the value of the definitionProxy property.
     * 
     * @return
     *     possible object is
     *     {@link DefinitionProxyType }
     *     
     */
    public DefinitionProxyType getDefinitionProxy() {
        return definitionProxy;
    }

    /**
     * Sets the value of the definitionProxy property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinitionProxyType }
     *     
     */
    public void setDefinitionProxy(DefinitionProxyType value) {
        this.definitionProxy = value;
    }

    public boolean isSetDefinitionProxy() {
        return (this.definitionProxy!= null);
    }

}
