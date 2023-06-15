package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String todos(Model m) {
		m.addAttribute("lstProds", prodRepo.findAll());
		return "listadoProductos";
	}
	
	@GetMapping("/productosHamburguesas")
	public String listHamburguesas(Model m) {
		m.addAttribute("lstProds", prodRepo.findByIdCategoriaProd(1));
		return "listadoProductosHamburguesas";
	}
	
	@GetMapping("/productosBebidas")
	public String listBebidas(Model m) {
		m.addAttribute("lstProds", prodRepo.findByIdCategoriaProd(2));
		return "listadoProductosBebidas";
	}
	
	@GetMapping("/productosComplementos")
	public String listComplementos(Model m) {
		m.addAttribute("lstProds", prodRepo.findByIdCategoriaProd(3));
		return "listadoProductosComplementos";
	}
	
	@GetMapping("/productosAdicionales")
	public String listAdicionales(Model m) {
		m.addAttribute("lstProds", prodRepo.findByIdCategoriaProd(4));
		return "listadoProductosAdicionales";
	}
	
	@GetMapping("/nuevoProducto")
	public String nuevoProducto(Model m) {
		m.addAttribute("catProd", catProdRepo.findAll());
		m.addAttribute("producto", new Producto());
		return "nuevoProducto";
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
	public String editarProducto(Model m, @RequestParam("idProd") int idProd) {
		m.addAttribute("catProd", catProdRepo.findAll());
		m.addAttribute("producto", prodRepo.findById(idProd));
		
		return "editarProducto";
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