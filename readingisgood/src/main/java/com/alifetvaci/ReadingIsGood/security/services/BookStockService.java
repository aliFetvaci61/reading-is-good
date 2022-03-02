package com.alifetvaci.ReadingIsGood.security.services;

import java.util.List;


import com.alifetvaci.ReadingIsGood.payload.request.OrderBook;

public interface BookStockService {

	boolean updateBookStock(List<OrderBook> list);
}
