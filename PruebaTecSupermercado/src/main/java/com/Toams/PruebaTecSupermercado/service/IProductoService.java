package com.Toams.PruebaTecSupermercado.service;

import java.util.List;

import com.Toams.PruebaTecSupermercado.dto.ProductoDTO;

public interface IProductoService {
    List<ProductoDTO> traerProductos();
    ProductoDTO crearProducto(ProductoDTO productoDTO);
    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO);
    void eliminarProducto(Long id);
}
