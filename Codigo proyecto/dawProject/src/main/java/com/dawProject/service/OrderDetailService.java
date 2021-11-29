package com.dawProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawProject.model.OrderDetail;
import com.dawProject.repository.OrderDetailRepository;

@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	public List<OrderDetail> findAll() {
		return orderDetailRepository.findAll();
	}
	
	public List<OrderDetail> findCart(int orderDetailId) {
		//List<OrderDetail> list = orderDetailRepository.findAll();
		List<OrderDetail> list = new ArrayList<>();
		//list.clear();
		for (OrderDetail o: orderDetailRepository.findAll()) {
			if (o.getOrder().getOrderNumber()==orderDetailId) {
				list.add(o);
			}
		}
		return list;
	}
	
	public OrderDetail findByOrderDetailId(Integer orderDetailId) {
		OrderDetail orderDetail = null;
		for (OrderDetail o: orderDetailRepository.findAll()) {
			if (o.getOrderDetail_id()==orderDetailId) {
				orderDetail = o;
			}
		}
		return orderDetail;
	}	
	
	public OrderDetail findByOrderDetailIdProduct(Integer IdProduct, Integer orderCode) {
		OrderDetail orderDetail = null;
		for (OrderDetail o: orderDetailRepository.findAll()) {
			if (o.getOrder().getOrderNumber() == orderCode) {
				if (o.getProduct().getProductCode()==IdProduct) {
					orderDetail = o;
				}
			}
		}
		return orderDetail;
	}
	
	public OrderDetail save(OrderDetail orderDetail) {	
		orderDetailRepository.save(orderDetail);
		return orderDetail;
	}
	
	public OrderDetail delete(OrderDetail orderDetail) {
		for (OrderDetail p : orderDetailRepository.findAll()) {
			if(orderDetail.getOrderDetail_id()==p.getOrderDetail_id())
				orderDetailRepository.delete(p);
		}
		return orderDetail;
	}

}
