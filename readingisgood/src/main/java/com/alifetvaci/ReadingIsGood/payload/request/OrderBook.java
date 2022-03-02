package com.alifetvaci.ReadingIsGood.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class OrderBook {

	@Size(min=10)
	private String book;
	
	@Min(value = 1)
	private int number;

	public OrderBook() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderBook(String book, int number) {
		super();
		this.book = book;
		this.number = number;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}
