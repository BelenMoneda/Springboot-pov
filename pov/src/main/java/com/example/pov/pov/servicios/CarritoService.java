package com.example.pov.pov.servicios;

import com.example.pov.pov.entidades.Carrito;
import com.example.pov.pov.entidades.ItemCarrito;
import com.example.pov.pov.entidades.Producto;
import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.repositorios.CarritoRepository;
import com.example.pov.pov.repositorios.ProductoRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {
    private final ProductoRepository productoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public CarritoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Carrito obtenerCarritoDeUsuario(Usuario usuario) {
        return carritoRepository.findByUsuario(usuario).orElse(null);
    }
    
    public Carrito obtenerCarritoDeUsuarioActivo(Usuario usuario) {
        Carrito carrito = carritoRepository.findByUsuarioAndEstado(usuario, true).orElse(null);

        if (carrito == null) {
            carrito = new Carrito(usuario);
            carritoRepository.save(carrito);
        }
        
        return carrito;
    }

    public Carrito guardarCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    // Método público para obtener el carrito desde la sesión
    public Carrito obtenerCarritoDeSesion(HttpSession session) {
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        Usuario usuario = usuarioService.buscarUsuarioNombreUsuario(usuarioService.getNombreUsuario());

        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
            carrito.setUsuario(usuario);
            carritoRepository.save(carrito);
        }

        return carrito;
    }

    public void agregarProductoAlCarrito(HttpSession session, Integer idProducto, Integer cantidad) {
        Carrito carrito = obtenerCarritoDeSesion(session);
        List<ItemCarrito> items = carrito.getItems();

        Optional<ItemCarrito> itemExistente = items.stream()
                .filter(item -> item.getProducto().getIdProducto().equals(idProducto))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            ItemCarrito nuevoItem = new ItemCarrito(producto, cantidad);
            items.add(nuevoItem);
        }
        session.setAttribute("carrito", carrito); // Asegura que el carrito actualizado se guarde en la sesión
        carritoRepository.save(carrito);
    }

    public void actualizarProductoCarrito(HttpSession session, Integer idProducto, Integer cantidad) {
        Carrito carrito = obtenerCarritoDeSesion(session);
        List<ItemCarrito> items = carrito.getItems();

        Optional<ItemCarrito> itemExistente = items.stream()
                .filter(item -> item.getProducto().getIdProducto().equals(idProducto))
                .findFirst();

        ItemCarrito item = itemExistente.get();
        item.setCantidad(cantidad);

        session.setAttribute("carrito", carrito);
        carritoRepository.save(carrito);
    }

    public void eliminarProductoDelCarrito(HttpSession session, Integer idProducto) {
        Carrito carrito = obtenerCarritoDeSesion(session);
        carrito.getItems().removeIf(item -> item.getProducto().getIdProducto().equals(idProducto));
        session.setAttribute("carrito", carrito); // Asegura que el carrito actualizado se guarde en la sesión
    }

    public void vaciarCarrito(HttpSession session) {
        Carrito carrito = obtenerCarritoDeSesion(session);
        carrito.getItems().clear();
        session.setAttribute("carrito", carrito); // Asegura que el carrito actualizado se guarde en la sesión
    }

    public double calcularTotalCarrito(Carrito carrito) {
        return carrito.getItems().stream()
                .mapToDouble(item -> item.getProducto().getPrecioUnitario() * item.getCantidad())
                .sum();
    }

    public int obtenerTamañoCarrito(Carrito carrito) {
        return carrito.getItems().stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }
}
