package com.alifetvaci.ReadingIsGood.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alifetvaci.ReadingIsGood.models.Order;
import com.alifetvaci.ReadingIsGood.payload.request.OrderRequest;

public interface OrderService {

	Order createOrder(String CustomerId, OrderRequest orderRequest);

	Map<String, Object> getOrders(String CustomerId, int page, int size);

	Order getOrderDetails(String CustomerId, String id);

	List<Order> listOrdersByDateInterval(String CustomerId, Date startDate, Date endData);

}
