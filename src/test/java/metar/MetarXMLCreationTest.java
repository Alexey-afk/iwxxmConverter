/**
 * Copyright (C) 2018 Dmitry Moryakov, Main aeronautical meteorological center, Moscow, Russia
 * moryakovdv[at]gmail[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package metar;

import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.BeforeClass;
import org.junit.Test;

import _int.icao.iwxxm._2.AerodromeHorizontalVisibilityPropertyType;
import _int.icao.iwxxm._2.AerodromeHorizontalVisibilityType;
import _int.icao.iwxxm._2.AerodromeObservedCloudsPropertyType;
import _int.icao.iwxxm._2.AerodromeObservedCloudsType;
import _int.icao.iwxxm._2.AerodromePresentWeatherType;
import _int.icao.iwxxm._2.AerodromeSurfaceWindPropertyType;
import _int.icao.iwxxm._2.AerodromeSurfaceWindType;
import _int.icao.iwxxm._2.AerodromeWindShearPropertyType;
import _int.icao.iwxxm._2.AerodromeWindShearType;
import _int.icao.iwxxm._2.CloudAmountReportedAtAerodromeType;
import _int.icao.iwxxm._2.CloudLayerPropertyType;
import _int.icao.iwxxm._2.CloudLayerType;
import _int.icao.iwxxm._2.DistanceWithNilReasonType;
import _int.icao.iwxxm._2.METARType;
import _int.icao.iwxxm._2.MeteorologicalAerodromeObservationRecordPropertyType;
import _int.icao.iwxxm._2.MeteorologicalAerodromeObservationRecordType;
import _int.icao.iwxxm._2.MeteorologicalAerodromeObservationRecordType.Cloud;
import _int.icao.iwxxm._2.MeteorologicalAerodromeReportStatusType;
import _int.icao.iwxxm._2.PermissibleUsageReasonType;
import _int.icao.iwxxm._2.PermissibleUsageType;
import _int.icao.iwxxm._2.RunwayDirectionPropertyType;
import _int.icao.iwxxm._2.AerodromeCloudForecastType.Layer;
import _int.wmo.def.metce._2013.ProcessType;
import aero.aixm.schema._5_1.AirportHeliportTimeSlicePropertyType;
import aero.aixm.schema._5_1.AirportHeliportTimeSliceType;
import aero.aixm.schema._5_1.AirportHeliportType;
import aero.aixm.schema._5_1.CodeAirportHeliportDesignatorType;
import aero.aixm.schema._5_1.CodeICAOType;
import aero.aixm.schema._5_1.RunwayDirectionType;
import common.IWXXM21Helpers;
import common.NamespaceMapper;
import common.StringConstants;
import common.UriConstants;
import iwxxmenums.ANGLE_UNITS;
import iwxxmenums.LENGTH_UNITS;
import iwxxmenums.PRESSURE_UNITS;
import iwxxmenums.SPEED_UNITS;
import iwxxmenums.TEMPERATURE_UNITS;
import net.opengis.gml.v_3_2_1.AngleType;
import net.opengis.gml.v_3_2_1.CodeType;
import net.opengis.gml.v_3_2_1.FeaturePropertyType;
import net.opengis.gml.v_3_2_1.LengthType;
import net.opengis.gml.v_3_2_1.MeasureType;
import net.opengis.gml.v_3_2_1.ReferenceType;
import net.opengis.gml.v_3_2_1.SpeedType;
import net.opengis.gml.v_3_2_1.StringOrRefType;
import net.opengis.gml.v_3_2_1.TimeInstantPropertyType;
import net.opengis.gml.v_3_2_1.TimeInstantType;
import net.opengis.gml.v_3_2_1.TimePositionType;
import net.opengis.gml.v_3_2_1.TimePrimitivePropertyType;
import net.opengis.om._2.OMObservationPropertyType;
import net.opengis.om._2.OMObservationType;
import net.opengis.om._2.OMProcessPropertyType;
import net.opengis.om._2.TimeObjectPropertyType;
import net.opengis.samplingspatial._2.SFSpatialSamplingFeatureType;
import net.opengis.samplingspatial._2.ShapeType;
import wmo.WMOCloudRegister;
import wmo.WMOPrecipitationRegister;


/**This is the initial test class generally intended to STUDY all links between generated classes.
 * The process of the METAR creation 'from scratch'.
 */

public class MetarXMLCreationTest {

	/*First of all we should create any involved object with ObjectFactory helpers*/
	_int.icao.iwxxm._2.ObjectFactory ofIWXXM = new _int.icao.iwxxm._2.ObjectFactory();
	net.opengis.gml.v_3_2_1.ObjectFactory ofGML = new net.opengis.gml.v_3_2_1.ObjectFactory();
	net.opengis.om._2.ObjectFactory ofOM = new net.opengis.om._2.ObjectFactory();
	org.w3._1999.xlink.ObjectFactory ofXLink = new org.w3._1999.xlink.ObjectFactory();
	aero.aixm.schema._5_1.ObjectFactory ofAIXM = new aero.aixm.schema._5_1.ObjectFactory();
	_int.wmo.def.metce._2013.ObjectFactory ofMetce = new _int.wmo.def.metce._2013.ObjectFactory();
	net.opengis.samplingspatial._2.ObjectFactory ofSams = new net.opengis.samplingspatial._2.ObjectFactory();

	static final DateTimeFormatter dtFormat = DateTimeFormat.forPattern("yyyyMMddHHmmss");
	static final DateTimeFormatter dtFormatISO = ISODateTimeFormat.dateTimeNoMillis();

	static String testDateTime = "";
	static String testDateTimePosition = "";

	IWXXM21Helpers iwxxmHelpers = new IWXXM21Helpers();
	
	
	/**Before the test provide DateTime initialization with JodaTime*/
	@BeforeClass
	public static void beforeClass() {
		
		// today 00:00
		DateTime dtTest = DateTime.now().withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
		
		//set common METAR time - plus this hour and 30 minutes
		dtTest = dtTest.plusHours(DateTime.now().withZone(DateTimeZone.UTC).getHourOfDay());
		dtTest = dtTest.plusMinutes(30);
		
		testDateTime = dtTest.toString(dtFormat) + "Z";
		testDateTimePosition = dtTest.toString(dtFormatISO);
	}

	/**Generate METAR XML from scratch*/
	@Test
	public void testMetarCreationFromScratch() throws DatatypeConfigurationException, JAXBException {

		// <iwxxm:METAR> root tag
		METARType metarRootTag = ofIWXXM.createMETARType();

		// Id with ICAO code and current timestamp
		metarRootTag.setId("metar-UUWW-" + testDateTime);

		// Let it be automated
		metarRootTag.setAutomatedStation(true);
		
		// Set NON_OPERATIONAL and TEST properties. 
		metarRootTag.setPermissibleUsage(PermissibleUsageType.NON_OPERATIONAL);
		metarRootTag.setPermissibleUsageReason(PermissibleUsageReasonType.TEST);
		
		//Some description
		metarRootTag.setPermissibleUsageSupplementary("METAR composing test using JAXB");

		// This is not a COR or AMD
		metarRootTag.setStatus(MeteorologicalAerodromeReportStatusType.NORMAL);

		
		//
		metarRootTag = addTranslationCentreHeader(metarRootTag);
		
		
		//Compose METAR body message and place it in the root
		metarRootTag.setObservation(createObservationResult());

		// TODO : create TrendForecast and possible Extensions (RMK)

		
		//create XML representation
		marshallMessageToXML(metarRootTag);

	}

	/**If we act as a translation centre - include following tags to the header, 
	 * else do not include any of them
	 * @throws DatatypeConfigurationException 
	 * */
	private METARType addTranslationCentreHeader(METARType metar) throws DatatypeConfigurationException {

		// Create and set special XML DateTime object
		GregorianCalendar calDateTime = DateTime.now().toGregorianCalendar();
		XMLGregorianCalendar xmlCalRepr = DatatypeFactory.newInstance().newXMLGregorianCalendar(calDateTime);
		metar.setTranslationTime(xmlCalRepr);
		metar.setTranslatedBulletinReceptionTime(xmlCalRepr);
		UUID bullId = UUID.randomUUID();
		metar.setTranslatedBulletinID(bullId.toString());
		metar.setTranslationCentreName("UUWW Vnukovo RU");
		metar.setTranslationCentreDesignator("UUWW");
		// One can place the failed(wrong) TAC METAR into attribute to store it
		metar.setTranslationFailedTAC("");
		

		return metar;
	}
	
	private OMObservationPropertyType createObservationResult() {

		// тег <>om:OM_Observation
		OMObservationPropertyType omOM_Observation = ofOM.createOMObservationPropertyType();

		OMObservationType ot = ofOM.createOMObservationType();
		ot.setId("obs-UUWW-" + testDateTime);
		
		//тип наблюдения - ссылка xlink:href
		ReferenceType observeType = ofGML.createReferenceType();
		observeType.setHref(UriConstants.OBSERVATION_TYPE_METAR);
		ot.setType(observeType);
		
		//Create instant time section
		TimeObjectPropertyType timeObjectProperty = ofOM.createTimeObjectPropertyType();
		TimeInstantType timeInstant = ofGML.createTimeInstantType();
		timeInstant.setId("ti-UUWW-"+testDateTime);
		TimePositionType timePosition = ofGML.createTimePositionType();
		timePosition.getValue().add(testDateTimePosition);
		timeInstant.setTimePosition(timePosition);
		
		JAXBElement<TimeInstantType> timeElement = ofGML.createTimeInstant(timeInstant);
		timeObjectProperty.setAbstractTimeObject(timeElement);
		
		//and place it to <phenomenonTime>
		ot.setPhenomenonTime(timeObjectProperty);
		
		//create <resultTime>
		TimeInstantPropertyType timeInstantResult = ofGML.createTimeInstantPropertyType();
		timeInstantResult.setHref("#ti-UUWW-"+testDateTime);
		ot.setResultTime(timeInstantResult);
		
		//create <om:procedure> frame
		ProcessType metceProcess = ofMetce.createProcessType();
		metceProcess.setId("p-49-2-metar");
		
		StringOrRefType processDescription = ofGML.createStringOrRefType();
		processDescription.setValue(StringConstants.WMO_49_2_METCE_METAR);
		metceProcess.setDescription(processDescription);
		
		OMProcessPropertyType omProcedure = ofOM.createOMProcessPropertyType();
		omProcedure.setAny(ofMetce.createProcess(metceProcess));
		ot.setProcedure(omProcedure);
		
		//тег om:ObserverdProperty
		ReferenceType observedProperty = ofGML.createReferenceType();
		observedProperty.setHref(UriConstants.OBSERVED_PROPERTY_METAR);
		observedProperty.setTitle(StringConstants.WMO_METAR_OBSERVED_PROPERTY_TITLE);
		
		ot.setObservedProperty(observedProperty);
		
		ot.setFeatureOfInterest(createAirportDescriptionSectionTag());
		
		
		// At last create payload
		MeteorologicalAerodromeObservationRecordPropertyType metarRecord = createMETARRecordTag();

		// and place it into <iwxxm:result> section
		ot.setResult(metarRecord);

		omOM_Observation.setOMObservation(ot);

		return omOM_Observation;

	}

	/**Create aerodrome description section as GML FeatureOfInterest 
	 * ICAO code=UUWW*/
	private FeaturePropertyType createAirportDescriptionSectionTag() {

		FeaturePropertyType airportTag = ofGML.createFeaturePropertyType();
		SFSpatialSamplingFeatureType sfFeature = ofSams.createSFSpatialSamplingFeatureType();
		sfFeature.setId("sp-UUWW");
		
		//empty shape tag
		ShapeType shape = ofSams.createShapeType();
		sfFeature.setShape(shape);
		
		
		ReferenceType sfType = ofGML.createReferenceType();
		sfType.setHref(UriConstants.GIS_SAMPLING_FEATURE);
		sfFeature.setType(sfType);
		
		AirportHeliportType airportType = ofAIXM.createAirportHeliportType();
		airportType.setId("aerodrome-UUWW");
		
		AirportHeliportTimeSlicePropertyType ahTimeSliceProperty = ofAIXM.createAirportHeliportTimeSlicePropertyType();
		AirportHeliportTimeSliceType ahTimeSliceType = ofAIXM.createAirportHeliportTimeSliceType();
		ahTimeSliceType.setId("aerodrome-UUWW-ts");
		
		TimePrimitivePropertyType validTime = ofGML.createTimePrimitivePropertyType();
		ahTimeSliceType.setValidTime(validTime);
		ahTimeSliceType.setInterpretation("BASELINE");
		
		CodeAirportHeliportDesignatorType designator = ofAIXM.createCodeAirportHeliportDesignatorType();
		designator.setValue("UUWW");
		JAXBElement<CodeAirportHeliportDesignatorType> designatorTag = ofAIXM.createAirportHeliportTimeSliceTypeDesignator(designator);
		ahTimeSliceType.getRest().add(designatorTag);
		
		
		CodeICAOType icaoType = ofAIXM.createCodeICAOType();
		icaoType.setValue("UUWW");
		JAXBElement<CodeICAOType> locationIdicator = ofAIXM.createAirportHeliportTimeSliceTypeLocationIndicatorICAO(icaoType);
		ahTimeSliceType.getRest().add(locationIdicator);
		
		ahTimeSliceProperty.setAirportHeliportTimeSlice(ahTimeSliceType);
		
		
		airportType.getTimeSlice().add(ahTimeSliceProperty);
		
		JAXBElement<AirportHeliportType> ahTag =ofAIXM.createAirportHeliport(airportType);
		
		FeaturePropertyType sfAirport = ofGML.createFeaturePropertyType();
		sfAirport.setAbstractFeature(ahTag);
		
		sfFeature.getSampledFeature().add(sfAirport);
		
		JAXBElement<SFSpatialSamplingFeatureType> sfFeatureTag = ofSams.createSFSpatialSamplingFeature(sfFeature);
		
		airportTag.setAbstractFeature(sfFeatureTag);
		return airportTag;
		
	}
	
	/**Create valuable METAR section as Observation result.
	 * Tag <iwxxm:MeteorologicalAerodromeObservtionRecord>
	 */
	private MeteorologicalAerodromeObservationRecordPropertyType createMETARRecordTag() {
		
		//Envelop
		MeteorologicalAerodromeObservationRecordPropertyType metarRecordTag = ofIWXXM
				.createMeteorologicalAerodromeObservationRecordPropertyType();
		//body
		MeteorologicalAerodromeObservationRecordType metarRecord = ofIWXXM
				.createMeteorologicalAerodromeObservationRecordType();
		metarRecord.setId("obs-record-UUWW-" + testDateTime);

		//Set temperature
		MeasureType mtTemperature = ofGML.createMeasureType();
		mtTemperature.setUom(TEMPERATURE_UNITS.CELSIUS.getStringValue());
		mtTemperature.setValue(11.2);
		metarRecord.setAirTemperature(mtTemperature);

		//Set dew pont
		MeasureType mtDew = ofGML.createMeasureType();
		mtDew.setUom(TEMPERATURE_UNITS.CELSIUS.getStringValue());
		mtDew.setValue(5.2);
		metarRecord.setDewpointTemperature(mtDew);

		// Set QNH
		MeasureType mtQNH = ofGML.createMeasureType();
		mtQNH.setUom(PRESSURE_UNITS.HECTOPASCALS.getStringValue());
		mtQNH.setValue(1024);
		metarRecord.setQnh(mtQNH);

		//TODO: keep in mind that there are restrictions for CAVOK conditions, so let it be false
		metarRecord.setCloudAndVisibilityOK(false);

		//Create and set wind section
		metarRecord.setSurfaceWind(createWindSectionTag());

		//Create and set visibility section
		metarRecord.setVisibility(createVisibilitySectionTag());
		
		//create and set present weather conditions
		metarRecord.getPresentWeather().add(createWeatherSection());
		
		//Create and set cloud section
		metarRecord.setCloud(createCloudSectionTag());

		//Test with wind shear
		//metarRecord.setWindShear(createWindShearTag());

		//Place the body into envelop
		metarRecordTag.setMeteorologicalAerodromeObservationRecord(metarRecord);
		
		return metarRecordTag;
	}

	/**Wind section*/
	private AerodromeSurfaceWindPropertyType createWindSectionTag() {
		//Envelop
		AerodromeSurfaceWindPropertyType surfaceWindType = ofIWXXM.createAerodromeSurfaceWindPropertyType();

		//body
		AerodromeSurfaceWindType surfaceWind = ofIWXXM.createAerodromeSurfaceWindType();

		//Let it be VRB
		surfaceWind.setVariableWindDirection(true);

		//Set gust speed 6m/s
		SpeedType speedGustType = ofGML.createSpeedType();
		speedGustType.setUom(SPEED_UNITS.MPS.getStringValue());
		speedGustType.setValue(6f);
		surfaceWind.setWindGustSpeed(speedGustType);

		//Set mean wind 2m/s
		SpeedType speedMeanType = ofGML.createSpeedType();
		speedMeanType.setUom(SPEED_UNITS.MPS.getStringValue());
		speedMeanType.setValue(2f);
		surfaceWind.setMeanWindSpeed(speedMeanType);

		//Set wind direction
		AngleType windAngle = ofGML.createAngleType();
		windAngle.setUom(ANGLE_UNITS.DEGREES.getStringValue());
		windAngle.setValue(80d);
		surfaceWind.setMeanWindDirection(windAngle);

		//Set wind angles
		AngleType windAngleCW = ofGML.createAngleType();
		windAngleCW.setUom(ANGLE_UNITS.DEGREES.getStringValue());
		windAngleCW.setValue(60d);
		surfaceWind.setExtremeClockwiseWindDirection(windAngleCW);
		
		AngleType windAngleCCW = ofGML.createAngleType();
		windAngleCCW.setUom(ANGLE_UNITS.DEGREES.getStringValue());
		windAngleCCW.setValue(120d);
		surfaceWind.setExtremeCounterClockwiseWindDirection(windAngleCCW);

		//Place body into envelop
		surfaceWindType.setAerodromeSurfaceWind(surfaceWind);
		return surfaceWindType;
	}

	private AerodromePresentWeatherType createWeatherSection() {
		 //<iwxxm:weather xlink:href="http://codes.wmo.int/306/4678/-SHRA"/>
		 
		 AerodromePresentWeatherType presentWeather = ofIWXXM.createAerodromePresentWeatherType();
		 
		 presentWeather.setHref(iwxxmHelpers.getPrecipitationReg().getWMOUrlByCode("SHRA"));
		 
		 return presentWeather;
	}
	
	/**Visibility section*/
	private AerodromeHorizontalVisibilityPropertyType createVisibilitySectionTag() {
		//Envelop 
		AerodromeHorizontalVisibilityPropertyType visiblityType = ofIWXXM
				.createAerodromeHorizontalVisibilityPropertyType();
		//body
		AerodromeHorizontalVisibilityType visibility = ofIWXXM.createAerodromeHorizontalVisibilityType();

		//Minimal visibility
		LengthType minVis = ofGML.createLengthType();
		minVis.setUom(LENGTH_UNITS.METERS.getStringValue());
		minVis.setValue(6000d);

		//Prevailing visibility
		LengthType prevailVis = ofGML.createLengthType();
		prevailVis.setUom(LENGTH_UNITS.METERS.getStringValue());
		prevailVis.setValue(10000d);

		visibility.setMinimumVisibility(minVis);
		visibility.setPrevailingVisibility(prevailVis);

		//Place body to the envelop
		visiblityType.setAerodromeHorizontalVisibility(visibility);
		
		return visiblityType;
	}

	/**Cloud section*/
	private JAXBElement<MeteorologicalAerodromeObservationRecordType.Cloud> createCloudSectionTag() {
		
		MeteorologicalAerodromeObservationRecordType.Cloud cloudSection = new Cloud();
		
		
		//Envelop
		AerodromeObservedCloudsPropertyType cloudsType = ofIWXXM.createAerodromeObservedCloudsPropertyType();
		
		//Body
		AerodromeObservedCloudsType clouds = ofIWXXM.createAerodromeObservedCloudsType();
		
		
		
		//Create layer
		AerodromeObservedCloudsType.Layer cloudLayer = ofIWXXM.createAerodromeObservedCloudsTypeLayer();
		cloudLayer.setCloudLayer(iwxxmHelpers.createCloudLayerSection(1, 100,
				"CB", null, LENGTH_UNITS.FEETS));
		clouds.getLayer().add(cloudLayer);
		
		
		

		//TODO: add some more cloud layers

		//Place body into envelop
		cloudsType.setAerodromeObservedClouds(clouds);
		
		return ofIWXXM.createMeteorologicalAerodromeObservationRecordTypeCloud(cloudSection);
		
	}

	/**Wind shear section*/
	private AerodromeWindShearPropertyType createWindShearTag() {
		//Envelop
		AerodromeWindShearPropertyType windShearType = ofIWXXM.createAerodromeWindShearPropertyType();
		//body
		AerodromeWindShearType windShear = ofIWXXM.createAerodromeWindShearType();
		
		//Runway description
		RunwayDirectionPropertyType runwayType = ofIWXXM.createRunwayDirectionPropertyType();
		RunwayDirectionType runway = ofAIXM.createRunwayDirectionType();
		
		runway.setId("runway-R24");
		CodeType rwCode = ofGML.createCodeType();
		rwCode.setValue("R24");
		runway.getName().add(rwCode);
		runwayType.setRunwayDirection(runway);
		
		windShear.getRunway().add(runwayType);
		
		//Place body to envelop
		windShearType.setAerodromeWindShear(windShear);
		
		
		return windShearType;
	}

	/**Marshall root METAR to XML with console output*/
	public void marshallMessageToXML(METARType metar) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(METARType.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, UriConstants.GLOBAL_SCHEMAS_LOCATION);

		jaxbMarshaller.setProperty(StringConstants.SUN_JAXB_NAMESPACE_MAPPING_PROPERTY_NAME, new NamespaceMapper());

		JAXBElement<METARType> metarRootElement = ofIWXXM.createMETAR(metar);

		jaxbMarshaller.marshal(metarRootElement, System.out);
	}

}
