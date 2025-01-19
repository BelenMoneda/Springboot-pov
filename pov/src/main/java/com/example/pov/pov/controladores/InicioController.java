package com.example.pov.pov.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.pov.pov.entidades.ItemCarrito;
import com.example.pov.pov.entidades.Producto;
import com.example.pov.pov.entidades.Rol;
import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.repositorios.RolRepository;
import com.example.pov.pov.servicios.CarritoService;
import com.example.pov.pov.servicios.ProductoService;
import com.example.pov.pov.servicios.UsuarioService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.servlet.http.HttpSession;

import com.example.pov.pov.entidades.Carrito;

@Controller

public class InicioController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private CarritoService carritoService;

    @Autowired
    private RolRepository rolRepository;

    // @GetMapping("/")
    // public String inicio(Model model) {
    //     List<Producto> productos = productoService.obtenerTodosLosProductos();
    //     model.addAttribute("productos", productos);
    //     return "public/index"; 
    // }

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


    // Método para mostrar la página principal
    @GetMapping("/")
    public String inicio(Model model, HttpSession session) {
        String nombreUsuario = usuarioService.getNombreUsuario();
        
        if(nombreUsuario != null) {
            Usuario usuario = usuarioService.buscarUsuarioEmail(nombreUsuario);
            Carrito carrito = carritoService.obtenerCarritoDeUsuarioActivo(usuario);

            if(carrito == null) {
                carrito = new Carrito(usuario);
                carritoService.guardarCarrito(carrito);
            }

            session.setAttribute("carrito", carrito);
            model.addAttribute("carrito", carrito);
            model.addAttribute("tamano", carritoService.obtenerTamañoCarrito(carrito));
        }

        List<Producto> productos = productoService.obtenerTodosLosProductos();
        model.addAttribute("productos", productos);
        return "public/index";
    }

    @GetMapping("/irCarrito")
    public String redirigirAlCarrito(Model model, HttpSession session) {
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }

        model.addAttribute("carrito", carrito);
        return "public/carrito"; // Cambia a la vista correspondiente de tu carrito
    }
    
}


