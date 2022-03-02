package com.alifetvaci.ReadingIsGood.controller;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alifetvaci.ReadingIsGood.models.Order;
import com.alifetvaci.ReadingIsGood.models.OrderStatus;
import com.alifetvaci.ReadingIsGood.models.TotalCountOfPurchasedBook;
import com.alifetvaci.ReadingIsGood.payload.response.OrderCount;
import com.alifetvaci.ReadingIsGood.payload.response.OrdersAmount;
import com.alifetvaci.ReadingIsGood.repository.OrderRepository;
import com.alifetvaci.ReadingIsGood.security.services.CustomerDetailsService;

@Controller
@RequestMapping("/api")
public class StatisticsController {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerDetailsService customerDetailService;

	@GetMapping("/statistics/order-count")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> getTotalOrderCount(Authentication authentication) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date oneMonthAgo = cal.getTime();

		Long countByCustomerIdAndCreatedAtBetween = orderRepository
				.countByCustomerIdAndCreatedAtBetween(authanticationCustomerId, oneMonthAgo, now);
		OrderCount orderCount = new OrderCount();
		orderCount.setCount(countByCustomerIdAndCreatedAtBetween);
		logger.info("get Total Order Count");
		return ResponseEntity.ok(orderCount);

	}

	@GetMapping("/statistics/purchased-amount")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> getPurchasedAmount(Authentication authentication) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date oneMonthAgo = cal.getTime();

		List<Order> findByCustomerIdInAndCreatedAtBetweenAndOrderStatusIn = orderRepository
				.findByCustomerIdAndCreatedAtBetweenAndOrderStatusIn(authanticationCustomerId, oneMonthAgo, now,
						OrderStatus.COMPLETED);
		double totalPurchase = findByCustomerIdInAndCreatedAtBetweenAndOrderStatusIn.stream()
				.mapToDouble(f -> f.getPurchase()).sum();

		OrdersAmount orderAmount = new OrdersAmount();
		orderAmount.setAmount(totalPurchase);
		logger.info("get Total Purchased Amount");
		return ResponseEntity.ok(orderAmount);

	}

	@GetMapping("/statistics/purchased-books")
	@PreAuthorize("#authentication == authentication")
	public String getPurchasedBooks(Authentication authentication, Model model) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);

		Calendar calendar = Calendar.getInstance();

		List<Order> findByCustomerIdAndOrderStatusIn = orderRepository
				.findByCustomerIdAndOrderStatusIn(authanticationCustomerId, OrderStatus.COMPLETED);
		HashMap<String, TotalCountOfPurchasedBook> map = new HashMap<>();
		Iterator<Order> iterator = findByCustomerIdAndOrderStatusIn.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			calendar.setTime(order.getCreatedAt());
			Month month = Month.of(calendar.get(Calendar.MONTH) + 1);

			TotalCountOfPurchasedBook totalCountOfPurchasedBook = map.get(month.toString());
			if (totalCountOfPurchasedBook == null) {
				TotalCountOfPurchasedBook totalCountOfPurchasedBook2 = new TotalCountOfPurchasedBook();
				totalCountOfPurchasedBook2.setMonth(month.toString());
				totalCountOfPurchasedBook2.setTotalBookCount(order.getOrderBooks().size());
				totalCountOfPurchasedBook2.setTotalOrderCount(1);
				totalCountOfPurchasedBook2.setTotalPuchasedAmount(order.getPurchase());
				map.put(month.toString(), totalCountOfPurchasedBook2);
			} else {
				totalCountOfPurchasedBook.setTotalBookCount(
						totalCountOfPurchasedBook.getTotalBookCount() + order.getOrderBooks().size());
				totalCountOfPurchasedBook.setTotalOrderCount(totalCountOfPurchasedBook.getTotalOrderCount() + 1);
				totalCountOfPurchasedBook.setTotalPuchasedAmount(
						totalCountOfPurchasedBook.getTotalPuchasedAmount() + order.getPurchase());
				map.replace(month.toString(), totalCountOfPurchasedBook);
			}
		}

		List<TotalCountOfPurchasedBook> totalCountOfPurchasedBookList = new ArrayList<TotalCountOfPurchasedBook>(
				map.values());

		model.addAttribute("totalCountOfPurchasedBookList", totalCountOfPurchasedBookList);
		logger.info("get purchased books table");
		return "purchased-books";

	}

}
