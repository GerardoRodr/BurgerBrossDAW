package com.cibertec.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_productos")
public class Producto {
	@Id
	@Column(name = "id_producto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	@Column(name = "nombre_producto")
	private String nombreProducto;
	@Column(name = "id_categoria_prod")
	private int idCategoriaProd;
	@Column(name = "desc_prod")
	private String descProd;
	@Column(name = "stock")
	private int stock;
	@Column(name = "precio_producto")
	private BigDecimal precioProducto;
	
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public int getIdCategoriaProd() {
		return idCategoriaProd;
	}
	public void setIdCategoriaProd(int idCategoriaProd) {
		this.idCategoriaProd = idCategoriaProd;
	}
	public String getDescProd() {
		return descProd;
	}
	public void setDescProd(String descProd) {
		this.descProd = descProd;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public BigDecimal getPrecioProducto() {
		return precioProducto;
	}
	public void setPrecioProducto(BigDecimal precioProducto) {
		this.precioProducto = precioProducto;
	}
}
