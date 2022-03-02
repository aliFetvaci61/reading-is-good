package com.alifetvaci.ReadingIsGood.models;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

import com.alifetvaci.ReadingIsGood.payload.request.OrderBook;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Order {
	
	@Id
	@JsonIgnore
	private String id;
	
	@NotBlank
	private String customerId;
	
	@NotBlank
	private List<OrderBook> orderBooks;
	
	private OrderStatus orderStatus;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private double purchase;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<OrderBook> getOrderBooks() {
		return orderBooks;
	}

	public void setOrderBooks(List<OrderBook> orderBooks) {
		this.orderBooks = orderBooks;
	}

	public double getPurchase() {
		return purchase;
	}

	public void setPurchase(double purchase) {
		this.purchase = purchase;
	}
	
	

}
