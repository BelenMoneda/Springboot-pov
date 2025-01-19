package com.example.pov.pov.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pov.pov.entidades.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String usuario);
    Usuario findByNombreUsuario(String nombreUsuario);
}
