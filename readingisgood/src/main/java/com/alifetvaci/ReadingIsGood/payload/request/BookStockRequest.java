package com.alifetvaci.ReadingIsGood.payload.request;

import javax.validation.constraints.NotBlank;

public class BookStockRequest {
	
	@NotBlank
	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
