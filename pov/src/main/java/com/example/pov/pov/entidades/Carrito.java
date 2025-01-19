package com.example.pov.pov.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CARRITO")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarrito;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items = new ArrayList<>();

    @Column(name = "estado", nullable = false)
    private boolean estado;

    public Carrito() {
        this.estado = true;
    }

    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        this.estado = true;
    }

    public Integer getIdCarrito() {
        return idCarrito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // Método para agregar un producto al carrito
    public void agregarItem(ItemCarrito item) {
        this.items.add(item);
        item.setCarrito(this);
    }

    // Método para actualizar la cantidad de un producto en el carrito
    public void actualizarItem(Integer idProducto, Integer cantidad) {
        this.items.stream()
                .filter(item -> item.getProducto().getIdProducto().equals(idProducto))
                .findFirst()
                .ifPresent(item -> item.setCantidad(cantidad));
    }

    // Método para eliminar un producto del carrito
    public void eliminarItem(ItemCarrito item) {
        this.items.remove(item);
        item.setCarrito(null);
    }

    // Método para obtener el tamaño del carrito (cantidad total de items)
    public int obtenerTamañoCarrito() {
        return this.items.size();
    }

    // Método para calcular el total del carrito
    public double calcularTotalCarrito() {
        return this.items.stream()
                .mapToDouble(item -> item.getProducto().getPrecioUnitario() * item.getCantidad())
                .sum();
    }
}
