package com.example.pov.pov.controladores;

import com.example.pov.pov.entidades.Carrito;
import com.example.pov.pov.entidades.ItemCarrito;
import com.example.pov.pov.entidades.Pedido;
import com.example.pov.pov.entidades.Producto;
import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.servicios.CarritoService;
import com.example.pov.pov.servicios.PedidoService;
import com.example.pov.pov.servicios.ProductoService;
import com.example.pov.pov.servicios.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProductoService productoService;

    @Autowired
    PedidoService pedidoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // Ver el carrito (usando la sesión)
    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        Usuario usuario = usuarioService.buscarUsuarioEmail(usuarioService.getNombreUsuario());
        Carrito carrito = carritoService.obtenerCarritoDeUsuarioActivo(usuario);

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", carritoService.calcularTotalCarrito(carrito));
        model.addAttribute("tamano", carritoService.obtenerTamañoCarrito(carrito));

        return "public/carrito";
    }

    // Agregar un producto al carrito
    @PostMapping("/agregar/{id}")
    public String agregarProducto(
        @PathVariable Integer id, 
        @RequestParam Integer cantidad, 
        HttpSession session, 
        HttpServletRequest request
    ) {
        String nombreUsuario = usuarioService.getNombreUsuario();

        if (nombreUsuario == null) {
            return "public/login";
        }

        Usuario usuario = usuarioService.buscarUsuarioEmail(nombreUsuario);
        Carrito carrito = carritoService.obtenerCarritoDeUsuarioActivo(usuario);
        Producto producto = productoService.obtenerProductoPorId(id);
        ItemCarrito item = new ItemCarrito(producto, cantidad);

        carrito.agregarItem(item);
        carritoService.guardarCarrito(carrito);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProductoCarrito(
        @PathVariable Integer id, 
        @RequestParam Integer cantidad, 
        HttpSession session, 
        HttpServletRequest request
    ) {
        String nombreUsuario = usuarioService.getNombreUsuario();
        Usuario usuario = usuarioService.buscarUsuarioEmail(nombreUsuario);
        Carrito carrito = carritoService.obtenerCarritoDeUsuarioActivo(usuario);

        carrito.actualizarItem(id, cantidad);
        carritoService.guardarCarrito(carrito);
        return "redirect:" + request.getHeader("referer");
    }

    // Eliminar un producto del carrito
    @PostMapping("/eliminar")
    public String eliminarProducto(
        HttpSession session, 
        @RequestParam Integer idProducto,
        HttpServletRequest request
    ) {
        String nombreUsuario = usuarioService.getNombreUsuario();
        Usuario usuario = usuarioService.buscarUsuarioEmail(nombreUsuario);
        Carrito carrito = carritoService.obtenerCarritoDeUsuarioActivo(usuario);
        ItemCarrito item = carrito.getItems().stream()
                .filter(i -> i.getProducto().getIdProducto().equals(idProducto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        carrito.eliminarItem(item);
        carritoService.guardarCarrito(carrito);
        return "redirect:" + request.getHeader("referer");
    }

    // Vaciar el carrito
    @PostMapping("/vaciar")
    public ResponseEntity<?> vaciarCarrito(HttpSession session) {
        carritoService.vaciarCarrito(session);
        return ResponseEntity.ok("Carrito vaciado");
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
    
    @GetMapping("/finalizar")
    public String finalizarPedido(Model model) {
        String nombreUsuario = usuarioService.getNombreUsuario();
        Usuario usuario = usuarioService.buscarUsuarioEmail(nombreUsuario);
        Carrito carrito = carritoService.obtenerCarritoDeUsuarioActivo(usuario);

        Double total = carritoService.calcularTotalCarrito(carrito);
        Pedido pedido = new Pedido("", total, "FINALIZADO", "", "PENDIENTE", usuario);

        pedidoService.guardarPedido(pedido);
        carrito.setEstado(false);
        carritoService.guardarCarrito(carrito);

        model.addAttribute("carrito", carrito);

        return "public/pago";
    }
}
