package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, String> {
}