package com.dawProject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Customers")
public class Customer implements Serializable {
	
	
	private static final long serialVersionUID = 2258581567332090958L;
	
	@Id
	@Column(name="Username")
	private String username;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Lastname")
	private String lastname;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="Address")
	private String address;
	
	@Column(name="Phone")
	private String phone;
	
	@Column(name="City")
	private String city;
	
	@Column(name="Postalcode")
	private String postalcode;
	
	@Column(name="Country")
	private String country;
	
	@Column(name="State")
	private String state;
	
	public Customer() {
		super();
	}

	public Customer(String username, String name, String lastname, String email, String address, String phone, String city,
			String postalcode, String country, String state) {
		super();
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.city = city;
		this.postalcode = postalcode;
		this.country = country;
		this.state = state;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Customer [username=" + username + ", name=" + name + ", lastname=" + lastname + ", email=" + email
				+ ", address=" + address + ", phone=" + phone + ", city=" + city + ", postalcode=" + postalcode
				+ ", country=" + country + ", state=" + state + "]";
	}



	
	
	

}
