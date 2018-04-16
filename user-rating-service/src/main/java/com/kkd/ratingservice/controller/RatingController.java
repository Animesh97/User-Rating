package com.kkd.ratingservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kkd.ratingservice.model.CustomerModel;
import com.kkd.ratingservice.model.FarmerModel;
import com.kkd.ratingservice.repository.CustomerRating;
import com.kkd.ratingservice.repository.FarmerRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
public class RatingController {
	
	
@Autowired
CustomerRating customerRating;

@Autowired
FarmerRating farmerRating;

//Get Mapping to get data of all farmers
@ApiOperation(value = "Getting the review of all farmers")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Extraction of data completed successfully"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
}
)
@HystrixCommand(fallbackMethod="getAllFarmerFallback")
@GetMapping("/rating/farmer")
public ResponseEntity<List<FarmerModel>> getAllFarmerRatings() {
	List<FarmerModel> farmerList = farmerRating.findAll();
	return ResponseEntity.status(HttpStatus.OK).body(farmerList);
}

//Fallback Method for above mapping
public ResponseEntity<List<FarmerModel>> getAllFarmerFallback(){
	List<FarmerModel> farmerList = Arrays
            .asList(new FarmerModel("default", "default", "default", 0f , "default"));
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(farmerList);
}

//Post mapping to add new farmer's rating
@ApiOperation(value = "Adding the review of farmer")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Extraction of data completed successfully"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
}
)
@HystrixCommand(fallbackMethod="addFarmerFallback")
@PostMapping("/rating/farmer")
ResponseEntity<String> addFarmerRating(@Valid @RequestBody FarmerModel rating) {
	farmerRating.insert(rating);
	return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added");
}

//Fallback Method for above mapping
public ResponseEntity<String> addFarmerFallback(@RequestBody FarmerModel rating){
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not add");
}

//Get Mapping to retrieve data of a particular FarmerID
@ApiOperation(value = "Get the review of a single farmer")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Extraction of data completed successfully"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
}
)
@HystrixCommand(fallbackMethod="getFarmerByIdFallback")
@GetMapping("/rating/farmer/{id}")
public ResponseEntity<List<FarmerModel>> getFarmerById(@PathVariable String id)
{
	List<FarmerModel> idList=new ArrayList<FarmerModel>();
	String dbId;
	FarmerModel dbRating;
	List<FarmerModel> ratingList = (List<FarmerModel>) farmerRating.findAll();
	Iterator i = ratingList.iterator();
	while(i.hasNext())
	{
		dbRating=(FarmerModel) i.next();
		dbId=dbRating.getKkdFarmId();
		if(dbId.equals(id)) {
			idList.add(dbRating);
		}
	}
	return ResponseEntity.status(HttpStatus.OK).body(idList);
}

//Fallback Method for above mapping
public  ResponseEntity<List<FarmerModel>> getFarmerByIdFallback(@PathVariable String id){
	List<FarmerModel> idList = Arrays
            .asList(new FarmerModel("default", "default", "default", 0f, "default"));
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(idList);
}

//Customer Area starts here

//Get Mapping to get data of Customers
@ApiOperation(value = "Get the review of all the Customers")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Extraction of data completed successfully"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
}
)
@HystrixCommand(fallbackMethod="getAllCustomerFallback")
@GetMapping("/rating/customer")
public ResponseEntity<List<CustomerModel>> getAllCustomerRatings(){
	List<CustomerModel> custList= customerRating.findAll();
	return ResponseEntity.status(HttpStatus.OK).body(custList);
}

//Fallback Method for above mapping
public ResponseEntity<List<CustomerModel>> getAllCustomerFallback(){
	List<CustomerModel> custList = Arrays
            .asList(new CustomerModel("default", "default", "default", 0f , "default"));
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(custList);
}

//Post Mapping to add new  Customer Rating
@ApiOperation(value = "Add the review of a Customer")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Extraction of data completed successfully"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
}
)
@HystrixCommand(fallbackMethod="addCustomerFallback")
@PostMapping("/rating/customer")
public ResponseEntity<String> addCustomerRating(@RequestBody CustomerModel rating) {
	customerRating.insert(rating);
	return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added");
}

//Fallback Method for above mapping
public ResponseEntity<String> addCustomerFallback(@RequestBody CustomerModel rating){
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not add");
}

//Get Mapping to retrieve data of particular CustomerId
@ApiOperation(value = "Get the rating of a particular Customer")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Extraction of data completed successfully"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
}
)
@HystrixCommand(fallbackMethod="getCustomerByIdFallback")
@GetMapping("/rating/customer/{id}")
public ResponseEntity<List<CustomerModel>> getCustomerById(@PathVariable String id)
{
	List<CustomerModel> idList=new ArrayList<CustomerModel>();
	String dbId;
	CustomerModel dbRating;
	List<CustomerModel> ratingList = (List<CustomerModel>) customerRating.findAll();
	Iterator i = ratingList.iterator();
	while(i.hasNext())
	{
		dbRating=(CustomerModel) i.next();
		dbId=dbRating.getKkdCustId();
		if(dbId.equals(id)) {
			idList.add(dbRating);
		}
	}
	return ResponseEntity.status(HttpStatus.CREATED).body(idList);
}

//Fallback Method for above mapping
public ResponseEntity<List<CustomerModel>> getCustomerByIdFallback(@PathVariable String id){
	List<CustomerModel> idList = Arrays
            .asList(new CustomerModel("default", "default", "default", 0f, "default"));
	return ResponseEntity.status(HttpStatus.OK).body(idList);
}
	
}
