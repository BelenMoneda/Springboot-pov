package com.example.pov.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import com.example.pov.pov.entidades.Producto;
// import com.example.pov.pov.servicios.ProductoService;

// import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
public class ProductoController {

    @GetMapping("/productos")
    public String listarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) String categoria,
            Model model) {

        return "public/listaProductos";
    }
}
