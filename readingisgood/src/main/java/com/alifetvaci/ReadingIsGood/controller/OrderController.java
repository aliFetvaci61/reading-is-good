package com.alifetvaci.ReadingIsGood.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.ReadingIsGood.models.Order;
import com.alifetvaci.ReadingIsGood.payload.request.OrderRequest;
import com.alifetvaci.ReadingIsGood.services.CustomerDetailsService;
import com.alifetvaci.ReadingIsGood.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private CustomerDetailsService customerDetailService;

	@Autowired
	private OrderService orderService;

	@PostMapping("/order")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest orderRequest,
			Authentication authentication) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);

		return ResponseEntity.ok(orderService.createOrder(authanticationCustomerId, orderRequest));

	}

	@GetMapping("/order")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> getOrders(Authentication authentication, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {

		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);

		return new ResponseEntity<>(orderService.getOrders(authanticationCustomerId, page, size), HttpStatus.OK);

	}

	@GetMapping("/order/{id}")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> getOrderDetails(Authentication authentication, @PathVariable String id) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);

		return new ResponseEntity<>(orderService.getOrderDetails(authanticationCustomerId, id), HttpStatus.OK);

	}

	@GetMapping("/order/listOrdersByDateInterval")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> listOrdersByDateInterval(Authentication authentication,
			@RequestParam(value = "startDate") @DateTimeFormat(pattern = "MMddyyyy") Date startDate,
			@RequestParam(value = "endData") @DateTimeFormat(pattern = "MMddyyyy") Date endData) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);

		return new ResponseEntity<>(orderService.listOrdersByDateInterval(authanticationCustomerId, startDate, endData),
				HttpStatus.OK);
	}

}
