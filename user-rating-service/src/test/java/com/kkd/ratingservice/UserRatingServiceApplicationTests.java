//Author: Animesh Pandey, Lokesh Rishi

package com.kkd.ratingservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kkd.ratingservice.controller.RatingController;
import com.kkd.ratingservice.model.CustomerModel;
import com.kkd.ratingservice.model.FarmerModel;
import com.kkd.ratingservice.repository.CustomerRating;
import com.kkd.ratingservice.repository.FarmerRating;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RatingController.class)
public class UserRatingServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	FarmerRating farmerRating;
	@MockBean
	CustomerRating customerRating;

	FarmerModel rating = new FarmerModel("aa", "bb", "cc", 3.14f, "dd");
	CustomerModel customerObject = new CustomerModel("aa", "bb", "cc", 3.14f, "dd");

	// Function to Test Get Mapping of Farmer
	@Test
	public void testGetMappingFarmer() {
		List<FarmerModel> mockList = new ArrayList<FarmerModel>();
		mockList.add(rating); // List to be returned from mapping
		
		Mockito.when(farmerRating.findAll()).thenReturn(mockList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rating/farmer").accept(MediaType.APPLICATION_JSON);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.get("/rating/farmer/bb")
				.accept(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

			// String which we expect to be returned after a successful mapping
			String expected = "[{\"kkdCustId\":\"aa\",\"kkdFarmId\":\"bb\",\"orderId\":\"cc\",\"rating\":3.14,\"review\":\"dd\"}]";
			JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
			JSONAssert.assertEquals(expected, result2.getResponse().getContentAsString(), false);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// Function to test Post Mapping of Farmer
	@Test
	public void testPostMappingFarmer() {
		// Content to be passed along with Post request
		String exampleRating = "{\"kkdCustId\":\"aa\",\"kkdFarmId\":\"bb\",\"orderId\":\"cc\",\"rating\":3.14,\"review\":\"dd\"}";
		try {
			Mockito.when(farmerRating.insert(Mockito.any(FarmerModel.class))).thenReturn(null);
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rating/farmer")
					.accept(MediaType.APPLICATION_JSON).content(exampleRating).contentType(MediaType.APPLICATION_JSON);
			MvcResult result;
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			// Check for the response status code
			assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Customer Test Cases
	
	//Function to test get mapping of Customer
	@Test
	public void testGetMappingCustomer() {
		List<CustomerModel> mockList = new ArrayList<CustomerModel>();
		mockList.add(customerObject); // List to be returned from mapping
		Mockito.when(customerRating.findAll()).thenReturn(mockList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rating/customer").accept(MediaType.APPLICATION_JSON);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.get("/rating/customer/aa")
				.accept(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

			// String which we expect to be returned after a successful mapping
			String expected = "[{\"kkdCustId\":\"aa\",\"kkdFarmId\":\"bb\",\"orderId\":\"cc\",\"rating\":3.14,\"review\":\"dd\"}]";
			JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
			JSONAssert.assertEquals(expected, result2.getResponse().getContentAsString(), false);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Function to test Post mapping of Customer
	@Test
	public void testPostMappingCustomer() {
		String exampleRating = "{\"kkdCustId\":\"aa\",\"kkdFarmId\":\"bb\",\"orderId\":\"cc\",\"rating\":3.14,\"review\":\"dd\"}";
		try {
			Mockito.when(customerRating.insert(Mockito.any(CustomerModel.class))).thenReturn(null);
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rating/farmer")
					.accept(MediaType.APPLICATION_JSON).content(exampleRating).contentType(MediaType.APPLICATION_JSON);
			MvcResult result;
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			// Check for the response status code
			assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
