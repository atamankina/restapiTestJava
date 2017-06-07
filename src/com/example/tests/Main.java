/**
 * Running tests for RESTApi.
 * The tested scenarios and results are shown in the console.
 */
package com.example.tests;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

/**
 * @author Galina
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//Input
		String correct_url = 
				"https://weather.cit.api.here.com/weather/1.0/report.json?product=observation&name=Barcelona&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg";
		String url_noproduct = 
				"https://weather.cit.api.here.com/weather/1.0/report.json?name=Barcelona&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg";
		String url_nocity = 
				"https://weather.cit.api.here.com/weather/1.0/report.json?product=observation&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg";
		String url_noappid =
				"https://weather.cit.api.here.com/weather/1.0/report.json?product=observation&name=Barcelona&app_code=AJKnXv84fjrb0KIHawS0Tg";
		String url_noappcode = 
				"https://weather.cit.api.here.com/weather/1.0/report.json?product=observation&name=Barcelona&app_id=DemoAppId01082013GAL";
		String url_othermime = "https://www.google.com/";
		String schema_classpath = "weather_schema.json";
		String wrong_schema = "wrong_schema.json";
		
		//Tests
		RestAPITest rt = new RestAPITest();
		System.out.println("Verify response status code 200...");
		System.out.println("Test result for the correct request: " + correct_url);
		rt.verifyCorrectRequest(correct_url);
		System.out.println("Test result for the incorrect request: " + url_nocity);
		rt.verifyCorrectRequest(url_nocity);
		
		System.out.println("Verify response status code 400...");
		System.out.println("Test result for the correct request:");
		rt.verifyBadRequest(correct_url);
		System.out.println("Test result for the incorrect request:");
		rt.verifyBadRequest(url_nocity);
		
		System.out.println("Verify response media type...");
		System.out.println("Test result for the matching media type:");
		rt.verifyMediaTypeJson(correct_url);
		System.out.println("Test result for the not matching media type:");
		rt.verifyMediaTypeJson(url_othermime);
		
		System.out.println("Verify expected values for specific attributes...");
		System.out.println("Test results for correct values:");
		rt.verifyResponseFieldValue(correct_url, "/observations/location", "city", "Barcelona");
		rt.verifyResponseFieldValue(correct_url, "/observations/location", "country", "Spain");
		System.out.println("Test results for incorrect values:");
		rt.verifyResponseFieldValue(correct_url, "/observations/location", "city", "Berlin");
		rt.verifyResponseFieldValue(correct_url, "/observations/location", "country", "Germany");
		
		System.out.println("Verify JSON schema of the response...");
		System.out.println("Test results for the correct JSON schema:");
		rt.verifyResponseJsonSchema(correct_url, schema_classpath);
		System.out.println("Test results for the incorrect JSON schema:");
		rt.verifyResponseJsonSchema(correct_url, wrong_schema);
		
		System.out.println("Tests done.");
	
	
	}

}
