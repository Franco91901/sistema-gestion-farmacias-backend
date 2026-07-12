package com.proyecto.core.notificacion.application.service;

import com.proyecto.core.sede.domain.repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificacionScheduler {

    private final NotificacionService notificacionService;
    private final SedeRepository sedeRepository;

    @Scheduled(fixedRate = 300000)
    public void generarNotificacionesPeriodicas() {
        log.info("Iniciando generación automática de notificaciones para todas las sedes...");
        sedeRepository.findAll().forEach(sede -> {
            try {
                notificacionService.generarNotificacionesAutomaticas(sede.getIdSede());
                log.debug("Notificaciones generadas para sede {}", sede.getIdSede());
            } catch (Exception e) {
                log.error("Error generando notificaciones para sede {}: {}", sede.getIdSede(), e.getMessage());
            }
        });
        log.info("Generación automática de notificaciones completada");
    }
}
