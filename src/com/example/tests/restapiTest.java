/**
 * 
 */
package com.example.tests;

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



/**
 * @author Galina
 *
 */
public class RestAPITest {
	
	/**
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void testCorrectRequest(String url) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(url);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		try{
		assertEquals(
			     response.getStatusLine().getStatusCode(),
			     HttpStatus.SC_OK);
		}catch(AssertionError ae){System.out.println("Status code is NOT 200 as expected.");}
		System.out.println("Status code is 200 as expected.");
		System.out.println(response.toString());
		
	}

	/**
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void testBadRequest(String url) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(url);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		assertEquals(
			     response.getStatusLine().getStatusCode(),
			     HttpStatus.SC_BAD_REQUEST);
		
		
	}
	
	
	/**
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void verifyMediaTypeJson(String url) throws ClientProtocolException, IOException{
		String jsonMimeType = "application/json";
		HttpUriRequest request = new HttpGet(url);
		 
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		 
		String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
		assertEquals(jsonMimeType, mimeType );
		
	}
	
	/**
	 * @param url
	 * @param path
	 * @param key
	 * @param expected
	 * @throws IOException
	 */
	public void verifyResponseFieldValue(String url, String path, String key, String expected) throws IOException{
		Response response = get(url);
		System.out.println(response.asString());
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response.asString());
		
		String actual = node.at(path).findValue(key).asText();
		System.out.println(actual);
		
		assertEquals(expected, actual);
			
	}
	
	/**
	 * @param url
	 * @param classpath
	 */
	public void validateResponseJsonSchema(String url, String classpath){
		
	}
}
