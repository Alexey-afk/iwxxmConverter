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
package org.gamc.spmi.iwxxmConverter.sigmetconverter;

import java.util.regex.Pattern;




/** Regexps for parsing SIGMET
 * sigmet blocks: 
 * 	1) Location indicator of FIR(/UIR) or CTA;
	2)Name of the FIR(/UIR ) or CTA;
	3)Description of the phenomenon;
	4)Observed or Forecasted;
	5)Location of observation/forecast;
	6)Level (altitude) of the observation/forecast;
	7)Movement or Expected Movement;
	8)Changes in intensity;
	9)Forecasted position at end of SIGMET’s end of validity (optional)
 * 
 * */
public class SigmetParsingRegexp {

	/**SIGMET Mandatory header*/
	public final static Pattern sigmetHeader = Pattern.compile(
			"(?<icao>[A-Z]{4})\\s+(?<isSigmet>SIGMET)\\s+(?<sigmetNumber>(([A-Z]+)?\\s*\\d+))\\s+(?<isValid>VALID)\\s+(?<ddf>\\d\\d)(?<hhf>\\d\\d)(?<mmf>\\d\\d)\\/(?<ddt>\\d\\d)(?<hht>\\d\\d)(?<mmt>\\d\\d)\\s+(?<watchOffice>[A-Z]{4})-?\\s*(?<firCode>[A-Z]{4})?\\s+((?<firName>.+)\\s+(FIR|CTA))+(?<uir>\\/UIR)?");
	
	/**Pattern for phenomenas except VA,VC*/
	public final static Pattern sigmetPhenomena = Pattern.compile("(?<severity>ISOL|OBSC|SQL|EMBD|FRQ|SEV|HVY)\\s+(?<phenomena>.+)\\s+(?<obsfcst>OBS|FCST)\\s+(?:AT\\s+(?<atHour>\\d\\d)(?<atMinutes>\\d\\d)Z)?");
	
	/**Pattern to determine entire fir*/
	public final static Pattern sigmetEntireFir = Pattern.compile("ENTIRE FIR(?:\\/UIR)?");
	
	/**Pattern to determine polygon*/
	public final static Pattern sigmetInPolygon = Pattern.compile("(?<isInPolygon>WI)");
	
	/**Pattern to extract coordinate point*/
	public final static Pattern sigmetCoordPoint = Pattern.compile("(?<point>(?<latitude>N|S)(?<ladeg>\\d{2})(?<lamin>\\d{2})?\\s+(?<longitude>E|W)(?<lodeg>\\d{2,3})(?<lomin>\\d{2})?)");

	/**Pattern to extract lines into collection*/
	public final static Pattern sigmetLines = Pattern.compile("((N|NE|E|SE|S|SW|W|NW) OF LINE (?<pointStart>(?<latitudeStart>N|S)(?<ladegStart>\\d{2})(?<laminStart>\\d{2})?\\s+(?<longitudeStart>E|W))(?<lodegStart>\\d{2,3})(?<lominStart>\\d{2})?)\\s+(.)?\\s+(?<pointEnd>(?<latitudeEnd>N|S)(?<ladegEnd>\\d{2})(?<laminEnd>\\d{2})?\\s+(?<longitudeEnd>E|W)(?<lodegEnd>\\d{2,3})(?<lominEnd>\\d{2})?)");
	
	/**Pattern to find sectors */
	public final static Pattern sigmetSectorPooints = Pattern.compile("((N|NE|E|SE|S|SW|W|NW) OF LINE (?<pointStart>(?<latitudeStart>N|S)(?<ladegStart>\\d{2})(?<laminStart>\\d{2})?\\s+(?<longitudeStart>E|W))(?<lodegStart>\\d{2,3})(?<lominStart>\\d{2})?)\\s+(.)?\\s+(?<pointEnd>(?<latitudeEnd>N|S)(?<ladegEnd>\\d{2})(?<laminEnd>\\d{2})?\\s+(?<longitudeEnd>E|W)(?<lodegEnd>\\d{2,3})(?<lominEnd>\\d{2})?)");
	
	/**Within corridor with distance of certain line*/
	public final static Pattern sigmetWithin = Pattern.compile("(APRX|WTN)\\s+(?<range>\\d+)\\s?(KM|NM)\\s+(WID|OF)\\s+LINE\\s+(?:BTN|\\/)?");
	
	/**Whether phenomena moves or stands and intensity*/
	public final static Pattern sigmetMoves = Pattern.compile("((?<notMoving>STNR)|(MOV\\s+(?<movDirection>[A-Z]{1,3})\\s+(?<movSpeed>\\d+)(<?speedunits>KMH|KT)?))\\s+(?<force>INTSF|WKN|NC)?");
	
	public final static Pattern sigmetSector = Pattern.compile("");
	
	/**Vertical length (height) of the phenomena*/
	public final static Pattern sigmetVerticalHeight = Pattern.compile("(?<hastopfl>(?<top>TOP)\\s+(?<above>ABV)?\\s*FL(?<fl>\\d+))|(?<hassurface>(?<surface>SFC)\\/(FL(?<topfl>(\\d+))|(?<heightmeters>\\d+)M))|(?<hasbothfls>FL(?<lowfl>\\d+)\\/(?<highfl>\\d+))");
	
	
}