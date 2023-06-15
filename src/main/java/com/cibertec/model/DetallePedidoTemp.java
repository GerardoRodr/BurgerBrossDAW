package com.cibertec.model;

import java.math.BigDecimal;

public class DetallePedidoTemp {
	
	private int id_producto;
	
	private String nombreProducto;
	
	private BigDecimal precio_producto;
	
	private int cantidad;

	private BigDecimal subtotal;

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public BigDecimal getPrecio_producto() {
		return precio_producto;
	}

	public void setPrecio_producto(BigDecimal precio_producto) {
		this.precio_producto = precio_producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
}
