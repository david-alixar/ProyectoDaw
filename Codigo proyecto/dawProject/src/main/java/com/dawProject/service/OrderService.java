package com.dawProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawProject.model.Customer;
import com.dawProject.model.Order;
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
	
	public List<Order> findAllByStatus(String status, List<Order> orders) {
		List<Order> list = new ArrayList<>();
		for (Order o: orders) {
			if (o.getStatus().equals(status)) {
				list.add(o);
			}
		}
		return list;
	}
	
	public List<Order> findAllByDate(String date, List<Order> orders) {
		List<Order> list = new ArrayList<>();
		for (Order o: orders) {
			if (o.getDate().equals(date)) {
				list.add(o);
			}
		}
		return list;
	}
	
	public List<Customer> findAllByUsername() {
		List<Customer> list = new ArrayList<>();
		for (Order o: orderRepository.findAll()) {
			if (!list.contains(o.getCustomer())) {
				list.add(o.getCustomer());
			}
		}
		return list;
	}
	
	public List<String> findAllByDates(List<Order> orders) {
		List<String> list = new ArrayList<>();
		for (Order o: orders) {
			if (!list.contains(o.getDate())) {
				list.add(o.getDate());
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
