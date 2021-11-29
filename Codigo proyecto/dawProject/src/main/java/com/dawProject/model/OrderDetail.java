package com.dawProject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;

@Entity
@Table(name="OrderDetails")
public class OrderDetail implements Serializable {
	
	
	private static final long serialVersionUID = 2258581567332090958L;
	
	@Id
	@Column(name="OrderDetail_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderDetail_id;
	
	@Column(name="Quantity")
	private int quantity;
	
	@Column(name="Subtotal")
	private float subtotal;
	
	@ManyToOne
	@JoinColumn(name="Products_ProductCode",nullable=false)
	private Product product;
	
	@ManyToOne 
	@JoinColumn(name="Orders_OrderNumber",nullable=false)
	private Order order;

	public OrderDetail() {
		super();
	}

	public OrderDetail(int orderDetail_id, int quantity, float subtotal, Product product, Order order) {
		super();
		this.orderDetail_id = orderDetail_id;
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.product = product;
		this.order = order;
	}
	
	public OrderDetail(int quantity, float subtotal, Product product, Order order) {
		super();
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.product = product;
		this.order = order;
	}

	public int getOrderDetail_id() {
		return orderDetail_id;
	}

	public void setOrderDetail_id(int orderDetail_id) {
		this.orderDetail_id = orderDetail_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "OrderDetail [orderDetail_id=" + orderDetail_id + ", quantity=" + quantity + ", subtotal=" + subtotal
				+ ", product=" + product + ", order=" + order + "]";
	}

	
	
	



	
	
	
	

}
