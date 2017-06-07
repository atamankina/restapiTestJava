/**
 * 
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
		
		RestAPITest rt = new RestAPITest();
		
		rt.testCorrectRequest(correct_url);
		rt.testCorrectRequest(url_noproduct);
		rt.verifyResponseFieldValue(correct_url, "/observations/location", "city", "Berlin");
	
	
	}

}
