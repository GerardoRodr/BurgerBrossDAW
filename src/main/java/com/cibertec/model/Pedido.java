package com.cibertec.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_pedidos")
public class Pedido {
	@Id
	@Column(name = "id_pedido")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_pedido;
	@Column(name = "total_pedido")
	private BigDecimal total_pedido;
	@Column(name = "nombre_cliente")
	private String nombre_cliente;
	@Column(name = "fecha_pedido")
	private Date fecha_pedido;
	@Column(name = "estado_pedido")
	private boolean estado_pedido;
	
	public int getId_pedido() {
		return id_pedido;
	}
	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}
	public BigDecimal getTotal_pedido() {
		return total_pedido;
	}
	public void setTotal_pedido(BigDecimal total_pedido) {
		this.total_pedido = total_pedido;
	}
	public String getNombre_cliente() {
		return nombre_cliente;
	}
	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}
	public Date getFecha_pedido() {
		return fecha_pedido;
	}
	public void setFecha_pedido(Date fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}
	public boolean isEstado_pedido() {
		return estado_pedido;
	}
	public void setEstado_pedido(boolean estado_pedido) {
		this.estado_pedido = estado_pedido;
	}
}
