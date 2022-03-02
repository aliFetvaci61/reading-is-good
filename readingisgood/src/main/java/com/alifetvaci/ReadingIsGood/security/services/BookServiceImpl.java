package com.alifetvaci.ReadingIsGood.security.services;

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

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Override
	public boolean updateBookStock(List<OrderBook> list) {

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
		Book book = new Book();
		book.setName(bookRequest.getName());
		book.setWriter(bookRequest.getWriter());
		book.setEdition(bookRequest.getEdition());
		book.setTotal(bookRequest.getTotal());
		book.setPrice(bookRequest.getPrice());
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
