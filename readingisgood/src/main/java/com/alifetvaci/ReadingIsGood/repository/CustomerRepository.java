package com.alifetvaci.ReadingIsGood.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alifetvaci.ReadingIsGood.models.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
	
	Optional<Customer> findByEmail(String email);

	Boolean existsByEmail(String email);
}
