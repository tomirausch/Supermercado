package com.Toams.PruebaTecSupermercado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Toams.PruebaTecSupermercado.dto.SucursalDTO;
import com.Toams.PruebaTecSupermercado.mapper.Mapper;
import com.Toams.PruebaTecSupermercado.model.Sucursal;
import com.Toams.PruebaTecSupermercado.repository.SucursalRepository;

@Service
public class SucursalService implements ISucursalService{

    @Autowired
    private SucursalRepository repo;

    @Override
    public List<SucursalDTO> traerSucursales() {
        return repo.findAll().stream()
                .map(Mapper::toSucursalDTO)
                .toList();
    }

    @Override
    public SucursalDTO crearSucursal(SucursalDTO sucursalDTO) {
        Sucursal sucursal = Sucursal.builder()
                .nombre(sucursalDTO.getNombre())
                .direccion(sucursalDTO.getDireccion())
                .build();
        return Mapper.toSucursalDTO(repo.save(sucursal));
    }

    @Override
    public SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDTO) {
        Sucursal sucursal = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        if (sucursalDTO.getNombre() != null) {
            sucursal.setNombre(sucursalDTO.getNombre());
        }
        if (sucursalDTO.getDireccion() != null) {
            sucursal.setDireccion(sucursalDTO.getDireccion());
        }

        return Mapper.toSucursalDTO(repo.save(sucursal));
    }

    @Override
    public void eliminarSucursal(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Sucursal no encontrada");
        }
        repo.deleteById(id);
    }
}
