package com.cibertec.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			Producto p = prodRepo.findById((long) dp.getId_producto()).orElse(new Producto());
			
			DetallePedidoTemp dpt = new DetallePedidoTemp();
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
}
