package com.example.pov.pov.controladores;

import com.example.pov.pov.entidades.Carrito;
import com.example.pov.pov.entidades.Pedido;
import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.servicios.CarritoService;
import com.example.pov.pov.servicios.PedidoService;
import com.example.pov.pov.servicios.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("")
    public String paginaPedidos(Model model) {
        return "public/pedido";
    }

    @ModelAttribute("usuario")
    public String usuario(Model model) {
        String nombreUsuario = usuarioService.getNombreUsuario();
        
        if(nombreUsuario == null) {
            return "Anónimo";
        }

        Usuario usuario = usuarioService.buscarUsuarioEmail(nombreUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("esAdmin", usuario.getRol().getNombreRol() == "ADMIN");

        return usuario.getNombreUsuario(); 
    }


    // @GetMapping("/confirmar")
    // public String confirmarPedido(Model model) {
    //     Carrito carrito = carritoService.obtenerCarritoActual();
    //     Usuario usuario = usuarioService.getUsuarioActual();

    //     if (carrito == null || carrito.getItems().isEmpty()) {
    //         model.addAttribute("error", "El carrito está vacío.");
    //         return "redirect:/carrito";
    //     }

    //     model.addAttribute("carrito", carrito);
    //     model.addAttribute("total", carritoService.calcularTotalCarrito(carrito));
    //     return "pedido/confirmar";
    // }

    // @PostMapping("/confirmar")
    // public String realizarPedido(Model model) {
    //     Carrito carrito = carritoService.obtenerCarritoActual();
    //     Usuario usuario = usuarioService.getUsuarioActual();

    //     if (carrito == null || carrito.getItems().isEmpty()) {
    //         model.addAttribute("error", "El carrito está vacío.");
    //         return "redirect:/carrito";
    //     }

    //     pedidoService.crearPedido(carrito, usuario);
    //     carritoService.vaciarCarrito(carrito);

    //     return "redirect:/productos?pedidoExitoso=true";
    // }
}
