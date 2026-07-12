package com.farmacia.notificaciones.service;

import com.farmacia.notificaciones.dto.NotificacionEvento;
import com.farmacia.notificaciones.dto.NotificacionResponseDTO;

import java.util.List;

public interface NotificacionService {
    void save(NotificacionEvento event);
    List<NotificacionResponseDTO> findByUser(Long userId);
}
