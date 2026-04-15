package com.Toams.PruebaTecSupermercado.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Toams.PruebaTecSupermercado.dto.DetalleVentaDTO;
import com.Toams.PruebaTecSupermercado.dto.VentaDTO;
import com.Toams.PruebaTecSupermercado.mapper.Mapper;
import com.Toams.PruebaTecSupermercado.model.DetalleVenta;
import com.Toams.PruebaTecSupermercado.model.Producto;
import com.Toams.PruebaTecSupermercado.model.Sucursal;
import com.Toams.PruebaTecSupermercado.model.Venta;
import com.Toams.PruebaTecSupermercado.repository.ProductoRepository;
import com.Toams.PruebaTecSupermercado.repository.SucursalRepository;
import com.Toams.PruebaTecSupermercado.repository.VentaRepository;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private VentaRepository repo;
    @Autowired
    private SucursalRepository repoSucursal;
    @Autowired
    private ProductoRepository repoProducto;

    @Override
    public List<VentaDTO> traerVentas() {
        List<Venta> ventas = repo.findAll();
        List<VentaDTO> ventasDTO = ventas.stream()
                .map(Mapper::toVentaDTO)
                .toList();
        
        return ventasDTO;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDTO) {

        if(ventaDTO == null) {
            throw new RuntimeException("La venta no puede ser nula");
        }

        if(ventaDTO.getIdSucursal() == null) {
            throw new RuntimeException("La venta debe tener una sucursal asociada");
        }

        if(ventaDTO.getDetalle() == null || ventaDTO.getDetalle().isEmpty()) {
            throw new RuntimeException("La venta debe tener al menos un detalle");
        }

        // Buscar Sucursal
        Sucursal sucursal = repoSucursal.findById(ventaDTO.getIdSucursal())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        // Crear Venta
        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha());
        venta.setEstado(ventaDTO.getEstado());
        venta.setTotal(ventaDTO.getTotal());
        venta.setSucursal(sucursal);

        // Lista de detalles
        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal totalCalculado = BigDecimal.ZERO;
        
        for(DetalleVentaDTO detDTO : ventaDTO.getDetalle()) {
            // Buscar producto por id
            Producto producto = repoProducto.findById(detDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            // Crear el detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProd(producto);
            detalle.setCantProd(detDTO.getCantProd());
            detalle.setPrecioUnitario(detDTO.getPrecioUnitario());

            detalles.add(detalle);
            totalCalculado = totalCalculado.add(detDTO.getPrecioUnitario().multiply(BigDecimal.valueOf(detDTO.getCantProd())));
        }
        // Asignar detalles a la venta
        venta.setDetalle(detalles);

        return Mapper.toVentaDTO(repo.save(venta));
    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        // Buscar la venta existente
        Venta venta = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        if(ventaDTO.getIdSucursal() != null) {
            Sucursal sucursal = repoSucursal.findById(ventaDTO.getIdSucursal())
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            venta.setSucursal(sucursal);
        }

        if(ventaDTO.getFecha() != null) {
            venta.setFecha(ventaDTO.getFecha());
        }
        if(ventaDTO.getEstado() != null) {
            venta.setEstado(ventaDTO.getEstado());
        }
        if(ventaDTO.getTotal() != null) {
            venta.setTotal(ventaDTO.getTotal());
        }

        return Mapper.toVentaDTO(repo.save(venta));
    }

    @Override
    public void eliminarVenta(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Venta no encontrada");
        }
        repo.deleteById(id);
    }

}
