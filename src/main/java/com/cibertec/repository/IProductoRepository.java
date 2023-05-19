package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, String> {
}