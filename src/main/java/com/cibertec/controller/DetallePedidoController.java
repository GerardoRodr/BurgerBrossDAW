package com.cibertec.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class DetallePedidoController {
	@Autowired
	private IDetallePedidoRepository detPedidoRepo;
	
	@Autowired
	private IPedidoRepository pedidoRepo;
	
	@Autowired
	private IProductoRepository prodRepo;
	
	private List<DetallePedidoTemp> llenarDetallePedidoTemp(int idPedido) {
		
		List<DetallePedidoTemp> tempDetPed = new ArrayList<>();
		
		for (DetallePedido dp : detPedidoRepo.findByIdPedido(idPedido)) {
			Producto p = prodRepo.findById(dp.getId_producto()).orElse(new Producto());
			
			DetallePedidoTemp dpt = new DetallePedidoTemp();
			dpt.setIdDetallePedido(dp.getId_detalle_pedido());
			dpt.setId_producto(dp.getId_producto());
			dpt.setNombreProducto(p.getNombreProducto());
			dpt.setPrecio_producto(dp.getPrecio_producto());
			dpt.setCantidad(dp.getCantidad());
			dpt.setSubtotal(dp.getSubtotal());
			
			tempDetPed.add(dpt);
		}
		
		return tempDetPed;
	}
	
	@GetMapping("/verDetallePedido")
	public String verDetallePedido(Model m, @RequestParam("idPedido") int idPedido) {
		
		Pedido p = pedidoRepo.findById(idPedido).orElse(new Pedido());
		
		m.addAttribute("totalPedido", p.getTotal_pedido());
		m.addAttribute("nombreCliente", p.getNombre_cliente());
		m.addAttribute("lstDetPedidos", llenarDetallePedidoTemp(idPedido));
		
		return "verDetallePedido";
	}
	
	@GetMapping("/editarPedido")
	public String editarPedido(@RequestParam("idPedido") int idPedido, Model m) {
		
		Pedido p = pedidoRepo.findById(idPedido).orElse(new Pedido());
		
		m.addAttribute("totalPedido", p.getTotal_pedido());
		m.addAttribute("nombreCliente", p.getNombre_cliente());
		m.addAttribute("lstDetPedidos", llenarDetallePedidoTemp(idPedido));
		
		return "editarPedido";
	}
	
	@GetMapping("/editarDetallePedido")
	public String editarDetallePedido(@RequestParam("idDetPedido") int idDetPedido, @RequestParam("idProducto") int idProducto, Model m) {
		m.addAttribute("selProd", prodRepo.findAll());
		m.addAttribute("det_ped", detPedidoRepo.findById(idDetPedido));
		
		return "editarDetallePedido";
	}
	
	private BigDecimal calcularTotal(List<DetallePedido> list) {
		BigDecimal total = new BigDecimal(0);
		
		for (DetallePedido dp : list) {
			total = total.add(dp.getSubtotal());
		}
		
		return total;
	}
	
	@PostMapping("/editarDetallePedido")
	public String editarDetallePedidoPost(@ModelAttribute DetallePedido dp, RedirectAttributes redirect) {
		//INSTANCIAMOS EL PRODUCTO CON LA ID ACTUALIZADA
		Producto prod = prodRepo.findById(dp.getId_producto()).orElse(new Producto());
		
		//BUSCAMOS LA FILA EN LA BASE DE DATOS CORRESPONDIENTE A SU ID ENVIADA DESDE EL FORMULARIO
		DetallePedido detPed = detPedidoRepo.findById(dp.getId_detalle_pedido()).orElse(new DetallePedido());
		
		//ACTUALIZAMOS LOS DATOS
		detPed.setId_producto(dp.getId_producto());
		detPed.setCantidad(dp.getCantidad());
		detPed.setPrecio_producto(prod.getPrecioProducto());
		
		//CALCULAMOS EL SUBTOTAL
		BigDecimal subtotal = detPed.getPrecio_producto().multiply(BigDecimal.valueOf(detPed.getCantidad()));
		//ACTUALIZAMOS EL SUBTOTAL
		detPed.setSubtotal(subtotal);
		
		//GUARDAMOS LOS CAMBIOS EN LA BD
		detPedidoRepo.save(detPed);
		//(Acá aun faltaria actualizar el total del pedido en la tabla tb_pedidos xd)
		
		//DESPUES DE ACTUALIZAR EL DETALLEPEDIDO, BUSCAMOS TODOS LOS DETALLESPEDIDOS CON EL IDPEDIDO DE NUESTRA PREFERENCIA
		List<DetallePedido> listDetallePedido = detPedidoRepo.findByIdPedido(dp.getIdPedido());
		
		//LLAMAMOS AL PEDIDO DENTRO DE LA TBL_PEDIDOS
		Pedido p = pedidoRepo.findById(dp.getIdPedido()).orElse(new Pedido());
		//CALCULAMOS EL TOTAL
		p.setTotal_pedido(calcularTotal(listDetallePedido));
		//GUARDAMOS EL DATO
		pedidoRepo.save(p);
		
		//PARA EL REDIRECT NECESITO ENVIAR EL DATO idPedido:
		redirect.addAttribute("idPedido", dp.getIdPedido());
		
		return "redirect:/editarPedido";
	}
}
