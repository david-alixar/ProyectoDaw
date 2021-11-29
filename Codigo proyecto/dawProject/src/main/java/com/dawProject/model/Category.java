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
@Table(name="Categories")
public class Category implements Serializable {
	
	
	private static final long serialVersionUID = 2258581567332090958L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CategorieId")
	private int categorieId;
	
	@Column(name="CategorieName")
	private String categorieName;
	
	@Column(name="Description")
	private String description;
	
	public Category(int categorieId, String categorieName, String description) {
		super();
		this.categorieId = categorieId;
		this.categorieName = categorieName;
		this.description = description;
	}

	public Category() {
		super();
	}

	public int getCategorieId() {
		return categorieId;
	}

	public void setCategorieId(int categorieId) {
		this.categorieId = categorieId;
	}

	public String getCategorieName() {
		return categorieName;
	}

	public void setCategorieName(String categorieName) {
		this.categorieName = categorieName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Category [categorieId=" + categorieId + ", categorieName=" + categorieName + ", description="
				+ description + "]";
	}


	
	
	


	
	
	

}
