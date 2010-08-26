/*
   Copyright 2007-2010 Rustici Software, LLC

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   Author(s):

   Kevin Glynn (kevin.glynn@scorm.com)
*/

package RusticiSoftware.System;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import RusticiSoftware.System.Globalization.*;
import java.util.Locale;
import RusticiSoftware.JavaSupport.util.LocaleSupport;

public class DoubleSupport {
	
	public static String ToString(double d, String format) {
		
		String formattedValue = "";
		
		if ((format.toLowerCase().indexOf("g") == 0 || format.toLowerCase().indexOf("n") == 0) && format.length() < 3) {
			
			if (format.length() == 2) {
				String lengthStr = format.substring(1);
				try {
					int len = Integer.parseInt(lengthStr);
					String decimalFormatString = "#.";
					for(int i=0; i<len; i++) {
						decimalFormatString += "0";
					}
					formattedValue = (new DecimalFormat(decimalFormatString)).format(d);
				
				} catch (NumberFormatException e) {
				
					throw new RuntimeException("DoubleSupport.ToString(double d, String format ) was called with an unsupported format of '" + format + "'");

				}
			} else {
				formattedValue = Double.toString(d);
			}
			
		} else {			
			throw new RuntimeException("DoubleSupport.ToString(double d, String format ) was called with an unsupported format of '" + format + "'");
		}
		
		return formattedValue;
	}
	
	// Force doubles to print with at least one decimal place
	public static String ToString(double d, Locale loc) {
		NumberFormat nf = NumberFormat.getInstance(loc);
		nf.setGroupingUsed(false);
		if (loc == LocaleSupport.INVARIANT)
		{
			nf.setMinimumFractionDigits(1);
			nf.setMaximumFractionDigits(7);
		}
		return nf.format(d);
	}

	public static boolean isValidDouble(String s, int style, Locale loc) throws ParseException
	{
		try
		{
			parse(s, style, loc);
			return true;
		}
		catch (ParseException e)
		{
			return false;
		}
	}

	public static double parse(String s, int style, Locale loc) throws ParseException
	{
		String toParse = s;
		
		if ((style & NumberStyles.getAllowLeadingWhite()) > 0)
			   toParse = StringSupport.TrimStart(toParse, null);

		if ((style & NumberStyles.getAllowTrailingWhite()) > 0)
			   toParse = StringSupport.TrimEnd(toParse, null);

		if ((style & NumberStyles.getAllowLeadingSign()) == 0)
			   if (toParse.charAt(0) == '+' || toParse.charAt(0) == '-')
				   throw new ParseException("Signs not allowed: " + s, 0);

		return NumberFormat.getInstance(loc == null ? LocaleSupport.INVARIANT : loc).parse(toParse).doubleValue();
		
	}
}
