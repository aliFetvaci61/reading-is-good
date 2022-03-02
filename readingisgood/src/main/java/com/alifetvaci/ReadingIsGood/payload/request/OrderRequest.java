package com.alifetvaci.ReadingIsGood.payload.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class OrderRequest {

	@NotEmpty(message = "Input book list cannot be empty.")
	private List<OrderBook> orderBook;

	public List<OrderBook> getOrderBook() {
		return orderBook;
	}

	public void setOrderBook(List<OrderBook> orderBook) {
		this.orderBook = orderBook;
	}

}
