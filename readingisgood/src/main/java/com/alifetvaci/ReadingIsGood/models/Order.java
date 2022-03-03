package com.alifetvaci.ReadingIsGood.models;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alifetvaci.ReadingIsGood.payload.request.OrderBook;


@Document("orders")
public class Order {

	@Id
	private String id;

	@NotNull
	private String customerId;

	@Valid
	@NotNull
	private List<OrderBook> orderBooks;

	private OrderStatus orderStatus;

	private Date createdAt;

	private Date updatedAt;

	private double purchase;

	public Order() {
		super();
	}

	public Order(String id, @NotNull String customerId, @Valid @NotNull List<OrderBook> orderBooks,
			OrderStatus orderStatus, Date createdAt, Date updatedAt, double purchase) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.orderBooks = orderBooks;
		this.orderStatus = orderStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.purchase = purchase;
	}

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

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", orderBooks=" + orderBooks + ", orderStatus="
				+ orderStatus + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", purchase=" + purchase
				+ "]";
	}

}
