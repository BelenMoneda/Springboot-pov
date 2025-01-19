package com.example.pov.pov.entidades;
import jakarta.persistence.*;

@Entity
@Table(name = "PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column(length = 100)
    private String direccion;

    @Column(nullable = false)
    private Double precioTotal;

    @Column(nullable = false, length = 20)
    private String estadoPedido;

    @Column(nullable = false)
    private String fechaPedido;

    @Column(nullable = false, length = 50)
    private String estadoPago;

    public Pedido(String direccion, Double precioTotal, String estadoPedido, String fechaPedido, String estadoPago, Usuario usuario) {
        this.direccion = direccion;
        this.precioTotal = precioTotal;
        this.estadoPedido = estadoPedido;
        this.fechaPedido = fechaPedido;
        this.estadoPago = estadoPago;
        this.usuario = usuario;
    }

    public Pedido(Integer idPedido, String nombreUsuario, String apellidos, String email, String direccion,
            Double precioTotal, String estadoPedido, String fechaPedido, String estadoPago, Usuario usuario) {
        this.idPedido = idPedido;
        this.direccion = direccion;
        this.precioTotal = precioTotal;
        this.estadoPedido = estadoPedido;
        this.fechaPedido = fechaPedido;
        this.estadoPago = estadoPago;
        this.usuario = usuario;
    }

    public Pedido() {
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }


}
