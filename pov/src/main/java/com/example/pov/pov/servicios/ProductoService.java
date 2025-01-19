package com.example.pov.pov.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pov.pov.entidades.Producto;
import com.example.pov.pov.repositorios.ProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }
    
    public List<Producto> obtenerProductosFiltrados(String nombre, Double precioMin, Double precioMax, String categoria) {
        return productoRepository.filtrarProductos(nombre, precioMin, precioMax, categoria);
    }
    
    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }

    public List<Producto> obtenerProductosAleatorios() {
        List<Producto> productos = obtenerTodosProductos();
        List<Producto> productosAleatorios = new ArrayList<>();
        Random random = new Random();
        List<Integer> indices = new ArrayList<>();
        
        while (indices.size() < 5 && indices.size() < productos.size()) {
            int randomIndex = random.nextInt(productos.size());
            if (!indices.contains(randomIndex)) {
                indices.add(randomIndex);
            }
        }

        for (Integer index : indices) {
            productosAleatorios.add(productos.get(index));
        }

        return productosAleatorios;
    }

    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public void borrarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

}

