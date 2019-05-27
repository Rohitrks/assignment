package com.mytaxi.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtilities {

	/*
	 * To read the data from property file
	 * @param String Key
	 */
	public static String getValueForKey(String readKey)
	{
		String baseDir = Matcher.quoteReplacement(new File("").getAbsolutePath());
		System.setProperty("baseDir", baseDir.replace("\\\\", "/"));
		String configFilePath = "/src/test/resources/config/qa.properties";

		Properties prop = new Properties();
		InputStream input = null;
		String returnValue =  "";

		try {
			input = new FileInputStream(System.getProperty("baseDir") + configFilePath);
			prop.load(input);
			returnValue = prop.getProperty(readKey);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return returnValue;
	}

	/*
	 * This will verify if email in the String is true or false
	 */
	public boolean validateEmailFormat(String email){
		Boolean isEmailValid=false;
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher((CharSequence) email);
		isEmailValid=matcher.matches();
		return isEmailValid;
	}

}
