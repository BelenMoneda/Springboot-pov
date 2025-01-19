package com.example.pov.pov.entidades;
import jakarta.persistence.*;

@Entity
@Table(name = "ROL")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(nullable = false, length = 20)
    private String nombreRol;

    public Rol() { }

    public Rol(Integer idRol) {
        this.nombreRol = "USER";
    }

    public Rol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

}

