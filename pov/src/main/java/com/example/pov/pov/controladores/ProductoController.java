package com.example.pov.pov.controladores;
import com.example.pov.pov.entidades.Producto;
import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.servicios.ProductoService;
import com.example.pov.pov.servicios.UsuarioService;

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

    @GetMapping
    public String inicio(Model model,
                         @RequestParam(value = "nombre", required = false) String nombre,
                         @RequestParam(value = "precioMin", required = false) Double precioMin,
                         @RequestParam(value = "precioMax", required = false) Double precioMax,
                         @RequestParam(value = "categoria", required = false) String categoria) {
        List<Producto> productos = productoService.obtenerProductosFiltrados(nombre, precioMin, precioMax, categoria);
        model.addAttribute("productos", productos);
        return "public/listaProductos"; 
    }

    @ModelAttribute("usuario")
    public String usuario() {
        String nombreUsuario = usuarioService.getNombreUsuario();

        if(nombreUsuario == null) {
            return "Anónimo";
        }

        Usuario usuario = usuarioService.buscarUsuarioEmail(nombreUsuario);

        return usuario.getNombreUsuario(); 
    }

    @GetMapping("/{id}")
	public String paginaProducto(@PathVariable Integer id, Model model) {
		Producto p = productoService.obtenerProductoPorId(id);
        List<Producto> productos = productoService.obtenerProductosAleatorios();
        model.addAttribute("producto", p);
        model.addAttribute("otrosProductos", productos);

		return "public/detalleProducto";
	}
}
