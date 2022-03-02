package com.alifetvaci.ReadingIsGood.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alifetvaci.ReadingIsGood.models.Order;
import com.alifetvaci.ReadingIsGood.models.OrderStatus;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>{

	Page<Order> findByCustomerId(String customerId, Pageable pageable);
	Long countByCustomerIdAndCreatedAtBetween(String customerId, Date from, Date to);
	Long countByCustomerId(String customerId);
	List<Order> findByCustomerIdAndCreatedAtBetween(String customerId, Date from, Date to);
	List<Order> findByCustomerIdAndCreatedAtBetweenAndOrderStatusIn(String customerId, Date from, Date to,OrderStatus orderStatus);
	List<Order> findByCustomerIdAndOrderStatusIn(String customerId, OrderStatus orderStatus);
}
