//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.27 at 12:41:52 PM MSK 
//


package net.opengis.gml.v_3_2_1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * gml:GeodeticCRS is a coordinate reference system based on a geodetic datum.
 * 
 * <p>Java class for GeodeticCRSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeodeticCRSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}AbstractCRSType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{http://www.opengis.net/gml/3.2}ellipsoidalCS"/>
 *           &lt;element ref="{http://www.opengis.net/gml/3.2}cartesianCS"/>
 *           &lt;element ref="{http://www.opengis.net/gml/3.2}sphericalCS"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}geodeticDatum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeodeticCRSType", propOrder = {
    "ellipsoidalCS",
    "cartesianCS",
    "sphericalCS",
    "geodeticDatum"
})
public class GeodeticCRSType
    extends AbstractCRSType
{

    @XmlElementRef(name = "ellipsoidalCS", namespace = "http://www.opengis.net/gml/3.2", type = JAXBElement.class, required = false)
    protected JAXBElement<EllipsoidalCSPropertyType> ellipsoidalCS;
    @XmlElementRef(name = "cartesianCS", namespace = "http://www.opengis.net/gml/3.2", type = JAXBElement.class, required = false)
    protected JAXBElement<CartesianCSPropertyType> cartesianCS;
    @XmlElementRef(name = "sphericalCS", namespace = "http://www.opengis.net/gml/3.2", type = JAXBElement.class, required = false)
    protected JAXBElement<SphericalCSPropertyType> sphericalCS;
    @XmlElementRef(name = "geodeticDatum", namespace = "http://www.opengis.net/gml/3.2", type = JAXBElement.class)
    protected JAXBElement<GeodeticDatumPropertyType> geodeticDatum;

    /**
     * Gets the value of the ellipsoidalCS property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EllipsoidalCSPropertyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link EllipsoidalCSPropertyType }{@code >}
     *     
     */
    public JAXBElement<EllipsoidalCSPropertyType> getEllipsoidalCS() {
        return ellipsoidalCS;
    }

    /**
     * Sets the value of the ellipsoidalCS property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EllipsoidalCSPropertyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link EllipsoidalCSPropertyType }{@code >}
     *     
     */
    public void setEllipsoidalCS(JAXBElement<EllipsoidalCSPropertyType> value) {
        this.ellipsoidalCS = value;
    }

    public boolean isSetEllipsoidalCS() {
        return (this.ellipsoidalCS!= null);
    }

    /**
     * Gets the value of the cartesianCS property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CartesianCSPropertyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link CartesianCSPropertyType }{@code >}
     *     
     */
    public JAXBElement<CartesianCSPropertyType> getCartesianCS() {
        return cartesianCS;
    }

    /**
     * Sets the value of the cartesianCS property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CartesianCSPropertyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link CartesianCSPropertyType }{@code >}
     *     
     */
    public void setCartesianCS(JAXBElement<CartesianCSPropertyType> value) {
        this.cartesianCS = value;
    }

    public boolean isSetCartesianCS() {
        return (this.cartesianCS!= null);
    }

    /**
     * Gets the value of the sphericalCS property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SphericalCSPropertyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link SphericalCSPropertyType }{@code >}
     *     
     */
    public JAXBElement<SphericalCSPropertyType> getSphericalCS() {
        return sphericalCS;
    }

    /**
     * Sets the value of the sphericalCS property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SphericalCSPropertyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link SphericalCSPropertyType }{@code >}
     *     
     */
    public void setSphericalCS(JAXBElement<SphericalCSPropertyType> value) {
        this.sphericalCS = value;
    }

    public boolean isSetSphericalCS() {
        return (this.sphericalCS!= null);
    }

    /**
     * Gets the value of the geodeticDatum property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link GeodeticDatumPropertyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link GeodeticDatumPropertyType }{@code >}
     *     
     */
    public JAXBElement<GeodeticDatumPropertyType> getGeodeticDatum() {
        return geodeticDatum;
    }

    /**
     * Sets the value of the geodeticDatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link GeodeticDatumPropertyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link GeodeticDatumPropertyType }{@code >}
     *     
     */
    public void setGeodeticDatum(JAXBElement<GeodeticDatumPropertyType> value) {
        this.geodeticDatum = value;
    }

    public boolean isSetGeodeticDatum() {
        return (this.geodeticDatum!= null);
    }

}
