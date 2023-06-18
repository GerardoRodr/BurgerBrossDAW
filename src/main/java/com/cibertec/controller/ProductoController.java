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

import com.cibertec.model.Producto;
import com.cibertec.repository.ICategoriaProductosRepository;
import com.cibertec.repository.IProductoRepository;

@Controller
public class ProductoController {
	@Autowired
	private IProductoRepository prodRepo;
	
	@Autowired
	private ICategoriaProductosRepository catProdRepo;
	
	@GetMapping("/listadoProductos")
	public String todos(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		
		if (sesion != null) {
			m.addAttribute("lstProds", prodRepo.findAll());
			url = "listadoProductos";
		} else {
			url = "redirect:/";
		}
		return url;
	}
	
	@GetMapping("/productosHamburguesas")
	public String listHamburguesas(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		
		if (sesion != null) {
			m.addAttribute("lstProds", prodRepo.findByIdCategoriaProd(1));
			url = "listadoProductosHamburguesas";
		}
		else {
			url = "redirect:/";
		}
		return url;
	}
	
	@GetMapping("/productosBebidas")
	public String listBebidas(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		
		if (sesion != null) {
			m.addAttribute("lstProds", prodRepo.findByIdCategoriaProd(2));
			url = "listadoProductosBebidas";
		} else {
			url = "redirect:/";
		}
		return url;
	}
	
	@GetMapping("/productosComplementos")
	public String listComplementos(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		
		if (sesion != null) {
			m.addAttribute("lstProds", prodRepo.findByIdCategoriaProd(3));
			url = "listadoProductosComplementos";
		} else {
			url = "redirect:/";
		}
		return url;
	}
	
	@GetMapping("/productosAdicionales")
	public String listAdicionales(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		
		if (sesion != null) {
			m.addAttribute("lstProds", prodRepo.findByIdCategoriaProd(4));
			url = "listadoProductosAdicionales";
		} else {
			url = "redirect:/";
		}
		return url;
	}
	
	@GetMapping("/nuevoProducto")
	public String nuevoProducto(Model m, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		
		if (sesion != null) {
			m.addAttribute("catProd", catProdRepo.findAll());
			m.addAttribute("producto", new Producto());
			url = "nuevoProducto";
		} else {
			url = "redirect:/";
		}
		return url;
	}
	
	@PostMapping("/nuevoProducto")
	public String nuevoProductoPost(@ModelAttribute Producto producto, Model m) {
		try {
			prodRepo.save(producto);
		} catch (Exception e) {
			m.addAttribute("mensaje", "Hubo un error en el registro.");
			e.printStackTrace();
		}
		
		return "redirect:/listadoProductos";
	}
	
	@GetMapping("/editarProducto")
	public String editarProducto(Model m, @RequestParam("idProd") int idProd, @CookieValue(value = "sesion", required = false) String sesion) {
		String url;
		
		if (sesion != null) {
			m.addAttribute("catProd", catProdRepo.findAll());
			m.addAttribute("producto", prodRepo.findById(idProd));
			url = "editarProducto";
		} else {
			url = "redirect:/";
		}
		
		return url;
	}
	
	@PostMapping("/editarProducto")
	public String editarProductoPost(@ModelAttribute Producto producto, Model m) {
		try {
			prodRepo.save(producto);
		} catch (Exception e) {
			m.addAttribute("mensaje", "Hubo un error en el registro.");
			e.printStackTrace();
		}
		
		return "redirect:/listadoProductos";
	}
	
	@PostMapping("/eliminarProducto")
	public String eliminarProducto(RedirectAttributes m, @RequestParam("idProd") int idProd) {
		try {
			prodRepo.deleteById(idProd);
		} catch (Exception e) {
			m.addFlashAttribute("mensaje", "Recuerda que para eliminar un producto debes asegurarte de que este no exista en algun pedido.");
		}
		
		return "redirect:/listadoProductos";
	}
}