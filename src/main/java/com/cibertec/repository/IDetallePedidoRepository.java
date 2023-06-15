package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.DetallePedido;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
	List<DetallePedido> findByIdPedido(int idPedido);  // Cambio de "findById_pedido" a "findByIdPedido"
}
