package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_categoria_productos")
public class CategoriaProductos {
	@Id
	@Column(name = "id_categoria_prod")
	private int id_categoria_prod;
	@Column(name = "nombre_categoria")
	private String nombre_categoria;
	
	public int getId_categoria_prod() {
		return id_categoria_prod;
	}
	public void setId_categoria_prod(int id_categoria_prod) {
		this.id_categoria_prod = id_categoria_prod;
	}
	public String getNombre_categoria() {
		return nombre_categoria;
	}
	public void setNombre_categoria(String nombre_categoria) {
		this.nombre_categoria = nombre_categoria;
	}
}
