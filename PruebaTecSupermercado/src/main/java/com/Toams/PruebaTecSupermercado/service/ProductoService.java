package com.Toams.PruebaTecSupermercado.service;

import java.util.List;

import com.Toams.PruebaTecSupermercado.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Toams.PruebaTecSupermercado.dto.ProductoDTO;
import com.Toams.PruebaTecSupermercado.mapper.Mapper;
import com.Toams.PruebaTecSupermercado.model.Producto;
import com.Toams.PruebaTecSupermercado.repository.ProductoRepository;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private ProductoRepository repo;

    @Override
    public List<ProductoDTO> traerProductos() {
        return repo.findAll().stream().map(Mapper::toProductoDTO).toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        var producto = Producto.builder()
                .nombre(productoDTO.getNombre())
                .categoria(productoDTO.getCategoria())
                .precio(productoDTO.getPrecio())
                .cantidad(productoDTO.getCantidad())
                .build();
        return Mapper.toProductoDTO(repo.save(producto));
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto producto = repo.findById(id)
                                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        producto.setNombre(productoDTO.getNombre());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCantidad(productoDTO.getCantidad());

        return Mapper.toProductoDTO(repo.save(producto));
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Producto no encontrado");
        }
        repo.deleteById(id);
    }

}
