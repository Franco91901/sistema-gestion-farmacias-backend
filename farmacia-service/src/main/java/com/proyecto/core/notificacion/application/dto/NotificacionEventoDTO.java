package com.proyecto.core.notificacion.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionEventoDTO {

    private Long usuarioId;
    private String titulo;
    private String mensaje;
    private String tipo;
    private Long idMedicamento;
    private String nombreMedicamento;
    private Integer stockMedicamento;
    private Long idSede;
    private String nombreSede;
}
