package com.cibertec.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.model.DetallePedido;
import com.cibertec.model.DetallePedidoTemp;
import com.cibertec.model.Pedido;
import com.cibertec.model.Producto;
import com.cibertec.repository.IDetallePedidoRepository;
import com.cibertec.repository.IPedidoRepository;
import com.cibertec.repository.IProductoRepository;

@Controller
public class PedidoController {
	@Autowired
	private IPedidoRepository pedidoRepo;
	
	@Autowired
	private IProductoRepository prodRepo;
	
	@Autowired
	private IDetallePedidoRepository detPedidoRepo;
	
	List<DetallePedidoTemp> tempDetPed = new ArrayList<>();
	
	@GetMapping("/")
	public String index(Model m) {
		m.addAttribute("lstPedidos", pedidoRepo.findAll());
		return "index";
	}
	
	@GetMapping("/nuevoPedidoNombre")
	public String nuevoPedido1() {
		return "nuevoPedidoNombre";
	}
	
	@GetMapping("/nuevoPedido")
	public String nuevoPedido2(Model m, @RequestParam("nombreCliente") String nombreCliente) {
		m.addAttribute("selProd", prodRepo.findAll());

		m.addAttribute("nombreCliente", nombreCliente);
		
		m.addAttribute("det_pedido", new DetallePedidoTemp());
		
		return "nuevoPedidoProducto";
	}
	
	private DetallePedidoTemp llenarDetalle(DetallePedidoTemp det) {
		DetallePedidoTemp detFinal = new DetallePedidoTemp();
		Producto p = prodRepo.findById((long) det.getId_producto()).orElse(new Producto());
		detFinal.setId_producto(det.getId_producto());
		detFinal.setNombreProducto(p.getNombreProducto());
		detFinal.setPrecio_producto(p.getPrecioProducto());
		detFinal.setCantidad(det.getCantidad());
		detFinal.setSubtotal(p.getPrecioProducto().multiply(BigDecimal.valueOf(det.getCantidad())));
		return detFinal;
	}
	
	//BOTON SIGUIENTE
	@PostMapping("/nuevoPedidoSiguiente")
	public String nuevoPedido2Post(@ModelAttribute DetallePedidoTemp detPedido, RedirectAttributes r, @RequestParam("nombreCliente") String nombreCliente) {
		tempDetPed.add(llenarDetalle(detPedido));
		r.addAttribute("nombreCliente", nombreCliente);
		return "redirect:/nuevoPedidoFinalizar";
	}
	
	@GetMapping("/nuevoPedidoFinalizar")
	public String nuevoPedido3(Model m, @RequestParam("nombreCliente") String nombreCliente) {
		
		m.addAttribute("selProd", prodRepo.findAll());
		
		m.addAttribute("nombreCliente", nombreCliente);
		
		m.addAttribute("det_pedido", new DetallePedido());
		
		m.addAttribute("listaPedidosTemp", tempDetPed);
		
		return "nuevoPedidoProducto2";
	}
	
	private BigDecimal calcularTotal(List<DetallePedidoTemp> list) {
		BigDecimal total = new BigDecimal(0);
		
		for (DetallePedidoTemp dp : list) {
			total = total.add(dp.getSubtotal());
		}
		
		return total;
	}
	
	@PostMapping("/finalizarPedido")
	public String finalizarPedido(@RequestParam("nombreCliente") String nombreCliente) {
		
		//INSERTAMOS EL PEDIDO EN LA BASE DE DATOS
		Pedido pedido = new Pedido();
		pedido.setTotal_pedido(calcularTotal(tempDetPed));
		pedido.setNombre_cliente(nombreCliente);
		pedido.setFecha_pedido(new Date());
		pedido.setEstado_pedido(false);
		pedidoRepo.save(pedido);
		
		//CONSULTO EL ULTIMO PEDIDO PARA SACARLE LA ID (ES DECIR EL QUE ACABAMOS DE INGRESAR)
		Pedido ultimoPedido = pedidoRepo.findUltimoPedido();
		
		//INSERTAMOS EL DETALLE PEDIDO EN LA BASE DE DATOS
		for (DetallePedidoTemp dp : tempDetPed) {
			DetallePedido objDP = new DetallePedido();
			objDP.setIdPedido(ultimoPedido.getId_pedido());
			objDP.setId_producto(dp.getId_producto());
			objDP.setPrecio_producto(dp.getPrecio_producto());
			objDP.setCantidad(dp.getCantidad());
			objDP.setSubtotal(dp.getSubtotal());
			
			detPedidoRepo.save(objDP);
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/cambiarEstado")
	public String cambiarEstado(@RequestParam("estado") Boolean estado, @RequestParam("idPedido") int idPedido) {
		
		//BUSCAMOS EL PEDIDO POR ID Y LO ALMACENAMOS EN UN OBJETO
		Pedido p = pedidoRepo.findById(idPedido).orElse(new Pedido());
		
		//ACTUALIZAMOS EL DATO
		p.setEstado_pedido(estado);
		
		//GUARDAMOS LOS CAMBIOS
		pedidoRepo.save(p);
		
		return "redirect:/";
	}
}