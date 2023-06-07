package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Long> {
	List<Producto> findByIdCategoriaProd(int idCategoriaProd);
}