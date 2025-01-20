package com.example.pov.pov.controladores;
import com.example.pov.pov.entidades.Carrito;
import com.example.pov.pov.entidades.Categoria;
import com.example.pov.pov.entidades.Producto;
import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.servicios.CarritoService;
import com.example.pov.pov.servicios.CategoriaService;
import com.example.pov.pov.servicios.ProductoService;
import com.example.pov.pov.servicios.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String inicio(
            Model model,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "precioMin", required = false) Double precioMin,
            @RequestParam(value = "precioMax", required = false) Double precioMax,
            @RequestParam(value = "categoria", required = false) String categoria,
            HttpSession session
        ) {

        System.out.println("Categoria: " + categoria);

        List<Categoria> categorias = categoriaService.obtenerTodas(); 
        List<Producto> productos = productoService.obtenerProductosFiltrados(nombre, precioMin, precioMax, categoria);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);

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
            model.addAttribute("usuario", usuario);
        }

        return "public/listaProductos"; 
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

    @GetMapping("/{id}")
    public String paginaProducto(@PathVariable Integer id, Model model, HttpSession session) {
        Producto p = productoService.obtenerProductoPorId(id);
            List<Producto> productos = productoService.obtenerProductosAleatorios();
            model.addAttribute("producto", p);
            model.addAttribute("otrosProductos", productos);

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
            model.addAttribute("usuario", usuario);
        }

        return "public/detalleProducto";
    }
    
}
