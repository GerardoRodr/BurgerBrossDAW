package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.CategoriaProductos;

public interface ICategoriaProductosRepository extends JpaRepository<CategoriaProductos, Integer> {
}