package com.Toams.PruebaTecSupermercado.mapper;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import com.Toams.PruebaTecSupermercado.dto.DetalleVentaDTO;
import com.Toams.PruebaTecSupermercado.dto.ProductoDTO;
import com.Toams.PruebaTecSupermercado.dto.SucursalDTO;
import com.Toams.PruebaTecSupermercado.dto.VentaDTO;
import com.Toams.PruebaTecSupermercado.model.Producto;
import com.Toams.PruebaTecSupermercado.model.Sucursal;
import com.Toams.PruebaTecSupermercado.model.Venta;

public class Mapper {

    // Mapeo de Producto a ProductoDTO
    public static ProductoDTO toProductoDTO(Producto producto) {
        if (producto == null) {
            return null;
        }
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .categoria(producto.getCategoria())
                .precio(producto.getPrecio())
                .cantidad(producto.getCantidad())
                .build();
    }

    // Mapeo de Sucursal a SucursalDTO
    public static SucursalDTO toSucursalDTO(Sucursal sucursal) {
        if (sucursal == null) {
            return null;
        }
        return SucursalDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .direccion(sucursal.getDireccion())
                .build();
    }

    // Mapero de Venta a VentaDTO
    public static VentaDTO toVentaDTO(Venta venta) {
        if (venta == null) {
            return null;
        }

        var detalle = venta.getDetalle().stream().map(det -> 
            DetalleVentaDTO.builder()
                .id(det.getId())
                .nombreProd(det.getProd() != null ? det.getProd().getNombre() : null)
                .cantProd(det.getCantProd())
                .precioUnitario(det.getPrecioUnitario())
                .subtotal(det.getPrecioUnitario() != null && det.getCantProd() != null
                        ? det.getPrecioUnitario().multiply(BigDecimal.valueOf(det.getCantProd()))
                        : null)
                .build()
        ).collect(Collectors.toList());

        var total = detalle.stream()
                .filter(d -> d.getSubtotal() != null)
                .map(DetalleVentaDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return VentaDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .estado(venta.getEstado() != null ? venta.getEstado() : null)
                .idSucursal(venta.getSucursal() != null ? venta.getSucursal().getId() : null)
                .total(total)
                .detalle(detalle)
                .build();
    }

}
