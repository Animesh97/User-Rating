package com.kkd.ratingservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkd.ratingservice.model.FarmerModel;


//Repository for MongoDB
public interface FarmerRating extends MongoRepository<FarmerModel , String> {
}
