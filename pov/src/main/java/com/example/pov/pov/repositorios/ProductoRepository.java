package com.example.pov.pov.repositorios;

import com.example.pov.pov.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {


    @Query("SELECT p FROM Producto p LEFT JOIN p.categorias c WHERE " +
    "(:nombre IS NULL OR LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " + 
    "(:precioMin IS NULL OR p.precioUnitario >= :precioMin) AND " + 
    "(:precioMax IS NULL OR p.precioUnitario <= :precioMax) AND " + 
    "(:categoria IS NULL OR LOWER(c.nombreCategoria) LIKE LOWER(CONCAT('%', :categoria, '%'))) " +
    "GROUP BY p.id")
    List<Producto> filtrarProductos(@Param("nombre") String nombre,
                                @Param("precioMin") Double precioMin,
                                @Param("precioMax") Double precioMax,
                                @Param("categoria") String categoria);

                                    


       
}
