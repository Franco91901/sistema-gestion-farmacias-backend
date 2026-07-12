package com.proyecto.core.orden.application.mapper;

import com.proyecto.core.orden.application.dto.OrdenResponseDTO;
import com.proyecto.core.orden.domain.model.Orden;
import org.springframework.stereotype.Component;

@Component
public class OrdenMapper {

    public OrdenResponseDTO toResponseDTO(Orden orden) {
        if (orden == null) return null;
        Long idSede = orden.getSede() != null ? orden.getSede().getIdSede() : null;
        String nombreSede = orden.getSede() != null ? orden.getSede().getNombre() : null;
        Long idSedeDestino = orden.getSedeDestino() != null ? orden.getSedeDestino().getIdSede() : null;
        String nombreSedeDestino = orden.getSedeDestino() != null ? orden.getSedeDestino().getNombre() : null;
        return new OrdenResponseDTO(
            orden.getIdOrden(), orden.getIdUsuario(), orden.getNombreUsuario(),
            idSede, nombreSede,
            orden.getTipo() != null ? orden.getTipo().name() : null,
            orden.getEstado() != null ? orden.getEstado().name() : null,
            idSedeDestino, nombreSedeDestino, orden.getFecha()
        );
    }
}
