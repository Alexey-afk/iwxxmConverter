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
package speciconverter;

import java.util.regex.Pattern;

/**Most of regexps are derived from METAR*/
public class SpeciParsingRegexp {
	
	
	public final static Pattern speciHeader = Pattern
			.compile("(?<header>^SPECI)\\s+((?<changeIndicator>\\w+)(?:\\s)){0,1}(?<icao>[A-Z]{4})\\s+(?<datetime>\\d{6})(?<zulu>Z){0,1}");
}
