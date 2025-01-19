package com.example.pov.pov;

import com.example.pov.pov.entidades.*;
import com.example.pov.pov.repositorios.*;
import com.example.pov.pov.servicios.UsuarioService;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PovApplication {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired  
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public static void main(String[] args) {
        SpringApplication.run(PovApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData() {
        return (args) -> {
            Rol adminRol = rolRepository.save(new Rol("ADMIN"));
            Rol userRol = rolRepository.save(new Rol("USER"));

            usuarioService.registrarUsuario(new Usuario("Administrador", "1234", "1", "admin1@gmail.com", "", "", adminRol, ""));
            usuarioService.registrarUsuario(new Usuario("Administrador", "1234", "2", "admin2@gmail.com", "", "", adminRol, ""));
            usuarioService.registrarUsuario(new Usuario("Bele", "1234", "Mone", "test@gmail.com", "", "", userRol, ""));

            // Crear categorías
            Categoria categoriaCamara = categoriaRepository.save(new Categoria(null, "Cámaras"));
            Categoria categoriaAccesorio = categoriaRepository.save(new Categoria(null, "Accesorios"));

            // Crear lista de categorías para productos
            List<Categoria> categorias = Arrays.asList(categoriaCamara, categoriaAccesorio);

            productoRepository.save(new Producto(null, "EOS R8", "CANON", "Cámara Mirrorless Body",
                "Conectividad: Wi-Fi y Bluetooth para transferencia de imágenes y disparo remoto desde tu smartphone o tablet.",
                Arrays.asList(categoriaCamara), 1400.0, 2, "/images/productos/1.jpg"));

            productoRepository.save(new Producto(null, "EOS R8 Kit", "CANON", "Cámara Mirrorless + Accesorios",
                "Kit que incluye cámara y accesorios básicos.",
                Arrays.asList(categoriaCamara), 1500.00, 10, "/images/productos/2.jpg"));

            productoRepository.save(new Producto(null, "EOS R6", "CANON", "Cámara Mirrorless Full-Frame", 
                "Estabilización de imagen en el cuerpo, sensor CMOS de 20 MP, video 4K UHD.", 
                Arrays.asList(categoriaCamara), 2500.0, 3, "/images/productos/2.jpg"));

            productoRepository.save(new Producto(null, "Alpha 7 IV", "SONY", "Cámara Mirrorless Full-Frame", 
                "Sensor Exmor R CMOS de 33 MP, procesador BIONZ XR, video 4K 60p.", 
                categorias, 2800.0, 4, "/images/productos/3.jpg"));

            productoRepository.save(new Producto(null, "Z6 II", "NIKON", "Cámara Mirrorless Full-Frame", 
                "Sensor CMOS de 24.5 MP, Dual EXPEED 6, 4K UHD.", 
                Arrays.asList(categoriaCamara), 2000.0, 5, "/images/productos/4.jpg"));

            productoRepository.save(new Producto(null, "Lumix S5", "PANASONIC", "Cámara Mirrorless Full-Frame", 
                "24 MP, grabación de video 4K, diseño compacto y ligero.", 
                Arrays.asList(categoriaCamara), 1700.0, 6, "/images/productos/5.jpg"));

            productoRepository.save(new Producto(null, "X-T5", "FUJIFILM", "Cámara Mirrorless APS-C", 
                "40 MP, video 6.2K, estabilización de imagen en el cuerpo (IBIS).", 
                Arrays.asList(categoriaCamara), 1900.0, 3, "/images/productos/6.jpg"));

            productoRepository.save(new Producto(null, "EOS M50 Mark II", "CANON", "Cámara Mirrorless APS-C", 
                "Sensor CMOS APS-C de 24.1 MP, video 4K, pantalla táctil abatible.", 
                Arrays.asList(categoriaCamara), 600.0, 10, "/images/productos/7.jpg"));

            productoRepository.save(new Producto(null, "Alpha 6400", "SONY", "Cámara Mirrorless APS-C", 
                "Sensor CMOS de 24.2 MP, video 4K HDR, Eye AF en tiempo real.", 
                Arrays.asList(categoriaCamara), 900.0, 8, "/images/productos/8.jpg"));

            productoRepository.save(new Producto(null, "Z50", "NIKON", "Cámara Mirrorless APS-C", 
                "20.9 MP, video 4K, pantalla abatible para vlogging.", 
                Arrays.asList(categoriaCamara), 850.0, 5, "/images/productos/6.jpg"));

            productoRepository.save(new Producto(null, "GFX 100S", "FUJIFILM", "Cámara Mirrorless Medio Formato", 
                "102 MP, estabilización en el cuerpo, video 4K.", 
                Arrays.asList(categoriaCamara), 6000.0, 2, "/images/productos/1.jpg"));

            productoRepository.save(new Producto(null, "OM-D E-M1 Mark III", "OLYMPUS", "Cámara Mirrorless Micro Cuatro Tercios", 
                "20 MP, estabilización de imagen de 5 ejes, video 4K.", 
                Arrays.asList(categoriaCamara), 1500.0, 4, "/images/productos/2.jpg"));

            productoRepository.save(new Producto(null, "Mavic Air 2", "DJI", "Dron con Cámara 4K", 
                "Capaz de grabar video 4K, con modos de vuelo inteligentes y control remoto.", 
                Arrays.asList(categoriaAccesorio), 800.0, 6, "/images/productos/3.jpg"));

            productoRepository.save(new Producto(null, "Insta360 ONE X2", "Insta360", "Cámara 360", 
                "Cámara de acción con grabación de video en 360 grados, resistente al agua.", 
                Arrays.asList(categoriaAccesorio), 430.0, 9, "/images/productos/4.jpg"));

            productoRepository.save(new Producto(null, "GoPro HERO10 Black", "GoPro", "Cámara de acción", 
                "Grabación de video en 5.3K, estabilización de imagen HyperSmooth 4.0.", 
                Arrays.asList(categoriaAccesorio), 500.0, 7, "/images/productos/5.jpg"));

            productoRepository.save(new Producto(null, "Pocket 2", "DJI", "Cámara de bolsillo estabilizada", 
                "Grabación de video 4K con gimbal de 3 ejes integrado, ideal para vlogs.", 
                Arrays.asList(categoriaAccesorio), 350.0, 15, "/images/productos/3.jpg"));

            productoRepository.save(new Producto(null, "Lumix GH5 II", "PANASONIC", "Cámara Mirrorless Micro Cuatro Tercios", 
                "20.3 MP, video 4K 60p, estabilización de imagen dual IS 2.", 
                Arrays.asList(categoriaCamara), 1700.0, 4, "/images/productos/2.jpg"));

            productoRepository.save(new Producto(null, "Alpha 7C", "SONY", "Cámara Mirrorless Full-Frame Compacta", 
                "24.2 MP, video 4K HDR, estabilización de imagen en 5 ejes.", 
                Arrays.asList(categoriaCamara), 1800.0, 6, "/images/productos/1.jpg"));

            productoRepository.save(new Producto(null, "EOS RP", "CANON", "Cámara Mirrorless Full-Frame", 
                "26.2 MP, video 4K, pantalla táctil abatible, compacta y ligera.", 
                Arrays.asList(categoriaCamara), 1300.0, 3, "/images/productos/4.jpg"));

            productoRepository.save(new Producto(null, "Z7 II", "NIKON", "Cámara Mirrorless Full-Frame", 
                "45.7 MP, Dual EXPEED 6, video 4K UHD.", 
                Arrays.asList(categoriaCamara), 3000.0, 2, "/images/productos/6.jpg"));

            productoRepository.save(new Producto(null, "X-T30 II", "FUJIFILM", "Cámara Mirrorless APS-C", 
                "26.1 MP, video 4K, diseño retro y compacto.", 
                Arrays.asList(categoriaCamara), 900.0, 12, "/images/productos/7.jpg"));

            productoRepository.save(new Producto(null, "SL2-S", "LEICA", "Cámara Mirrorless Full-Frame", 
                "24 MP, estabilización de imagen en el cuerpo, video 4K.", 
                Arrays.asList(categoriaCamara), 4500.0, 1, "/images/productos/8.jpg"));

        };
    }
}
