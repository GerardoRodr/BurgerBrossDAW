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
	public String todos(Model m) {
		m.addAttribute("lstProds", prodRepo.findAll());
		return "listadoProductos";
	}
	
	@GetMapping("/productosHamburguesas")
	public String listHamburguesas(Model m) {
		m.addAttribute("lstProdsHamb", prodRepo.findByIdCategoriaProd(1));
		return "listadoProductosHamburguesas";
	}
	
	@GetMapping("/productosBebidas")
	public String listBebidas(Model m) {
		m.addAttribute("lstProdsHamb", prodRepo.findByIdCategoriaProd(2));
		return "listadoProductosBebidas";
	}
	
	@GetMapping("/productosComplementos")
	public String listComplementos(Model m) {
		m.addAttribute("lstProdsHamb", prodRepo.findByIdCategoriaProd(3));
		return "listadoProductosComplementos";
	}
	
	@GetMapping("/productosAdicionales")
	public String listAdicionales(Model m) {
		m.addAttribute("lstProdsHamb", prodRepo.findByIdCategoriaProd(4));
		return "listadoProductosAdicionales";
	}
}