package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.model.Usuario;
import com.cibertec.repository.IUsuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsuarioController {
	@Autowired
	private IUsuarioRepository repo;
	
	@GetMapping("/")
	public String login(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		
		if (sesion != null) {
			url = "redirect:/index";
		} else {
			url = "login";
		}
		
		return url;
	}
	
	@PostMapping("/login")
	public String loginPost(HttpServletResponse response ,@RequestParam("usuario") String user, @RequestParam("password") String pass) {
		String url;
		
		Usuario u = repo.findLogin(user, pass);
		
		if (u != null) {
			
			Cookie sesion = new Cookie("sesion", "LoginExistoso");
			sesion.setMaxAge(604800);
			sesion.setPath("/");
			
			response.addCookie(sesion);						
			url = "redirect:/index";
		} else {
			url = "redirect:/";
		}
		
		return url;
	}
	
	@GetMapping("/listadoUsuarios")
	public String index(Model m) {
		m.addAttribute("lstUsuarios", repo.findAll());
		return "listadoUsuarios";
	}
}