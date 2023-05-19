package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cibertec.repository.IProductoRepository;

@Controller
public class ProductoController {
	@Autowired
	private IProductoRepository prodRepo;
	
	@GetMapping("/listadoProductos")
	public String listado(Model m) {
		m.addAttribute("lstProds", prodRepo.findAll());
		return "listadoProductos";
	}
}