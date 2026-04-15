package com.Toams.PruebaTecSupermercado.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    private Long id;
    private LocalDate fecha;
    private String estado;
    private BigDecimal total;
    
    private Long idSucursal;
    
    private List<DetalleVentaDTO> detalle;
}
