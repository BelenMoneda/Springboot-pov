package com.example.pov.pov.entidades;
import jakarta.persistence.*;

@Entity
@Table(name = "METODO_PAGO")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMetodoPago;

    @Column(nullable = false, length = 50)
    private String nombreMetodoPago;

    public MetodoPago(Integer idMetodoPago, String nombreMetodoPago) {
        this.idMetodoPago = idMetodoPago;
        this.nombreMetodoPago = nombreMetodoPago;
    }

    public MetodoPago() {
    }

    public Integer getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

}

