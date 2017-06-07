/**
 * Different tests for RESTApi.
 */
package com.example.tests;

import java.io.File;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.get;
import static org.junit.Assert.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Galina
 *
 */
public class RestAPITest {
	
	/**
	 * Test for expected HTTP code of the response. This one tests 200.
	 * For testing other codes, copy-paste and code adjustment in the assert clause is required.
	 * Test passes for responses with the code 200 (OK) and fails for other.
	 * @param url = request URL
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void verifyCorrectRequest(String url) throws ClientProtocolException, IOException{
		
		HttpUriRequest request = new HttpGet(url);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		try{
		assertEquals(
			     response.getStatusLine().getStatusCode(),
			     HttpStatus.SC_OK);
		}catch(AssertionError ae)
				{	System.out.println("Status code is NOT 200 as expected.");
					ae.printStackTrace();
					return;}
		
		System.out.println("Status code is 200 as expected.");
		
	}

	/**
	 * Test for expected HTTP code of the response. This one tests 400.
	 * Test passes for responses with the code 400 (bad request) and fails for other.
	 * @param url = request URL
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void verifyBadRequest(String url) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(url);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		try{
		assertEquals(
			     response.getStatusLine().getStatusCode(),
			     HttpStatus.SC_BAD_REQUEST);
		}catch(AssertionError ae)
		{	System.out.println("Status code is NOT 400 as expected.");
			ae.printStackTrace();
			return;}
		
		System.out.println("Status code is 400 as expected.");
		
	}
	
	
	/**
	 * Test for verifying expected media type. This one passes for application/json.
	 * To check other media types, copy-paste and adjust the mime type variable.
	 * @param url = request URL
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void verifyMediaTypeJson(String url) throws ClientProtocolException, IOException{
		String jsonMimeType = "application/json";
		HttpUriRequest request = new HttpGet(url);
		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
		
		try{
		assertEquals(jsonMimeType, mimeType );
		}catch(AssertionError ae)
		{	System.out.println("Media type is NOT application/json as expected.");
			ae.printStackTrace();
			return;}
		
		System.out.println("Media type is application/json as expected.");
	}
	
	/**
	 * Test to check values of specific fields in the response.
	 * It searches for the given key under the given path and compares the values 
	 * (in this implementation only matches String to String).
	 * @param url = request URL
	 * @param path = path to the "key" in JSON response
	 * @param key = key in JSON response
	 * @param expected = expected value of the "key" in JSON response
	 * @throws IOException
	 */
	public void verifyResponseFieldValue(String url, String path, String key, String expected) throws IOException{
		//using rest-assure library
		Response response = get(url);

		ObjectMapper mapper = new ObjectMapper();
		//using jackson library
		JsonNode node = mapper.readTree(response.asString());
		String actual = node.at(path).findValue(key).asText();
		
		try{
		assertEquals(expected, actual);
		}catch(AssertionError ae)
		{	System.out.println("The value is not as expected for the key: " + key);
			ae.printStackTrace();
			return;}
		
		System.out.println("The value is as expected for the key: " + key);	
	}
	
	/**
	 * Test to verify if the response schema matches the expected JSON schema
	 * @param url = request URL
	 * @param file = json schema file
	 */
	public void verifyResponseJsonSchema(String url, File file){
		//using rest-assure library
		Response response = get(url);		 
		
		try{
		assertThat(response.asString(), matchesJsonSchema(file));
		}catch(AssertionError ae)
		{	System.out.println("The response schema doesn't match the expected schema.");
			ae.printStackTrace();
			return;}
		
		System.out.println("The response schema matches the expected schema.");
	}
}
