package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, String> {
    @Query("SELECT u FROM Usuario u WHERE u.usuario = ?1 AND u.password = ?2")
    Usuario findLogin(String usuario, String password);
}