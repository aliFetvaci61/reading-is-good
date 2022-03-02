package com.alifetvaci.ReadingIsGood.security.services;

import java.util.List;

import com.alifetvaci.ReadingIsGood.models.TotalCountOfPurchasedBook;
import com.alifetvaci.ReadingIsGood.payload.response.OrderCount;
import com.alifetvaci.ReadingIsGood.payload.response.OrdersAmount;

public interface StatisticsService {
	
	
	OrderCount getTotalOrderCount(String CustomerId);
	OrdersAmount getPurchasedAmount(String CustomerId);
	List<TotalCountOfPurchasedBook> getPurchasedBooks(String CustomerId);

}
