package com.alifetvaci.ReadingIsGood.services;

import java.util.List;

import com.alifetvaci.ReadingIsGood.models.Book;
import com.alifetvaci.ReadingIsGood.payload.request.BookRequest;
import com.alifetvaci.ReadingIsGood.payload.request.BookStockRequest;
import com.alifetvaci.ReadingIsGood.payload.request.OrderBook;

public interface BookService {

	boolean updateBookStock(List<OrderBook> list);
	Book updateBookStock(String id, BookStockRequest bookStockRequest);
	Book persistNewBook(BookRequest bookRequest);
}
