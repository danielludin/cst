/*******************************************************************************
 * Copyright (c) 2015, Daniel Ludin
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Daniel Ludin (ludin@hispeed.ch) - initial implementation
 *******************************************************************************/
package ch.gpb.elexis.cst.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static void doIt() {

    }

    public static double getNumericFromLabResult2(String sResult) {
	double result = -1;
	String sPattern = "[0-9\\.]*";

	if (sResult.indexOf("<5") > -1) {
	    System.out.println("");
	}

	Pattern pattern = Pattern.compile(sPattern);
	Matcher matcher = pattern.matcher(sResult);

	while (matcher.find()) {
	    String sRes = matcher.group();
	    System.out.println("sRes: " + sRes);
	}

	if (matcher.find()) {

	    String sRes = matcher.group();
	    try {
		if (sRes.indexOf("/") > -1) {
		    String[] doubleValue = sRes.split("/");
		    sRes = doubleValue[0];
		}
		result = new Double(sRes).doubleValue();
	    } catch (NumberFormatException e) {
		System.out.println("Error formatting result: " + sResult + "/" + sRes + "/" + e.getMessage());
	    }

	}
	return result;
    }

    public static double getNumericFromLabResult(String sResult) {
	double result = -1;

	String sRes = sResult.replaceAll("[^0-9?!\\.]", "");

	try {
	    result = new Double(sRes).doubleValue();
	} catch (NumberFormatException e) {
	    System.out.println("Error formatting result: " + sResult + "/" + sRes + "/" + e.getMessage());
	}
	return result;

    }

    public static void getAllNumbers() {
	// Strip non-digits, ignoring decimal point
	// TODO: this will fail with values like "62 nach 15`liegen Arm rechts"
	String bd = "62.3 nach 0.01 `liegen Arm rechts";
	bd = "69 2. Messung links";
	Pattern pattern = Pattern.compile("([0-9\\.])+");
	Matcher matcher = pattern.matcher(bd);

	ArrayList<String> values = new ArrayList<String>();

	while (matcher.find()) {
	    System.out.println("" + matcher.group());
	    values.add(matcher.group());
	}
	Collections.sort(values); // Sort the arraylist
	String maxValue = values.get(values.size() - 1); //gets the last item, largest
	System.out.println("maxValue: " + maxValue);
    }

    public static void main(String[] args) {
	// TODO Auto-generated method stub

	//double result = CstService.getNumericFromLabResult("<5");

	//RegexTest.getNumericFromLabResult("< 5");

	//	String bd = "< 5";
	//	bd = bd.replaceAll("[^0-9?!\\.]", "");

	RegexTest.getAllNumbers();
    }

}
