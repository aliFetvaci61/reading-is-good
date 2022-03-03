package com.alifetvaci.ReadingIsGood.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.ReadingIsGood.models.Order;
import com.alifetvaci.ReadingIsGood.payload.request.OrderRequest;
import com.alifetvaci.ReadingIsGood.services.IAuthenticationFacadeService;
import com.alifetvaci.ReadingIsGood.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private IAuthenticationFacadeService iAuthenticationFacadeService;

	@Autowired
	private OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest orderRequest) {

		return ResponseEntity
				.ok(orderService.createOrder(iAuthenticationFacadeService.getAuthanticatedCustomerId(), orderRequest));

	}

	@GetMapping("/order")
	public ResponseEntity<?> getOrders(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {

		return new ResponseEntity<>(
				orderService.getOrders(iAuthenticationFacadeService.getAuthanticatedCustomerId(), page, size),
				HttpStatus.OK);

	}

	@GetMapping("/order/{id}")
	public ResponseEntity<?> getOrderDetails(@PathVariable String id) {

		return new ResponseEntity<>(
				orderService.getOrderDetails(iAuthenticationFacadeService.getAuthanticatedCustomerId(), id),
				HttpStatus.OK);

	}

	@GetMapping("/order/listOrdersByDateInterval")
	public ResponseEntity<?> listOrdersByDateInterval(
			@RequestParam(value = "startDate") @DateTimeFormat(pattern = "MMddyyyy") Date startDate,
			@RequestParam(value = "endData") @DateTimeFormat(pattern = "MMddyyyy") Date endData) {

		return new ResponseEntity<>(orderService.listOrdersByDateInterval(
				iAuthenticationFacadeService.getAuthanticatedCustomerId(), startDate, endData), HttpStatus.OK);
	}

}
