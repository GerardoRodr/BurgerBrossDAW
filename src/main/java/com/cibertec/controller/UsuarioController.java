package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cibertec.repository.IUsuarioRepository;

@Controller
public class UsuarioController {
	@Autowired
	private IUsuarioRepository repo;
	
	@GetMapping("/listadoUsuarios")
	public String index(Model m) {
		m.addAttribute("lstUsuarios", repo.findAll());
		return "listadoUsuarios";
	}
}