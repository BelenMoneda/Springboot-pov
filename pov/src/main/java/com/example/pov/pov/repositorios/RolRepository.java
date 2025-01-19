package com.example.pov.pov.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pov.pov.entidades.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombreRol(String nombre);
}
