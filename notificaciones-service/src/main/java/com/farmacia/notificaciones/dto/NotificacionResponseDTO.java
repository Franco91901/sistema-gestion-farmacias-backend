package com.farmacia.notificaciones.dto;

public record NotificacionResponseDTO(

        String id,

        Long usuarioId,

        String titulo,

        String mensaje,

        String tipo,

        Boolean leida,

        Long idMedicamento,

        String nombreMedicamento,

        Integer stockMedicamento,

        Long idSede,

        String nombreSede
) {}
