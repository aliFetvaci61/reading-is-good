package com.alifetvaci.ReadingIsGood.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alifetvaci.ReadingIsGood.models.Book;
import com.alifetvaci.ReadingIsGood.payload.request.OrderBook;
import com.alifetvaci.ReadingIsGood.repository.BookRepository;


@Service
public class BookStockServiceImpl implements BookStockService{
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public boolean updateBookStock(List<OrderBook> list) {
		for (OrderBook oB : list) {
			Book bookStock = bookRepository.findById(oB.getBook()).orElse(null);
			if (bookStock == null) {
				return false;
			}

			if (bookStock.getTotal() - oB.getNumber() < 0) {
				return false;
			}
			else {
				bookStock.setTotal(bookStock.getTotal() - oB.getNumber());;
				bookRepository.save(bookStock);
			}
		}
		return true;
	}



}
