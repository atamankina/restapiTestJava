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
import static org.junit.Assert.*;

/**
 * @author atamanki
 *
 */
public class restapiTest {
	
	public void testCorrectRequest(String url) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(url);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		assertEquals(
			     httpResponse.getStatusLine().getStatusCode(),
			     HttpStatus.SC_OK);
		
	}

	public void testBadRequest(String url) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(url);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		assertEquals(
			     httpResponse.getStatusLine().getStatusCode(),
			     HttpStatus.SC_BAD_REQUEST);
		
		
	}
	
	public void testNotFoundRequest(String url) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(url);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		assertEquals(
			     httpResponse.getStatusLine().getStatusCode(),
			     HttpStatus.SC_NOT_FOUND);
		
	}
	
	public void testMediaTypeJson(String url) throws ClientProtocolException, IOException{
		String jsonMimeType = "application/json";
		HttpUriRequest request = new HttpGet( "https://api.github.com/users/eugenp" );
		 
		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		 
		String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
		assertEquals(jsonMimeType, mimeType );
		
	}
}
