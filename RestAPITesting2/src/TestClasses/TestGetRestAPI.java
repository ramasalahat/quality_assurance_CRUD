package TestClasses;

import static org.junit.Assert.*;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import requestHandling.HandleRequestReponse;
import requestHandling.RestClientHandler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestGetRestAPI {

	
	@Test
	public void case1TestRestClientHandler() throws IOException {
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		// 2. validate if the connection is successfully opened
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 200);
		// 3. reading response using input stream
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		assertTrue("Data is not empty", !response.equals(""));
	}
	
	@Test
	public void case2testGetAnExistingResource() throws Exception {
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud + "/608f0f1113120c03e81c998e", HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		// 2. validate if the connection is successfully opened
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("record exists", connection.getResponseCode() == 200);
		// 3. reading response using input stream
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		assertTrue("Data is empty", !response.equals(""));

	}
	
	@Test(timeout = 10000)
	public void case3testGetANonExistingResource() throws Exception {
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud + "/1", HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		// 2. validate if the connection is successfully opened
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("record doesn't exist", connection.getResponseCode() == 404);
	}
	
	@Test
	public void case4testGetInvalidIdResource() throws Exception {
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud + "/r", HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		// 2. validate if the connection is successfully opened
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("record doesn't exist", connection.getResponseCode() == 404);
	}

	@Test
	public void case5testCreateValidResource() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.createResourceJSONFILE);
		// 3. Post Request
		RestClientHandler.sendPost(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		// 4. Reading Response
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		// 5. convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		
		String resourceId = jsonObject.get("_id").toString();
		System.out.println(URLs.crudcrud + "/" + resourceId);
		// 6. validation data jsonObject==response
		// (https://restful-booker.herokuapp.com/booking/id)
		HttpURLConnection connection2 = RestClientHandler.connectServer(URLs.crudcrud + "/" + resourceId , HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		System.out.println("connection.getResponseCode() : " + connection2.getResponseCode());
		String response2 = RestClientHandler.readResponse(connection2);
		System.out.println(response2);
		JSONObject jsonObject2 = (JSONObject) JSONUtils.convertStringToJSON(response2);

		assertTrue("validated", jsonObject.equals(jsonObject2));

	}
	
	@Test
	public void case6testCreateMissingValuesResource() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.createMissingValuesResourceJSONFILE);
		
		// 3. Post Request with missing name
		RestClientHandler.sendPost(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		// 4. Reading Response
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		// 5. convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		
		String resourceId = jsonObject.get("_id").toString();
		System.out.println(URLs.crudcrud + "/" + resourceId);
		// 6. validation data jsonObject==response
		// (https://restful-booker.herokuapp.com/booking/id)
		HttpURLConnection connection2 = RestClientHandler.connectServer(URLs.crudcrud + "/" + resourceId , HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		System.out.println("connection.getResponseCode() : " + connection2.getResponseCode());
		String response2 = RestClientHandler.readResponse(connection2);
		System.out.println(response2);

		JSONObject jsonObject2 = (JSONObject) JSONUtils.convertStringToJSON(response2);

		assertTrue("validated", jsonObject.equals(jsonObject2));

	}
	
	@Test
	public void case7testCreateDifferentParamsResource() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.createDifferentParamsResourceJSONFILE);
		
		// 3. Post Request with missing name
		RestClientHandler.sendPost(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		// 4. Reading Response
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		// 5. convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		
		String resourceId = jsonObject.get("_id").toString();
		System.out.println(URLs.crudcrud + "/" + resourceId);
		// 6. validation data jsonObject==response
		// (https://restful-booker.herokuapp.com/booking/id)
		HttpURLConnection connection2 = RestClientHandler.connectServer(URLs.crudcrud + "/" + resourceId , HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		System.out.println("connection.getResponseCode() : " + connection2.getResponseCode());
		String response2 = RestClientHandler.readResponse(connection2);
		System.out.println(response2);

		JSONObject jsonObject2 = (JSONObject) JSONUtils.convertStringToJSON(response2);

		assertTrue("validated", jsonObject.equals(jsonObject2));

	}

	@Test
	public void case8testCreateEmptyResource() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// 3. Post Request with missing name
		RestClientHandler.sendPost(connection, "{}", HTTPRequestsContentTypes.JSON);
		// 4. Reading Response
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		// 5. convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		
		String resourceId = jsonObject.get("_id").toString();
		System.out.println(URLs.crudcrud + "/" + resourceId);
		// 6. validation data jsonObject==response
		// (https://restful-booker.herokuapp.com/booking/id)
		HttpURLConnection connection2 = RestClientHandler.connectServer(URLs.crudcrud + "/" + resourceId , HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		System.out.println("connection.getResponseCode() : " + connection2.getResponseCode());
		String response2 = RestClientHandler.readResponse(connection2);
		System.out.println(response2);

		JSONObject jsonObject2 = (JSONObject) JSONUtils.convertStringToJSON(response2);

		assertTrue("validated", jsonObject.equals(jsonObject2));

	}
	
	
	@Test
	public void case9testUpdateResource() throws Exception {
		// create a new resource to be updated and get its id
		HttpURLConnection connection2 = RestClientHandler.connectServer(URLs.crudcrud, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		String resquestJSONObject2 = JSONUtils.readJSONObjectFromFile(FilesPaths.createResourceJSONFILE);
				RestClientHandler.sendPost(connection2, resquestJSONObject2, HTTPRequestsContentTypes.JSON);
		String response2 = RestClientHandler.readResponse(connection2);
		System.out.println(response2);
		JSONObject jsonObject2 = (JSONObject) JSONUtils.convertStringToJSON(response2);
		String id = jsonObject2.get("_id").toString();
		
		// create the connection to change the record
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud +"/" + id, HTTPMethod.PUT,
				HTTPRequestsContentTypes.JSON);
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.updateResource);
		// 3. Put Request
		RestClientHandler.sendPut(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		// 4. Reading Response
		System.out.println(connection.getResponseCode());
		assertTrue("request sent", connection.getResponseCode() == 200);

		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);

		// get the new record
		HttpURLConnection connection3 = RestClientHandler.connectServer(URLs.crudcrud + '/' + id, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		String response3 = RestClientHandler.readResponse(connection3);
		System.out.println(response3);
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response3);

		
		assertTrue("validated", !jsonObject.equals(jsonObject2));

	}
	
	@Test
	public void caseAtestUpdateResourceWithEmpty() throws Exception {
		// create a new resource to be updated and get its id
		HttpURLConnection connection2 = RestClientHandler.connectServer(URLs.crudcrud, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		String resquestJSONObject2 = JSONUtils.readJSONObjectFromFile(FilesPaths.createResourceJSONFILE);
				RestClientHandler.sendPost(connection2, resquestJSONObject2, HTTPRequestsContentTypes.JSON);
		String response2 = RestClientHandler.readResponse(connection2);
		System.out.println(response2);
		JSONObject jsonObject2 = (JSONObject) JSONUtils.convertStringToJSON(response2);
		String id = jsonObject2.get("_id").toString();
		
		// create the connection to change the record
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.crudcrud +"/" + id, HTTPMethod.PUT,
				HTTPRequestsContentTypes.JSON);
		// 2. Prepare Json Object
		String resquestJSONObject = "{}";
		// 3. Put Request
		RestClientHandler.sendPut(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		// 4. Reading Response
		System.out.println(connection.getResponseCode());
		assertTrue("request sent", connection.getResponseCode() == 200);

		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);

		// get the new record
		HttpURLConnection connection3 = RestClientHandler.connectServer(URLs.crudcrud + '/' + id, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		String response3 = RestClientHandler.readResponse(connection3);
		System.out.println(response3);
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response3);

		
		assertTrue("validated", !jsonObject.equals(jsonObject2));

	}
	

}
