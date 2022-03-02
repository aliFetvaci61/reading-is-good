package com.alifetvaci.ReadingIsGood.services;

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
import org.springframework.stereotype.Service;

import com.alifetvaci.ReadingIsGood.controller.StatisticsController;
import com.alifetvaci.ReadingIsGood.models.Order;
import com.alifetvaci.ReadingIsGood.models.OrderStatus;
import com.alifetvaci.ReadingIsGood.models.TotalCountOfPurchasedBook;
import com.alifetvaci.ReadingIsGood.payload.response.OrderCount;
import com.alifetvaci.ReadingIsGood.payload.response.OrdersAmount;
import com.alifetvaci.ReadingIsGood.repository.OrderRepository;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public OrderCount getTotalOrderCount(String CustomerId) {

		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date oneMonthAgo = cal.getTime();

		Long countByCustomerIdAndCreatedAtBetween = orderRepository.countByCustomerIdAndCreatedAtBetween(CustomerId,
				oneMonthAgo, now);
		OrderCount orderCount = new OrderCount();
		orderCount.setCount(countByCustomerIdAndCreatedAtBetween);
		logger.info("get Total Order Count");
		return orderCount;
	}

	@Override
	public OrdersAmount getPurchasedAmount(String CustomerId) {
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date oneMonthAgo = cal.getTime();

		List<Order> findByCustomerIdInAndCreatedAtBetweenAndOrderStatusIn = orderRepository
				.findByCustomerIdAndCreatedAtBetweenAndOrderStatusIn(CustomerId, oneMonthAgo, now,
						OrderStatus.COMPLETED);
		double totalPurchase = findByCustomerIdInAndCreatedAtBetweenAndOrderStatusIn.stream()
				.mapToDouble(f -> f.getPurchase()).sum();

		OrdersAmount orderAmount = new OrdersAmount();
		orderAmount.setAmount(totalPurchase);
		logger.info("get Total Purchased Amount");
		return orderAmount;
	}

	@Override
	public List<TotalCountOfPurchasedBook> getPurchasedBooks(String CustomerId) {
		Calendar calendar = Calendar.getInstance();

		List<Order> findByCustomerIdAndOrderStatusIn = orderRepository.findByCustomerIdAndOrderStatusIn(CustomerId,
				OrderStatus.COMPLETED);
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
		logger.info("get purchased books table");
		return new ArrayList<TotalCountOfPurchasedBook>(map.values());
	}

}
