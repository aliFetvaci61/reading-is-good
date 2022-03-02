package com.alifetvaci.ReadingIsGood.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.ReadingIsGood.exception.BadRequestException;
import com.alifetvaci.ReadingIsGood.models.Book;
import com.alifetvaci.ReadingIsGood.payload.request.BookRequest;
import com.alifetvaci.ReadingIsGood.payload.request.BookStockRequest;
import com.alifetvaci.ReadingIsGood.repository.BookRepository;

@RestController
@RequestMapping("/api")
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookRepository bookRepository;

	@PostMapping("/book")
	public ResponseEntity<Book> persistNewBook(@RequestBody @Valid BookRequest bookRequest) {
		Book book = new Book();
		book.setName(bookRequest.getName());
		book.setWriter(bookRequest.getWriter());
		book.setEdition(bookRequest.getEdition());
		book.setTotal(bookRequest.getTotal());
		book.setPrice(bookRequest.getPrice());
		bookRepository.save(book);
		logger.info("Persisted New Book");
		return ResponseEntity.ok(book);

	}

	@PutMapping("/book/{id}")
	public ResponseEntity<Book> updateBooksStock(@PathVariable String id,
			@RequestBody @Valid BookStockRequest bookStockRequest) {
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			book.setTotal(bookStockRequest.getTotal());
		} else {
			logger.info("Invalid book id : " + id);
			throw new BadRequestException("Invalid book id : " + id);
		}
		logger.info("Updated Book Stock");
		bookRepository.save(book);

		return ResponseEntity.ok(book);

	}

}
