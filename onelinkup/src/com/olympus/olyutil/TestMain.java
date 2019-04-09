package com.olympus.olyutil;

import java.util.HashMap;
import java.util.Map;
 

public class TestMain {
	/*************************************************************************************************************************************************************/
	static String propFile = "C:\\Java_Dev\\props\\connectionRapport.prop";
	/*************************************************************************************************************************************************************/
	public static void main(String[] args) {
		String result = null;
		String j1 = " { \"message\": \"Session expired or invalid\",  \"errorCode\": \"INVALID_SESSION_ID\"}  "; 
 
		/*
		Map<String, String> propMap = new HashMap();
		propMap = Olyutil.getProperties(propFile);		
		Olyutil.printHashMap(propMap);
		*/
		try {
			if (JsonUtil.isJSONValid(j1)) {
				System.out.println("******* is Valid JSON");
				JsonUtil.getJsonKeyVal(j1);
			} else {
				System.out.println("******* is NOT Valid JSON");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	} // End main
	/*************************************************************************************************************************************************************/
} 