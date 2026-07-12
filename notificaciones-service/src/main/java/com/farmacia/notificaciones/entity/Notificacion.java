package com.farmacia.notificaciones.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notificacion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    @Id
    private String id;

    private Long usuarioId;

    private String titulo;

    private String mensaje;

    private String tipo;

    private Boolean leida;

    private Long idMedicamento;

    private String nombreMedicamento;

    private Integer stockMedicamento;

    private Long idSede;

    private String nombreSede;

    private LocalDateTime fechaCreacion;

}
