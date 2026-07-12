package com.farmacia.notificaciones.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionEvento {

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
