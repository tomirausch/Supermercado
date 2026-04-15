package com.Toams.PruebaTecSupermercado.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {
    private Long id;
    private Long productoId;
    private String nombreProd;
    private Integer cantProd;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
