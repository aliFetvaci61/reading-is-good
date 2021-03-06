package com.alifetvaci.ReadingIsGood.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alifetvaci.ReadingIsGood.exception.BadRequestException;
import com.alifetvaci.ReadingIsGood.exception.ForbiddenException;
import com.alifetvaci.ReadingIsGood.models.Book;
import com.alifetvaci.ReadingIsGood.models.LogType;
import com.alifetvaci.ReadingIsGood.models.Order;
import com.alifetvaci.ReadingIsGood.models.OrderStatus;
import com.alifetvaci.ReadingIsGood.payload.request.OrderBook;
import com.alifetvaci.ReadingIsGood.payload.request.OrderRequest;
import com.alifetvaci.ReadingIsGood.repository.BookRepository;
import com.alifetvaci.ReadingIsGood.repository.OrderRepository;
import com.alifetvaci.ReadingIsGood.services.BookService;
import com.alifetvaci.ReadingIsGood.services.LogService;
import com.alifetvaci.ReadingIsGood.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	private BookService bookStockService;
	
	@Autowired
	private LogService logService;

	@Override
	public Order createOrder(String customerId, OrderRequest orderRequest) {
		Order order = new Order();
		order.setCustomerId(customerId);
		order.setOrderBooks(orderRequest.getOrderBook());
		order.setOrderStatus(OrderStatus.NEW);
		
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		order.setCreatedAt(time);
		
		Order saved = orderRepository.save(order);
		Order order1 =  new Order(saved.getId(),saved.getCustomerId(),saved.getOrderBooks(),saved.getOrderStatus(),saved.getCreatedAt(),saved.getUpdatedAt(),saved.getPurchase());
		
		logService.insertLog(customerId,LogType.NEW_ENTITY, saved.toString(), null);
		
		boolean updateBookStock = bookStockService.updateBookStockOrderCreate(customerId,orderRequest.getOrderBook());

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
		Order updated = orderRepository.save(saved);
		logService.insertLog(customerId,LogType.NEW_ENTITY, updated.toString(), order1.toString());
		return updated;
	}

	@Override
	public Map<String, Object> getOrders(String CustomerId, int page, int size) {
		List<Order> orders = new ArrayList<Order>();
		Pageable paging = PageRequest.of(page, size);
		Page<Order> pageOrders = orderRepository.findByCustomerId(CustomerId, paging);
		orders = pageOrders.getContent();
		Map<String, Object> response = new HashMap<>();
		response.put("orders", orders);
		response.put("currentPage", pageOrders.getNumber());
		response.put("totalItems", pageOrders.getTotalElements());
		response.put("totalPages", pageOrders.getTotalPages());
		logger.info("get Orders");
		return response;
	}

	@Override
	public Order getOrderDetails(String CustomerId, String id) {
		Order order = orderRepository.findById(id).orElse(null);
		if (order == null) {
			logger.info("Not found order id : " + id);
			throw new BadRequestException("Not found order id : " + id);
		}
		if (CustomerId.contains(order.getCustomerId())) {
			logger.info("get Order Details");
			return order;
		} else {
			logger.info("Forbidden order Fraud");
			throw new ForbiddenException("Forbidden order Fraud");
		}
	}

	@Override
	public List<Order> listOrdersByDateInterval(String CustomerId, Date startDate, Date endData) {

		List<Order> findByCustomerIdAndCreatedAtBetween = orderRepository
				.findByCustomerIdAndCreatedAtBetween(CustomerId, startDate, endData);
		logger.info("List orders by date interval");
		return findByCustomerIdAndCreatedAtBetween;
	}

}
