package com.example.pov.pov.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.example.pov.pov.servicios.CategoriaService;
import com.example.pov.pov.servicios.FileService;
import com.example.pov.pov.servicios.ProductoService;
import com.example.pov.pov.servicios.UsuarioService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.example.pov.pov.entidades.Categoria;
import com.example.pov.pov.entidades.Producto;
import com.example.pov.pov.entidades.Usuario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    FileService fileService;

    @GetMapping("")
    public String adminPage(Model model) {
        String nombreUsuario = usuarioService.getNombreUsuario();

        if(nombreUsuario == null) {
            return "redirect:/iniciar-sesion";
        }

        Usuario usuario = usuarioService.buscarUsuarioEmail(nombreUsuario);

        if(!usuario.getRol().getNombreRol().equals("ADMIN")) {
            return "redirect:/";
        }

        model.addAttribute("usuario", usuario);

        return "public/admin";
    }

    @PostMapping("/borrar/{id}")
    public String borrarProducto(@PathVariable Integer id) {
        productoService.borrarProducto(id);
        return "redirect:/admin";
    }

    @PostMapping("/añadir")
    public String añadirProducto(@ModelAttribute Producto producto, @RequestParam("imagenFile") MultipartFile imagen, Model modelo) {
        if (!imagen.isEmpty()) {
            fileService.saveFile(imagen);
            producto.setImagen("/images/" + imagen.getOriginalFilename());
            productoService.guardarProducto(producto);
        } else {
            modelo.addAttribute("error", "Error al subir la imagen: ");
        }
        
        return "redirect:/admin";
    }

    @PostMapping("/añadirCategoria")
    public String añadirCategoria(@RequestParam String nombre) {
        categoriaService.guardar(new Categoria(nombre));
        return "redirect:/admin";
    }

    @ModelAttribute
    public void getProducts(Model model) {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        model.addAttribute("productos", productos);
    }

    @ModelAttribute("categorias")
    public void getCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.obtenerTodas());
    }

}