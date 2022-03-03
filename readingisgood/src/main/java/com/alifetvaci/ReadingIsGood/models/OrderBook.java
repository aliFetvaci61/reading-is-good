package com.alifetvaci.ReadingIsGood.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class OrderBook {
	@NotBlank
	private String book;
	
	@Min(value = 1, message = "total should not be less than 1")
	private int total;

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "OrderBook [book=" + book + ", total=" + total + "]";
	}
	
	
}
