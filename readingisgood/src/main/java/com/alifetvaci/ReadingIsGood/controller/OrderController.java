package com.alifetvaci.ReadingIsGood.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.ReadingIsGood.exception.ForbiddenException;
import com.alifetvaci.ReadingIsGood.exception.BadRequestException;
import com.alifetvaci.ReadingIsGood.models.Book;
import com.alifetvaci.ReadingIsGood.models.Order;
import com.alifetvaci.ReadingIsGood.models.OrderStatus;
import com.alifetvaci.ReadingIsGood.payload.request.OrderBook;
import com.alifetvaci.ReadingIsGood.payload.request.OrderRequest;
import com.alifetvaci.ReadingIsGood.repository.BookRepository;
import com.alifetvaci.ReadingIsGood.repository.OrderRepository;
import com.alifetvaci.ReadingIsGood.security.services.BookStockService;
import com.alifetvaci.ReadingIsGood.security.services.CustomerDetailsService;

@RestController
@RequestMapping("/api")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CustomerDetailsService customerDetailService;
	
	@Autowired
	private BookStockService bookStockService;
	
	private final int decreaseStock = -1;

	@PostMapping("/order")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<Order> createOrder( @RequestBody @Valid OrderRequest orderRequest,
			Authentication authentication) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);

		Order order = new Order();
		order.setCustomerId(authanticationCustomerId);
		order.setOrderBooks(orderRequest.getOrderBook());
		order.setOrderStatus(OrderStatus.NEW);
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date time = cal.getTime();
		order.setCreatedAt(time);
		
		Order saved = orderRepository.save(order);

		boolean updateBookStock = bookStockService.updateBookStock(orderRequest.getOrderBook());

		if (updateBookStock) {
			Iterator<OrderBook> iterator = order.getOrderBooks().iterator();
			double purchase = 0 ; 
			while(iterator.hasNext()) {
				OrderBook orderBook = iterator.next();
				Book book = bookRepository.findById(orderBook.getBook()).orElse(null);
				if(book!=null) {
					 purchase = purchase + (book.getPrice() * orderBook.getNumber());
				}
			}
			saved.setPurchase(purchase);
			saved.setOrderStatus(OrderStatus.COMPLETED);
		} else {
			saved.setOrderStatus(OrderStatus.CANCELED);
		}
		saved.setUpdatedAt(new Date());
		logger.info("Order Created");
		orderRepository.save(saved);

		return ResponseEntity.ok(saved);

	}

	@GetMapping("/order")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> getOrders(Authentication authentication, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {

		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);
		List<Order> orders = new ArrayList<Order>();
		Pageable paging = PageRequest.of(page, size);
		Page<Order> pageOrders = orderRepository.findByCustomerId(authanticationCustomerId, paging);
		orders = pageOrders.getContent();
		Map<String, Object> response = new HashMap<>();
		response.put("orders", orders);
		response.put("currentPage", pageOrders.getNumber());
		response.put("totalItems", pageOrders.getTotalElements());
		response.put("totalPages", pageOrders.getTotalPages());
		logger.info("get Orders");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/order/{id}")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> getOrderDetails(Authentication authentication, @PathVariable String id) {

		Order order = orderRepository.findById(id).orElse(null);
		if(order==null) {
			logger.info("Not found order id : " + id);
			throw new BadRequestException("Not found order id : " + id);
		}
		if (customerDetailService.checkFraud(authentication, order.getCustomerId())) {
			logger.info("get Order Details");
			return new ResponseEntity<>(order, HttpStatus.OK);
		} else {
			logger.info("Forbidden order Fraud");
			throw new ForbiddenException("Forbidden order Fraud");
		}

	}
	
	@GetMapping("/order/listOrdersByDateInterval")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> listOrdersByDateInterval(Authentication authentication,
			@RequestParam(value = "startDate") @DateTimeFormat(pattern = "MMddyyyy") Date startDate,
			@RequestParam(value = "endData") @DateTimeFormat(pattern = "MMddyyyy") Date endData) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);
		List<Order> findByCustomerIdAndCreatedAtBetween = orderRepository.findByCustomerIdAndCreatedAtBetween(authanticationCustomerId, startDate, endData);
		logger.info("List orders by date interval");
		return new ResponseEntity<>(findByCustomerIdAndCreatedAtBetween, HttpStatus.OK);
	}

}
