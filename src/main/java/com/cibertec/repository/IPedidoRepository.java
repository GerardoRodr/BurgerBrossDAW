package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.model.Pedido;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
	@Query(value = "SELECT p FROM Pedido p WHERE p.id_pedido = (SELECT MAX(p2.id_pedido) FROM Pedido p2)")
    Pedido findUltimoPedido();
}