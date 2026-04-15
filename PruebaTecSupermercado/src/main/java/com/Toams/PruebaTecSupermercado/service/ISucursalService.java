package com.Toams.PruebaTecSupermercado.service;

import java.util.List;

import com.Toams.PruebaTecSupermercado.dto.SucursalDTO;

public interface ISucursalService {
    List<SucursalDTO> traerSucursales();
    SucursalDTO crearSucursal(SucursalDTO sucursalDTO);
    SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDTO);
    void eliminarSucursal(Long id);
}
