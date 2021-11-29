package com.dawProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawProject.model.Order;
import com.dawProject.model.OrderDetail;
import com.dawProject.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	public Order findByOrderNumber(Integer orderNumber) {
		Order order = null;
		for (Order o: orderRepository.findAll()) {
			if (o.getOrderNumber()==orderNumber) {
				order = o;
			}
		}
		return order;
	}	
	
	public Order findByCustomerPending(String customer) {
		Order order = null;
		for (Order o: orderRepository.findAll()) {
			if (o.getCustomer().getUsername().equals(customer) && o.getStatus().equals("No finalizado")) {
				order = o;
			}
		}
		return order;
	}	
	
	public Order findByCustomer(String customer) {
		Order order = null;
		for (Order o: orderRepository.findAll()) {
			if (o.getCustomer().getUsername().equals(customer)) {
				order = o;
			}
		}
		return order;
	}
	
	public List<Order> findAllByCustomer(String customer) {
		List<Order> list = orderRepository.findAll();
		list.clear();
		for (Order o: orderRepository.findAll()) {
			if (o.getCustomer().getUsername().equals(customer)) {
				list.add(o);
			}
		}
		return list;
	}
	
	public Order save(Order order) {	
		orderRepository.save(order);
		return order;
	}
	
	public Order changeStatus(String status, Order order) {	
				order.setStatus(status);
				orderRepository.save(order);
		return order;
	}
	
	public Order delete(Order order) {
		for (Order o : orderRepository.findAll()) {
			if(order.getOrderNumber()==o.getOrderNumber())
				orderRepository.delete(o);
		}
		return order;
	}
	
	

}
