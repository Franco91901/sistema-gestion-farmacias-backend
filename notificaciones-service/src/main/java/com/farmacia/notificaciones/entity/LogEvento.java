package com.farmacia.notificaciones.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "log")
public class LogEvento {
    @Id
    private String id;

    private String accion;

    private String detalle;

    private LocalDateTime fecha;
}
