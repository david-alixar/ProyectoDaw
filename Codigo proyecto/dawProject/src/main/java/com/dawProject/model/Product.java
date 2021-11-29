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
@Table(name="Products")
public class Product implements Serializable {
	
	
	private static final long serialVersionUID = 2258581567332090958L;
	
	@Id
	@Column(name="ProductCode")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productCode;
	
	@Column(name="ProductName")
	private String productName;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Price")
	private float price;
	
	@Column(name="ProductAvailable")
	private int productAvailable;
	
	@Column(name="Picture")
	private String picture;
	
	@Column(name="Brand")
	private String brand;
	
	@Column(name="Model")
	private String model;
	
	@Column(name="Year")
	private int year;
	
	@ManyToOne   
	@JoinColumn(name="Categories_CategorieId",nullable=false)
	private Category category;

	public Product(String productName, String description, float price, int productAvailable,
			String picture, String brand, String model, int year, Category category) {
		super();
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.productAvailable = productAvailable;
		this.picture = picture;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.category = category;
	}
	
	public Product(String productName, String description, float price, int productAvailable,
			String picture, String brand, String model, int year) {
		super();
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.productAvailable = productAvailable;
		this.picture = picture;
		this.brand = brand;
		this.model = model;
		this.year = year;
	}

	public Product() {
		super();
	}

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getProductAvailable() {
		return productAvailable;
	}

	public void setProductAvailable(int productAvailable) {
		this.productAvailable = productAvailable;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Product [productCode=" + productCode + ", productName=" + productName + ", description=" + description
				+ ", price=" + price + ", productAvailable=" + productAvailable + ", picture=" + picture + ", brand="
				+ brand + ", model=" + model + ", year=" + year + ", category=" + category + "]";
	}

	
	

	
	
	
	

}
