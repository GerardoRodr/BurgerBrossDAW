package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.model.DetallePedido;
import com.cibertec.repository.IPedidoRepository;
import com.cibertec.repository.IProductoRepository;

@Controller
public class PedidosController {
	@Autowired
	private IPedidoRepository pedidoRepo;
	
	@Autowired
	private IProductoRepository prodRepo;
	
	private String nombreCliente;
	
	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}	
	
	@GetMapping("/")
	public String index(Model m) {
		m.addAttribute("lstPedidos", pedidoRepo.findAll());
		return "index";
	}
	
	@GetMapping("/nuevoPedido1")
	public String nuevoPedido1() {
		return "nuevoPedidoNombre";
	}
	
	@GetMapping("/nuevoPedido2")
	public String nuevoPedido2(Model m, @RequestParam("nombreCliente") String nombreCliente) {
		m.addAttribute("selProd", prodRepo.findAll());
		setNombreCliente(nombreCliente);
		m.addAttribute("nombreCliente", "(" + getNombreCliente() + ")");
		
		m.addAttribute("det_pedido", new DetallePedido());
		
		return "nuevoPedidoProducto";
	}
	
	@PostMapping("/nuevoPedido2")
	public String nuevoPedido2Post() {	
		return "nuevoPedidoProducto";
	}
}