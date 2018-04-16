package com.kkd.ratingservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//All the fields that need to be int he database
@Document
@ApiModel(description = "All details about the User rating.")
public class CustomerModel {
	
	@ApiModelProperty(notes = "Can not be empty")
	@NotNull(message = "Cant be empty")
	private String kkdCustId;
	@ApiModelProperty(notes = "Can not be empty")
	@NotNull(message = "Cant be empty")
	private String kkdFarmId;
	@ApiModelProperty(notes = "Can not be empty")
	@NotNull(message = "Cant be empty")
	private String orderId;
	@ApiModelProperty(notes = "Should be between 1 and 5")
	@Size(min=1,max=5, message="Should be between 1 and 5")
	private Float rating;
	@ApiModelProperty(notes = "Can not be empty")
	private String review;
	
	public CustomerModel() {
		super();
	}

	public CustomerModel(String kkdCustId, String kkdFarmId, String orderId, Float rating, String review) {
		super();
		this.kkdCustId = kkdCustId;
		this.kkdFarmId = kkdFarmId;
		this.orderId = orderId;
		this.rating = rating;
		this.review = review;
	}

	public String getKkdCustId() {
		return kkdCustId;
	}

	public void setKkdCustId(String kkdCustId) {
		this.kkdCustId = kkdCustId;
	}

	public String getKkdFarmId() {
		return kkdFarmId;
	}

	public void setKkdFarmId(String kkdFarmId) {
		this.kkdFarmId = kkdFarmId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "Customers [kkdCustId=" + kkdCustId + ", kkdFarmId=" + kkdFarmId + ", orderId=" + orderId + ", rating="
				+ rating + ", review=" + review + "]";
	}
	
	
	
	

}
