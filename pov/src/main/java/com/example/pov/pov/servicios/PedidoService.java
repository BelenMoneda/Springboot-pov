package com.example.pov.pov.servicios;

import com.example.pov.pov.entidades.Carrito;
import com.example.pov.pov.entidades.Pedido;
import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.repositorios.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // public void procesarPedido(Carrito carrito) {
    //     Pedido pedido = new Pedido();
    //     pedido.setUsuario(carrito.getUsuario());
    //     pedido.setItems(carrito.getItems());
    //     pedido.setTotal(carrito.getItems().stream()
    //             .mapToDouble(item -> item.getCantidad() * item.getProducto().getPrecioUnitario())
    //             .sum());

    //     pedidoRepository.save(pedido);
    // }

    public Pedido guardarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
