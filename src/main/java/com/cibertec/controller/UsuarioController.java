package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.model.Usuario;
import com.cibertec.repository.IRolRepository;
import com.cibertec.repository.IUsuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsuarioController {
	@Autowired
	private IUsuarioRepository repo;
	
	@Autowired
	private IRolRepository rolRepo;
	
	@GetMapping("/cerrarSesion")
	public String cerrarSesion(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		
		// Itera sobre la lista de cookies (pq no solo hay una) y busca la cookie con nombre "sesion"
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("sesion")) {
	                cookie.setValue(null);
	                cookie.setMaxAge(0);
	                cookie.setPath("/");
	                response.addCookie(cookie);
	                break;
	            }
	        }
	    }
		
		return "redirect:/";
	}
	
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
			//LE ASIGNAMOS A LA COOKIE EL VALOR DE SU IDUSUARIO
			Cookie sesion = new Cookie("sesion", String.valueOf(u.getIdUsuario()));
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
	public String index(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		Usuario u = repo.findById(Integer.parseInt(sesion)).orElse(new Usuario());
		
		if (sesion != null) {
			m.addAttribute("usuario", u.getNombreUsuario());
			m.addAttribute("lstUsuarios", repo.findAll());
			url = "listadoUsuarios";
		} else {
			url = "redirect:/";
		}
		return url;
	}
	
	@GetMapping("/editarUsuario")
	public String editarUsuario(Model m, @RequestParam("idUsuario") int idUsuario, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		Usuario u = repo.findById(Integer.parseInt(sesion)).orElse(new Usuario());
		
		if (sesion != null) {
			m.addAttribute("usuario", u.getNombreUsuario());
			m.addAttribute("roles", rolRepo.findAll());
			m.addAttribute("usuarioModelo", repo.findById(idUsuario));
			url = "editarUsuario";
		} else {
			url = "redirect:/";
		}
		return url;
	}
	
	@PostMapping("/editarUsuario")
	public String editarUsuarioPost(@ModelAttribute Usuario usuario, Model m) {
		try {
			repo.save(usuario);
		} catch (Exception e) {
			m.addAttribute("mensaje", "Hubo un error en el registro.");
			e.printStackTrace();
		}
		
		return "redirect:/listadoUsuarios";
	}
	
	@GetMapping("/nuevoUsuario")
	public String nuevoUsuario(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		Usuario u = repo.findById(Integer.parseInt(sesion)).orElse(new Usuario());
		
		if (sesion != null) {
			m.addAttribute("usuario", u.getNombreUsuario());
			m.addAttribute("modeloUsuario", new Usuario());
			m.addAttribute("roles", rolRepo.findAll());
			
			url = "nuevoUsuario";
		} else {
			url = "redirect:/";
		}
		return url;
	}
	
	@PostMapping("/nuevoUsuario")
	public String nuevoUsuarioPost(Model m, @ModelAttribute Usuario usu) {
		
		try {
			repo.save(usu);
		} catch (Exception e) {
			m.addAttribute("mensaje", "Hubo un error en el registro.");
			e.printStackTrace();
		}
		
		return "redirect:/listadoUsuarios";
	}
	
	@PostMapping("/eliminarUsuario")
	public String eliminarUsuario(@RequestParam("idUsuario") int idUsuario, RedirectAttributes r) {
		
		try {
			repo.deleteById(idUsuario);
			r.addFlashAttribute("mensaje", "Se elimino correctamente el usuario con id: " + idUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/listadoUsuarios";
	}
	
	@GetMapping("/verPerfil")
	public String perfil(Model m, @CookieValue(value = "sesion", required = false) String sesion) {	
		String url;
		Usuario u = repo.findById(Integer.parseInt(sesion)).orElse(new Usuario());
		
		if (sesion != null) {
			m.addAttribute("usuario", u.getNombreUsuario());
			m.addAttribute("roles", rolRepo.findAll());
			m.addAttribute("usuarioModelo", repo.findById(u.getIdUsuario()));
			url = "perfil";
		} else {
			url = "redirect:/";
		}
		
		return url;
	}
}