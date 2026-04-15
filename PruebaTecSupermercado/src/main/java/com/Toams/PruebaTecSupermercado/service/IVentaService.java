package com.Toams.PruebaTecSupermercado.service;

import java.util.List;

import com.Toams.PruebaTecSupermercado.dto.VentaDTO;

public interface IVentaService {
    List<VentaDTO> traerVentas();
    VentaDTO crearVenta(VentaDTO VentaDTO);
    VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO);
    void eliminarVenta(Long id);
}
