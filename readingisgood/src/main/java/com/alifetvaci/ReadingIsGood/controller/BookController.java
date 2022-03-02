package com.alifetvaci.ReadingIsGood.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.ReadingIsGood.models.Book;
import com.alifetvaci.ReadingIsGood.payload.request.BookRequest;
import com.alifetvaci.ReadingIsGood.payload.request.BookStockRequest;
import com.alifetvaci.ReadingIsGood.services.BookService;

@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/book")
	public ResponseEntity<Book> persistNewBook(@RequestBody @Valid BookRequest bookRequest) {
		return ResponseEntity.ok(bookService.persistNewBook(bookRequest));

	}

	@PutMapping("/book/{id}")
	public ResponseEntity<Book> updateBooksStock(@PathVariable String id,
			@RequestBody @Valid BookStockRequest bookStockRequest) {
		return ResponseEntity.ok(bookService.updateBookStock(id, bookStockRequest));

	}

}
