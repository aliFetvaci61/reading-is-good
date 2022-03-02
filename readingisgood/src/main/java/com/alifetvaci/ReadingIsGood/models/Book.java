package com.alifetvaci.ReadingIsGood.models;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Book {

	@Id
	@JsonIgnore
	private String id;

	private String name;

	private String writer;

	private String edition;

	private int total;

	private double price;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(String id, String name, String writer, String edition, int total, double price) {
		super();
		this.id = id;
		this.name = name;
		this.writer = writer;
		this.edition = edition;
		this.total = total;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

}
