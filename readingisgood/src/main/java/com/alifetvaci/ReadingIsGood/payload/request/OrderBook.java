package com.alifetvaci.ReadingIsGood.payload.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderBook {

	@NotNull(message="book ID cannot be null")
	private String book;
	
    @Min(1)
	private int number;

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
