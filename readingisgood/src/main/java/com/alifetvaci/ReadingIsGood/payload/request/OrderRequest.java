package com.alifetvaci.ReadingIsGood.payload.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class OrderRequest {

	@Valid
	@NotNull
	private List<OrderBook> orderBook;
	
	public OrderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderRequest(List<OrderBook> orderBook) {
		super();
		this.orderBook = orderBook;
	}

	public List<OrderBook> getOrderBook() {
		return orderBook;
	}

	public void setOrderBook(List<OrderBook> orderBook) {
		this.orderBook = orderBook;
	}

}
