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
package org.gamc.spmi.iwxxmConverter.marshallers.v3;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import org.gamc.spmi.iwxxmConverter.common.NamespaceMapper;
import org.gamc.spmi.iwxxmConverter.common.StringConstants;
import org.gamc.spmi.iwxxmConverter.exceptions.ParsingException;
import org.gamc.spmi.iwxxmConverter.general.TafForecastSection;
import org.gamc.spmi.iwxxmConverter.general.TafForecastTimeSection;
import org.gamc.spmi.iwxxmConverter.iwxxmenums.ANGLE_UNITS;
import org.gamc.spmi.iwxxmConverter.iwxxmenums.LENGTH_UNITS;
import org.gamc.spmi.iwxxmConverter.iwxxmenums.TEMPERATURE_UNITS;
import org.gamc.spmi.iwxxmConverter.tac.TacConverter;
import org.gamc.spmi.iwxxmConverter.tafconverter.TAFCloudSection;
import org.gamc.spmi.iwxxmConverter.tafconverter.TafCommonWeatherSection;
import org.gamc.spmi.iwxxmConverter.wmo.WMOCloudRegister;
import org.joda.time.DateTime;

import schemabindings31._int.icao.iwxxm._3.AerodromeAirTemperatureForecastPropertyType;
import schemabindings31._int.icao.iwxxm._3.AerodromeAirTemperatureForecastType;
import schemabindings31._int.icao.iwxxm._3.AerodromeCloudForecastPropertyType;
import schemabindings31._int.icao.iwxxm._3.AerodromeCloudForecastType;
import schemabindings31._int.icao.iwxxm._3.AerodromeCloudType.Layer;
import schemabindings31._int.icao.iwxxm._3.AerodromeForecastChangeIndicatorType;
import schemabindings31._int.icao.iwxxm._3.AerodromeForecastWeatherType;
import schemabindings31._int.icao.iwxxm._3.AerodromeSurfaceWindForecastPropertyType;
import schemabindings31._int.icao.iwxxm._3.AerodromeSurfaceWindForecastType;
import schemabindings31._int.icao.iwxxm._3.AirportHeliportPropertyType;
import schemabindings31._int.icao.iwxxm._3.LengthWithNilReasonType;
import schemabindings31._int.icao.iwxxm._3.MeteorologicalAerodromeForecastPropertyType;
import schemabindings31._int.icao.iwxxm._3.MeteorologicalAerodromeForecastType;
import schemabindings31._int.icao.iwxxm._3.PermissibleUsageReasonType;
import schemabindings31._int.icao.iwxxm._3.PermissibleUsageType;
import schemabindings31._int.icao.iwxxm._3.ReportStatusType;
import schemabindings31._int.icao.iwxxm._3.SIGMETEvolvingConditionCollectionPropertyType;
import schemabindings31._int.icao.iwxxm._3.SIGMETEvolvingConditionCollectionType;
import schemabindings31._int.icao.iwxxm._3.SIGMETType;
import schemabindings31._int.icao.iwxxm._3.TAFType;
import schemabindings31.net.opengis.gml.v_3_2_1.AngleType;
import schemabindings31.net.opengis.gml.v_3_2_1.AssociationRoleType;
import schemabindings31.net.opengis.gml.v_3_2_1.LengthType;
import schemabindings31.net.opengis.gml.v_3_2_1.MeasureType;
import schemabindings31.net.opengis.gml.v_3_2_1.SpeedType;
import schemabindings31.net.opengis.gml.v_3_2_1.TimeInstantPropertyType;
import schemabindings31.net.opengis.gml.v_3_2_1.TimeInstantType;
import schemabindings31.net.opengis.gml.v_3_2_1.TimePeriodPropertyType;
import schemabindings31.net.opengis.gml.v_3_2_1.TimePeriodType;
import schemabindings31.net.opengis.gml.v_3_2_1.TimePositionType;

/**
 * Base class to perform conversion of TAC into intermediate object
 * {@link TAFTacMessage} and further IWXXM conversion and validation
 */
public class TAFConverterV3 implements TacConverter<TAFTacMessage, TAFType> {

	
	/** Our own helpers to suppress boiler-plate code */
	static final IWXXM31Helpers iwxxmHelpers = new IWXXM31Helpers();

	private String dateTime = "";
	private String dateTimePosition = "";

	private String timePeriodBegin = "";
	private String timePeriodEnd = "";

	private String timePeriodBeginPosition = "";
	private String timePeriodEndPosition = "";

	private TAFTacMessage translatedTaf;

		
	/**
	 * Converts given TAC string to IWXXM string
	 * 
	 * @param tac
	 *            - TAC to convert
	 * @return - XML String in IWXXM format
	 */
	@Override
	public String convertTacToXML(String tac)
			throws UnsupportedEncodingException, DatatypeConfigurationException, JAXBException {

		TAFTacMessage tafMessage = new TAFTacMessage(tac);
		

		TAFType result;
		try {
			tafMessage.parseMessage();
			 result = convertMessage(tafMessage);
		}
		catch(ParsingException pe) {
			result = IWXXM31Helpers.ofIWXXM.createTAFType();
			result.setTranslationFailedTAC(tac);
		}
		String xmlResult = marshallMessageToXML(result);
		return xmlResult;
	}

	/**
	 * Mapps internal TAC representation to JAXB-objects
	 * 
	 * @param translatedTaf
	 *            - TAC as internal object
	 * @return XML String in IWXXM format
	 */
	@Override
	public TAFType convertMessage(TAFTacMessage translatedTaf)
			throws DatatypeConfigurationException, UnsupportedEncodingException, JAXBException, ParsingException {

		this.translatedTaf = translatedTaf;

		// set common TAF time
		dateTime = translatedTaf.getMessageIssueDateTime().toString(iwxxmHelpers.getDateTimeFormat()) + "Z";
		dateTimePosition = translatedTaf.getMessageIssueDateTime().toString(iwxxmHelpers.getDateTimeISOFormat());

		timePeriodBeginPosition = translatedTaf.getValidityInterval().getStart()
				.toString(iwxxmHelpers.getDateTimeISOFormat());
		timePeriodEndPosition = translatedTaf.getValidityInterval().getEnd()
				.toString(iwxxmHelpers.getDateTimeISOFormat());

		timePeriodBegin = translatedTaf.getValidityInterval().getStart().toString(iwxxmHelpers.getDateTimeFormat())
				+ "Z";
		timePeriodEnd = translatedTaf.getValidityInterval().getEnd().toString(iwxxmHelpers.getDateTimeFormat()) + "Z";

		// <iwxxm:TAF> root tag
		TAFType tafRootTag = IWXXM31Helpers.ofIWXXM.createTAFType();

		// Id with ICAO code and current timestamp
		tafRootTag.setId(iwxxmHelpers.generateUUIDv4(String.format("taf-%s-%s", translatedTaf.getIcaoCode(), dateTime)));

		// Set NON_OPERATIONAL and TEST properties.
		tafRootTag.setPermissibleUsage(PermissibleUsageType.NON_OPERATIONAL);
		tafRootTag.setPermissibleUsageReason(PermissibleUsageReasonType.TEST);

		// COR, AMD, CNL, NORMAL
		switch (translatedTaf.getMessageStatusType()) {
		case AMENDMENT:
			tafRootTag.setReportStatus(ReportStatusType.AMENDMENT);
			break;
		case CANCEL:
			tafRootTag.setReportStatus(null);
			break;
		case CORRECTION:
			tafRootTag.setReportStatus(ReportStatusType.CORRECTION);
			break;
		default:
			tafRootTag.setReportStatus(ReportStatusType.NORMAL);
		}

		// Some description
		tafRootTag.setPermissibleUsageSupplementary("TAF composing test using JAXB");

		tafRootTag = addTranslationCentreHeader(tafRootTag);

		// issuetime and valid period are top-level tags
		tafRootTag.setIssueTime(createIssueTimesection());
		tafRootTag.setValidPeriod(iwxxmHelpers.createTimePeriod(translatedTaf.getIcaoCode(),translatedTaf.getValidityInterval().getStart(), translatedTaf.getValidityInterval().getEnd()));

		// Compose TAF body message and place it in the root
		tafRootTag.setBaseForecast(createBaseResultSection());

		// TODO : create change section for TrendForecast and possible Extensions (RMK)
		AtomicInteger globalSectionIndex = new AtomicInteger(1);
		for (TafForecastSection bcmgSection : translatedTaf.getBecomingSections()) {

			bcmgSection.parseSection();

			MeteorologicalAerodromeForecastPropertyType trendSectionforecast = createTrendResultsSection(bcmgSection,
					globalSectionIndex.getAndIncrement());
			tafRootTag.getChangeForecast().add(trendSectionforecast);
		}

		for (TafForecastSection tempoSection : translatedTaf.getTempoSections()) {

			tempoSection.parseSection();

			MeteorologicalAerodromeForecastPropertyType trendSectionforecast = createTrendResultsSection(tempoSection,
					globalSectionIndex.getAndIncrement());
			tafRootTag.getChangeForecast().add(trendSectionforecast);
		}

		for (TafForecastTimeSection timedSection : translatedTaf.getTimedSections()) {
			timedSection.parseSection();
			MeteorologicalAerodromeForecastPropertyType trendTimedSectionforecast = createTrendResultsSection(timedSection,
					globalSectionIndex.getAndIncrement());
			//tafRootTag.getChangeForecast().add(trendTimedSectionforecast);
		}

		for (TafForecastSection probSection : translatedTaf.getProbabilitySections()) {
			probSection.parseSection();
			MeteorologicalAerodromeForecastPropertyType probabilitySectionforecast = createTrendResultsSection(probSection,
					globalSectionIndex.getAndIncrement());
			//tafRootTag.getChangeForecast().add(probabilitySectionforecast);
		}

		return tafRootTag;
	}

	/** Creates issueTime */
	private TimeInstantPropertyType createIssueTimesection() {
		return iwxxmHelpers.createJAXBTimeSection(translatedTaf.getMessageIssueDateTime(), translatedTaf.getIcaoCode());
	}

	

	/** Creates base section of IWXXM TAF */
	/*
	private MeteorologicalAerodromeForecastPropertyType createBaseForecast() {

		// тег <om:OM_Observation>
		OMObservationPropertyType omOM_Observation = ofOM.createOMObservationPropertyType();
		OMObservationType ot = ofOM.createOMObservationType();
		ot.setId(iwxxmHelpers.generateUUIDv4(String.format("bf-%s-%s", translatedTaf.getIcaoCode(), dateTime)));

		// тип наблюдения - ссылка xlink:href
		ReferenceType observeType = ofGML.createReferenceType();
		observeType.setHref(UriConstants.OBSERVATION_TYPE_TAF);
		ot.setType(observeType);

		//ot.set(createAirportDescriptionSectionTag());

		// phenomenon time for taf always equals to validityPeriod
		TimeObjectPropertyType phenomenonTimeProperty = ofOM.createTimeObjectPropertyType();
		JAXBElement<TimePeriodType> timeElement = ofGML.createTimePeriod(iwxxmHelpers
				.createTrendPeriodSection(translatedTaf.getIcaoCode(), translatedTaf.getValidityInterval().getStart(),
						translatedTaf.getValidityInterval().getEnd(), 0)
				.getTimePeriod());
		phenomenonTimeProperty.setAbstractTimeObject(timeElement);
		ot.setPhenomenonTime(phenomenonTimeProperty);

		// result time for taf = link to issueTime
		TimeInstantPropertyType resultTime = ofGML.createTimeInstantPropertyType();
		resultTime.setHref("#" + createIssueTimesection().getTimeInstant().getId());
		ot.setResultTime(resultTime);

		// valid time - link to valid time
		TimePeriodPropertyType validTime = ofGML.createTimePeriodPropertyType();
		validTime.setHref("#" + createValidityPeriodSection().getTimePeriod().getId());
		ot.setValidTime(validTime);

		// create <om:procedure> frame
		ProcessType metceProcess = ofMetce.createProcessType();
		metceProcess.setId(iwxxmHelpers.generateUUIDv4("p-49-2-taf-" + translatedTaf.getIcaoCode()));

		StringOrRefType processDescription = ofGML.createStringOrRefType();
		processDescription.setValue(StringConstants.WMO_49_2_METCE_TAF);
		metceProcess.setDescription(processDescription);

		OMProcessPropertyType omProcedure = ofOM.createOMProcessPropertyType();
		omProcedure.setAny(ofMetce.createProcess(metceProcess));
		ot.setProcedure(omProcedure);

		// тег om:ObserverdProperty
		ReferenceType observedProperty = ofGML.createReferenceType();
		observedProperty.setHref(UriConstants.OBSERVED_PROPERTY_TAF);
		// observedProperty.setTitle(StringConstants.WMO_TAF_OBSERVED_PROPERTY_TITLE);
		ot.setObservedProperty(observedProperty);

		// set result section
		//ot.setResult(createBaseResultSection());

		omOM_Observation.setOMObservation(ot);

		return omOM_Observation;
	}
*/
	/** Result section of the BASE taf */
	private MeteorologicalAerodromeForecastPropertyType createBaseResultSection() {

		MeteorologicalAerodromeForecastPropertyType recordPropertyType = IWXXM31Helpers.ofIWXXM
				.createMeteorologicalAerodromeForecastPropertyType();
		MeteorologicalAerodromeForecastType recordType = IWXXM31Helpers.ofIWXXM.createMeteorologicalAerodromeForecastType();
		
		recordPropertyType.setMeteorologicalAerodromeForecast(recordType);
		
		// set id
		recordType.setId(iwxxmHelpers.generateUUIDv4(String.format("base-fcst-record-%s", translatedTaf.getIcaoCode())));

		// CAVOK
		recordType.setCloudAndVisibilityOK(translatedTaf.getCommonWeatherSection().isCavok());

		// visibility
		if (translatedTaf.getCommonWeatherSection().getPrevailVisibility() != null) {
			LengthType vis = IWXXM31Helpers.ofGML.createLengthType();
			vis.setUom(translatedTaf.getCommonWeatherSection().getVisibilityUnits().getStringValue());
			vis.setValue(translatedTaf.getCommonWeatherSection().getPrevailVisibility());
			recordType.setPrevailingVisibility(vis);
		}

		// surfaceWind
		AerodromeSurfaceWindForecastPropertyType sWindpropertyType = IWXXM31Helpers.ofIWXXM
				.createAerodromeSurfaceWindForecastPropertyType();
		AerodromeSurfaceWindForecastType sWindType = IWXXM31Helpers.ofIWXXM.createAerodromeSurfaceWindForecastType();

		// Set gust speed
		if (translatedTaf.getCommonWeatherSection().getGustSpeed() != null) {
			SpeedType speedGustType = IWXXM31Helpers.ofGML.createSpeedType();
			speedGustType.setUom(translatedTaf.getCommonWeatherSection().getSpeedUnits().getStringValue());
			speedGustType.setValue(translatedTaf.getCommonWeatherSection().getGustSpeed());
			sWindType.setWindGustSpeed(speedGustType);
		}

		// VRB?
		sWindType.setVariableWindDirection(translatedTaf.getCommonWeatherSection().isVrb());

		// Set mean wind
		SpeedType speedMeanType = IWXXM31Helpers.ofGML.createSpeedType();
		speedMeanType.setUom(translatedTaf.getCommonWeatherSection().getSpeedUnits().getStringValue());
		speedMeanType.setValue(translatedTaf.getCommonWeatherSection().getWindSpeed());
		sWindType.setMeanWindSpeed(speedMeanType);

		// Set wind direction
		if (!translatedTaf.getCommonWeatherSection().isVrb()) {
			AngleType windAngle = IWXXM31Helpers.ofGML.createAngleType();
			windAngle.setUom(ANGLE_UNITS.DEGREES.getStringValue());
			windAngle.setValue(translatedTaf.getCommonWeatherSection().getWindDir());
			sWindType.setMeanWindDirection(windAngle);
		}

		sWindpropertyType.setAerodromeSurfaceWindForecast(sWindType);
		recordType.setSurfaceWind(sWindpropertyType);

		// clouds
		recordType.setCloud(
				createCloudSectionTag(translatedTaf.getCommonWeatherSection(), translatedTaf.getIcaoCode(), 0));

		// Min and Max temperatures from taf
		recordType.getTemperature().add(createTemperaturesSection());

		// forecasted weather
		for (String weatherCode : translatedTaf.getCommonWeatherSection().getCurrentWeather()) {
			recordType.getWeather().add(createWeatherSection(weatherCode));
		}

		

		return recordPropertyType;

	}

	/**
	 * Body for trend sections
	 * 
	 * @param section
	 *            - {@link TafForecastSection} object
	 * @param sectionIndex
	 *            - index of the processing section
	 * @return {@link MeteorologicalAerodromeForecastRecordPropertyType} object
	 */
	private MeteorologicalAerodromeForecastPropertyType createTrendResultsSection(TafForecastSection section,
			int sectionIndex) {

		MeteorologicalAerodromeForecastPropertyType recordPropertyType = IWXXM31Helpers.ofIWXXM
				.createMeteorologicalAerodromeForecastPropertyType();
		MeteorologicalAerodromeForecastType recordType = IWXXM31Helpers.ofIWXXM.createMeteorologicalAerodromeForecastType();
		
		recordPropertyType.setMeteorologicalAerodromeForecast(recordType);

		// set id
		recordType.setId(iwxxmHelpers.generateUUIDv4(String.format("change-record-%d-%s", sectionIndex, translatedTaf.getIcaoCode())));
		AerodromeForecastChangeIndicatorType changeIndicator = AerodromeForecastChangeIndicatorType.BECOMING;

		switch (section.getSectionType()) {
		case BECMG:
			changeIndicator = AerodromeForecastChangeIndicatorType.BECOMING;
			break;
		case TEMPO:
			changeIndicator = AerodromeForecastChangeIndicatorType.TEMPORARY_FLUCTUATIONS;
			break;
		case PROB30:
			changeIndicator = AerodromeForecastChangeIndicatorType.PROBABILITY_30;
			break;
		case PROB40:
			changeIndicator = AerodromeForecastChangeIndicatorType.PROBABILITY_40;
			break;

		case PROB30TEMPO:
			changeIndicator = AerodromeForecastChangeIndicatorType.PROBABILITY_30_TEMPORARY_FLUCTUATIONS;
			break;
		case PROB40TEMPO:
			changeIndicator = AerodromeForecastChangeIndicatorType.PROBABILITY_40_TEMPORARY_FLUCTUATIONS;
			break;
		default:
			break;
		}
		recordType.setChangeIndicator(changeIndicator);
		// CAVOK
		recordType.setCloudAndVisibilityOK(section.getCommonWeatherSection().isCavok());

		// visibility
		if (section.getCommonWeatherSection().getPrevailVisibility() != null) {
			LengthType vis = IWXXM31Helpers.ofGML.createLengthType();
			vis.setUom(section.getCommonWeatherSection().getVisibilityUnits().getStringValue());
			vis.setValue(section.getCommonWeatherSection().getPrevailVisibility());

			recordType.setPrevailingVisibility(vis);
		}

		// surfaceWind
		AerodromeSurfaceWindForecastPropertyType sWindpropertyType = IWXXM31Helpers.ofIWXXM
				.createAerodromeSurfaceWindForecastPropertyType();
		AerodromeSurfaceWindForecastType sWindType = IWXXM31Helpers.ofIWXXM.createAerodromeSurfaceWindForecastType();
		boolean sectionHasWind = false;
		// Set gust speed
		if (section.getCommonWeatherSection().getGustSpeed() != null) {
			SpeedType speedGustType = IWXXM31Helpers.ofGML.createSpeedType();
			speedGustType.setUom(section.getCommonWeatherSection().getSpeedUnits().getStringValue());
			speedGustType.setValue(section.getCommonWeatherSection().getGustSpeed());
			sWindType.setWindGustSpeed(speedGustType);
			sectionHasWind = true;
		}

		// Set mean wind
		if (section.getCommonWeatherSection().getWindSpeed() != null) {
			SpeedType speedMeanType = IWXXM31Helpers.ofGML.createSpeedType();
			speedMeanType.setUom(section.getCommonWeatherSection().getSpeedUnits().getStringValue());
			speedMeanType.setValue(section.getCommonWeatherSection().getWindSpeed());
			sWindType.setMeanWindSpeed(speedMeanType);
			sectionHasWind = true;
		}

		// Set wind direction
		if (section.getCommonWeatherSection().getWindDir() != null) {
			AngleType windAngle = IWXXM31Helpers.ofGML.createAngleType();
			windAngle.setUom(ANGLE_UNITS.DEGREES.getStringValue());
			windAngle.setValue(section.getCommonWeatherSection().getWindDir());
			sWindType.setMeanWindDirection(windAngle);
			sectionHasWind = true;
		}

		if (sectionHasWind) {
			sWindpropertyType.setAerodromeSurfaceWindForecast(sWindType);
			recordType.setSurfaceWind(sWindpropertyType);
		}

		// clouds
		if (section.getCommonWeatherSection().getCloudSections().size() > 0) {
			AerodromeCloudForecastPropertyType cloudType = createCloudSectionTag(section.getCommonWeatherSection(),
					translatedTaf.getIcaoCode(), sectionIndex);

			recordType.setCloud(cloudType);
		}

		// forecasted weather
		for (String weatherCode : section.getCommonWeatherSection().getCurrentWeather()) {
			recordType.getWeather().add(iwxxmHelpers.createForecastWeatherSection(weatherCode));
		}

		

		return recordPropertyType;
	}

	/** Creates XML section for change forecast node - TEMPO OR BECOMING */
	/**
	 * @param sectionIndex
	 *            - the index of section among all change sections to create unique
	 *            id for it
	 */
	/*
	private MeteorologicalAerodromeForecastPropertyType createTrendForecast(TafForecastSection section, int sectionIndex) {

		MeteorologicalAerodromeForecastPropertyType trendType = ofIWXXM.createMeteorologicalAerodromeForecastPropertyType();
		MeteorologicalAerodromeForecastType trend = ofIWXXM.createMeteorologicalAerodromeForecastType();
		trendType.setMeteorologicalAerodromeForecast(trend);
		
		
		trend.setId(iwxxmHelpers.generateUUIDv4(String.format("cf-%d-%s", sectionIndex, translatedTaf.getIcaoCode())));


		// phenomenon time for taf always equals to validityPeriod
		TimePeriodPropertyType phenomenonTimeProperty = ofGML.createTimePeriodPropertyType();
		
		TimePeriodType tp = iwxxmHelpers
				.createTrendPeriodSection(translatedTaf.getIcaoCode(), section.getTrendValidityInterval().getStart(),
						section.getTrendValidityInterval().getEnd(), sectionIndex)
				.getTimePeriod();
		phenomenonTimeProperty.setTimePeriod(tp);

		trend.setPhenomenonTime(phenomenonTimeProperty);

	

		
		// тег om:ObserverdProperty
		ReferenceType observedProperty = ofGML.createReferenceType();
		observedProperty.setHref(UriConstants.OBSERVED_PROPERTY_TAF);
		// observedProperty.setTitle(StringConstants.WMO_TAF_OBSERVED_PROPERTY_TITLE);
		ot.setObservedProperty(observedProperty);
		
		// set result section
		t.setResult(createTrendResultsSection(section, sectionIndex));

		

		return trendType;

	}
	*/

	/** Creates sections for min and max temperatures forecasted in TAF */
	private AerodromeAirTemperatureForecastPropertyType createTemperaturesSection() {

		AerodromeAirTemperatureForecastPropertyType tempPropertyType = IWXXM31Helpers.ofIWXXM
				.createAerodromeAirTemperatureForecastPropertyType();
		AerodromeAirTemperatureForecastType temps = IWXXM31Helpers.ofIWXXM.createAerodromeAirTemperatureForecastType();

		// Set min temperature
		if (translatedTaf.getCommonWeatherSection().getAirTemperatureMin() != null) {
			MeasureType minTemperature = IWXXM31Helpers.ofGML.createMeasureType();
			minTemperature.setUom(TEMPERATURE_UNITS.CELSIUS.getStringValue());
			minTemperature.setValue(translatedTaf.getCommonWeatherSection().getAirTemperatureMin().doubleValue());

			TimeInstantPropertyType timeInstantMinTempProperty = iwxxmHelpers.createTimeInstantPropertyTypeForDateTime(
					translatedTaf.getCommonWeatherSection().getAirTemperatureMinTime(), translatedTaf.getIcaoCode(),"tn");

			temps.setMinimumAirTemperature(minTemperature);
			temps.setMinimumAirTemperatureTime(timeInstantMinTempProperty);

		}

		// Set max temperature
		if (translatedTaf.getCommonWeatherSection().getAirTemperatureMax() != null) {
			MeasureType maxTemperature = IWXXM31Helpers.ofGML.createMeasureType();
			maxTemperature.setUom(TEMPERATURE_UNITS.CELSIUS.getStringValue());
			maxTemperature.setValue(translatedTaf.getCommonWeatherSection().getAirTemperatureMax().doubleValue());
			TimeInstantPropertyType timeInstantMaxTempProperty = iwxxmHelpers.createTimeInstantPropertyTypeForDateTime(
					translatedTaf.getCommonWeatherSection().getAirTemperatureMaxTime(), translatedTaf.getIcaoCode(),"tx");

			// Time of the min temp forecasted

			temps.setMaximumAirTemperature(maxTemperature);
			temps.setMaximumAirTemperatureTime(timeInstantMaxTempProperty);
		}

		tempPropertyType.setAerodromeAirTemperatureForecast(temps);
		return tempPropertyType;

	}

	/**
	 * Creates weather section for given string code with link to WMO register url
	 */
	private AerodromeForecastWeatherType createWeatherSection(String weatherCode) {
		// <iwxxm:weather xlink:href="http://codes.wmo.int/306/4678/-SHRA"/>

		AerodromeForecastWeatherType forecastWeather = IWXXM31Helpers.ofIWXXM.createAerodromeForecastWeatherType();
		forecastWeather.setHref(iwxxmHelpers.getPrecipitationReg().getWMOUrlByCode(weatherCode));

		return forecastWeather;
	}

	/** Cloud section */
	private AerodromeCloudForecastPropertyType createCloudSectionTag(TafCommonWeatherSection weatherSection,
			String icaoCode, int sectionIndex) {
		// Envelop
		AerodromeCloudForecastPropertyType cloudsType = IWXXM31Helpers.ofIWXXM.createAerodromeCloudForecastPropertyType();

		// Body
		AerodromeCloudForecastType clouds = IWXXM31Helpers.ofIWXXM.createAerodromeCloudForecastType();
		clouds.setId(iwxxmHelpers.generateUUIDv4(String.format("acf-%d-%s", sectionIndex, icaoCode)));

		for (TAFCloudSection cloudSection : weatherSection.getCloudSections()) {

			int cloudAmount = iwxxmHelpers.getCloudReg().getCloudAmountByStringCode(cloudSection.getAmount());
			if (cloudAmount == WMOCloudRegister.missingCode) {
				JAXBElement<LengthWithNilReasonType> vVisibility = iwxxmHelpers
						.createVerticalVisibilitySection(cloudSection.getHeight());
				clouds.setVerticalVisibility(vVisibility);

			} else {
				Layer cloudLayer = IWXXM31Helpers.ofIWXXM.createAerodromeCloudTypeLayer();
				cloudLayer.setCloudLayer(iwxxmHelpers.createCloudLayerSection(cloudAmount, cloudSection.getHeight(),
						cloudSection.getType(), null, LENGTH_UNITS.FT));
				clouds.getLayer().add(cloudLayer);
			}

		}

		// Place body into envelop
		cloudsType.setAerodromeCloudForecast(clouds);
		return cloudsType;
	}

	/**
	 * Create aerodrome description section as GML FeatureOfInterest ICAO code=UUWW
	 */
	private AirportHeliportPropertyType createAirportDescriptionSectionTag() {

		return iwxxmHelpers.createAirportDescriptionSectionTag(translatedTaf.getIcaoCode());

	}

	/** Adding headers to the root */
	@Override
	public TAFType addTranslationCentreHeader(TAFType taf) throws DatatypeConfigurationException {

		taf = iwxxmHelpers.addTranslationCentreHeaders(taf, DateTime.now(), DateTime.now(),
				UUID.randomUUID().toString(), "UUWW", "Vnukovo, RU");
		taf.setTranslationFailedTAC("");

		return taf;
	}

	/**
	 * Marshall root TAF to XML with output to string
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public String marshallMessageToXML(TAFType taf) throws JAXBException, UnsupportedEncodingException {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		JAXBContext jaxbContext = JAXBContext.newInstance(TAFType.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, UriConstants.GLOBAL_SCHEMAS_LOCATION_V3);

		jaxbMarshaller.setProperty(StringConstants.SUN_JAXB_NAMESPACE_MAPPING_PROPERTY_NAME, new NamespaceMapper());

		JAXBElement<TAFType> metarRootElement = IWXXM31Helpers.ofIWXXM.createTAF(taf);

		jaxbMarshaller.marshal(metarRootElement, stream);

		return stream.toString("UTF-8");
	}
}
