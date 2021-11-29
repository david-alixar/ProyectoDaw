package com.dawProject.form;

public class AltaProductoForm {

	private int idCategoria;
	
	private String nombreProducto;
	
	private String descripcion;
	
	private String imagen;
	
	private float precio;
	
	private int cantidad;
	
	private String marca;
	
	private String modelo;
	
	private int year;

	public AltaProductoForm() {
		super();
	}

	public AltaProductoForm(int idCategoria, String nombreProducto, String descripcion, String imagen, float precio, int cantidad,
			String marca, String modelo, int year) {
		super();
		this.idCategoria = idCategoria;
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.precio = precio;
		this.cantidad = cantidad;
		this.marca = marca;
		this.modelo = modelo;
		this.year = year;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public String getImagen() {
		return imagen;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	

	

	
}
