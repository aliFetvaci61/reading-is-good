package com.alifetvaci.ReadingIsGood.payload.request;

import javax.validation.constraints.NotBlank;

public class BookRequest {
	
	@NotBlank
	private String name;

	@NotBlank
	private String writer;

	@NotBlank
	private String edition;

	@NotBlank
	private int total;
	
	@NotBlank
	private double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	

}
