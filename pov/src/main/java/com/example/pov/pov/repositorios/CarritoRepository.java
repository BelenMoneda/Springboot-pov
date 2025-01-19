package com.example.pov.pov.repositorios;

import com.example.pov.pov.entidades.Carrito;
import com.example.pov.pov.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByUsuario(Usuario usuario);
    Optional<Carrito> findByUsuarioAndEstado(Usuario usuario, Boolean estado);
}
