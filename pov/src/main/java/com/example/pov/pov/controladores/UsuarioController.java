package com.example.pov.pov.controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.pov.pov.entidades.Rol;
import com.example.pov.pov.entidades.Usuario;
import com.example.pov.pov.repositorios.RolRepository;
import com.example.pov.pov.repositorios.UsuarioRepository;
import com.example.pov.pov.servicios.UsuarioService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;


@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;


    @GetMapping("/registrar")
    public String registrarRedirect(Usuario usuario) {
        return "public/registro";
    }

    @GetMapping("/iniciar-sesion")
    public String iniciarSesionRedirect(Usuario usuario) {
        return "public/login";
    }

    @ModelAttribute
    public void loginUsuario(Model model, @RequestParam(name="error", required=false) String errorMessage) {
        if(errorMessage != null) {
            System.out.println("Error message: " + errorMessage);
            model.addAttribute("errorMessage", "Email o contraseña incorrectos");
        }

        model.addAttribute("usuario", new Usuario());
    }

    @PostMapping("/usuarios/registrar")
    public String registroUsuario(@ModelAttribute Usuario user) {
	Rol userRol = rolRepository.findByNombreRol("USER");
        user.setRol(userRol);
        usuarioService.registrarUsuario(user);

        return "redirect:/";
    }
}
