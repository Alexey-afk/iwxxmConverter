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
package speci;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.ParsingException;
import speciconverter.SPECIConverter;

public class SpeciConversionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	String speci = "SPECI UUEE 270845Z 02006MPS 2100 -SN OVC009 M04/M06 Q1008 R06L/590230\n" + 
			"     R06R/590230 TEMPO 1000 SHSN BKN012CB=";
	
	@Test
	public void test() throws UnsupportedEncodingException, DatatypeConfigurationException, JAXBException, ParsingException {
		SPECIConverter sc = new SPECIConverter();
		String result = sc.convertTacToXML(speci);
		System.out.println(result);
		
		
	}

}
