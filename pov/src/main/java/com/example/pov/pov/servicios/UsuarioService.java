package com.example.pov.pov.servicios;

import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.repositorios.UsuarioRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
	BCryptPasswordEncoder passwordEncoder;

    

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioEmail(String usuario) {
        return usuarioRepository.findByEmail(usuario);
    }

    public Usuario buscarUsuarioNombreUsuario(String usuario) {
        return usuarioRepository.findByNombreUsuario(usuario);
    }

    
    public String getNombreUsuario() {
        try {
            User usuario = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(usuario == null) {
                return null;
            }

            String name = usuario.getUsername();
            return name;
        } catch(Exception e) {
            return null;
        }
    }

    
    public boolean autenticarUsuario(String nombreUsuario, String contraseña) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(buscarUsuarioNombreUsuario(nombreUsuario));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Compara la contraseña ingresada con la almacenada en la base de datos
            return passwordEncoder.matches(contraseña, usuario.getContraseña());
        }
        return false;
    }

}
