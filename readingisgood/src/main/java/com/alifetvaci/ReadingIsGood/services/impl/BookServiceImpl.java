package com.alifetvaci.ReadingIsGood.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alifetvaci.ReadingIsGood.exception.BadRequestException;
import com.alifetvaci.ReadingIsGood.models.Book;
import com.alifetvaci.ReadingIsGood.payload.request.BookRequest;
import com.alifetvaci.ReadingIsGood.payload.request.BookStockRequest;
import com.alifetvaci.ReadingIsGood.payload.request.OrderBook;
import com.alifetvaci.ReadingIsGood.repository.BookRepository;
import com.alifetvaci.ReadingIsGood.services.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Override
	public synchronized boolean updateBookStock(List<OrderBook> list) { //if it happens if 2 or more users tries to buy one last book at the same time
		//One thread one transaction, before the end of the transaction  the other transaction not working, just waiting .
		for (OrderBook oB : list) {
			Book bookStock = bookRepository.findById(oB.getBook()).orElse(null);
			if (bookStock == null) {
				return false;
			}

			if (bookStock.getTotal() - oB.getNumber() < 0) {
				return false;
			} else {
				bookStock.setTotal(bookStock.getTotal() - oB.getNumber());
				;
				bookRepository.save(bookStock);
			}
		}
		return true;
	}

	@Override
	public Book persistNewBook(BookRequest bookRequest) {
		Book book = new Book(bookRequest.getName(), bookRequest.getWriter(), bookRequest.getEdition(),
				bookRequest.getTotal(), bookRequest.getPrice());
		Book saved = bookRepository.save(book);
		logger.info("Persisted New Book");
		return saved;
	}

	@Override
	public Book updateBookStock(String id, BookStockRequest bookStockRequest) {
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			book.setTotal(bookStockRequest.getTotal());
		} else {
			logger.info("Invalid book id : " + id);
			throw new BadRequestException("Invalid book id : " + id);
		}
		logger.info("Updated Book Stock");
		return bookRepository.save(book);
	}

}
