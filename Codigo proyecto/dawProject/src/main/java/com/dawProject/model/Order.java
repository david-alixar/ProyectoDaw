package com.dawProject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;

@Entity
@Table(name="Orders")
public class Order implements Serializable {
	
	
	private static final long serialVersionUID = 2258581567332090958L;
	
	@Id
	@Column(name="OrderNumber")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderNumber;
	
	@Column(name="Date")
	private String date;
	
	@Column(name="Status")
	private String status;
	
	@Column(name="TrackingNumber")
	private String trackingNumber;
	
	@Column(name="Total")
	private float Total;
	
	@ManyToOne   
	@JoinColumn(name="Customers_Username",nullable=false)
	private Customer customer;

	public Order() {
		super();
	}

	public Order(int orderNumber, String date, String status, String trackingNumber, float total, Customer customer) {
		super();
		this.orderNumber = orderNumber;
		this.date = date;
		this.status = status;
		this.trackingNumber = trackingNumber;
		Total = total;
		this.customer = customer;
	}
	
	public Order(String date, String status, String trackingNumber, float total, Customer customer) {
		super();
		this.date = date;
		this.status = status;
		this.trackingNumber = trackingNumber;
		Total = total;
		this.customer = customer;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public float getTotal() {
		return Total;
	}

	public void setTotal(float total) {
		Total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", date=" + date + ", status=" + status + ", trackingNumber="
				+ trackingNumber + ", Total=" + Total + ", customer=" + customer + "]";
	}
	
	



	
	
	
	

}
