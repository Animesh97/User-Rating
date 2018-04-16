package com.kkd.ratingservice;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkd.ratingservice.model.CustomerModel;
import com.kkd.ratingservice.model.FarmerModel;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=RatingService.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRatingServiceIntegrationTest {

	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate=new TestRestTemplate();
	HttpHeaders headers=new HttpHeaders();
	
	//Function to test get farmer by ID
	@Test
	public void testGetFamrerMapping() {
		try {
			HttpEntity<String> entity=new HttpEntity<String>(null,headers);
			ResponseEntity<String> response=restTemplate.exchange(createURLWithPort("/rating/farmer/string"),HttpMethod.GET,entity,String.class);
			String expected="[{\"kkdCustId\":\"string\",\"kkdFarmId\":\"string\",\"orderId\":\"string\",\"rating\":5,\"review\":\"string\"}]";
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Function to test POST mapping of farmer
	@Test
	public void testPostFarmerMapping() {
		FarmerModel farmer=new FarmerModel("Customer","Farmer","Order",5f,"Review");
		HttpEntity<FarmerModel> entity=new HttpEntity<FarmerModel>(farmer,headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/rating/farmer"),HttpMethod.POST, entity, String.class);
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
	}
	
	//Integration test cases for Customer
	
	//Function to test get Customer by ID
	@Test
	public void testGetFamerMapping() {
		try {
			HttpEntity<String> entity=new HttpEntity<String>(null,headers);
			ResponseEntity<String> response=restTemplate.exchange(createURLWithPort("/rating/customer/string"),HttpMethod.GET,entity,String.class);
			String expected="[{\"kkdCustId\":\"string\",\"kkdFarmId\":\"string\",\"orderId\":\"string\",\"rating\":5,\"review\":\"string\"}]";
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Function to test POST mapping of farmer
		@Test
		public void testPostCustomerMapping() {
			CustomerModel customer=new CustomerModel("Customer","Farmer","Order",5f,"Review");
			HttpEntity<CustomerModel> entity=new HttpEntity<CustomerModel>(customer,headers);
			ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/rating/customer"),HttpMethod.POST, entity, String.class);
			assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
		}
		
		//Function to return Complete URI
		private String createURLWithPort(String uri) {
			return "http://localhost:" + port + uri;
		}
}
