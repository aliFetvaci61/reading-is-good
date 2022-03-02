package com.alifetvaci.ReadingIsGood.payload.request;

import javax.validation.constraints.Min;

public class BookStockRequest {
	
	@Min(value = 1, message = "total should not be less than 1")
	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
