package com.Toams.PruebaTecSupermercado.repository;

import com.Toams.PruebaTecSupermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
