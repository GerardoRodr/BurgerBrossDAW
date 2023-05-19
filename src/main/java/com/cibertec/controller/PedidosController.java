package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cibertec.repository.IPedidoRepository;

@Controller
public class PedidosController {
	@Autowired
	private IPedidoRepository pedidoRepo;
	
	@GetMapping("/")
	public String index(Model m) {
		m.addAttribute("lstPedidos", pedidoRepo.findAll());
		return "index";
	}
}