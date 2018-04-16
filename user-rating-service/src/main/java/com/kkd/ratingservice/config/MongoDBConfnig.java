package com.kkd.ratingservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.kkd.ratingservice.repository.CustomerRating;

@Configuration
@EnableMongoRepositories(basePackageClasses = CustomerRating.class)
public class MongoDBConfnig {
}