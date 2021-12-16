package com.dawProject.form;

public class AltaUsuarioForm {

	private String username;
	
	private String password;
	
	private String password2;
	
	private String role;
	
	private String picture;
	
	private String name;
	
	private String lastname;
	
	private String email;
	
	private String address;
	
	private String phone;
	
	private String city;
	
	private String postalcode;
	
	private String country;
	
	private String state;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password) {
		this.password2 = password;
	}

	public AltaUsuarioForm(String username, String password, String password2, String role, String picture, String name,
			String lastname, String email, String address, String phone, String city, String postalcode, String country,
			String state) {
		super();
		this.username = username;
		this.password = password;
		this.password2 = password2;
		this.role = role;
		this.picture = picture;
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
	
	public AltaUsuarioForm(String username, String password, String role, String picture, String name,
			String lastname, String email, String address, String phone, String city, String postalcode, String country,
			String state) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.picture = picture;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	public AltaUsuarioForm() {
		super();
	}
}
