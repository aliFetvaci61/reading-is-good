package com.alifetvaci.ReadingIsGood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alifetvaci.ReadingIsGood.models.Log;

public interface LogRepository extends MongoRepository<Log, String>{

}
