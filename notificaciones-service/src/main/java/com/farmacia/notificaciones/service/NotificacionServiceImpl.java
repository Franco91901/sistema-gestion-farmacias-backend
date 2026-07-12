package com.farmacia.notificaciones.service;

import com.farmacia.notificaciones.dto.NotificacionEvento;
import com.farmacia.notificaciones.dto.NotificacionResponseDTO;
import com.farmacia.notificaciones.entity.LogEvento;
import com.farmacia.notificaciones.entity.Notificacion;
import com.farmacia.notificaciones.repository.LogEventoRepository;
import com.farmacia.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;
    private final LogEventoRepository logRepository;

    @Override
    public void save(NotificacionEvento event) {

        Notificacion notification =
                Notificacion.builder()
                        .usuarioId(event.getUsuarioId())
                        .titulo(event.getTitulo())
                        .mensaje(event.getMensaje())
                        .tipo(event.getTipo())
                        .idMedicamento(event.getIdMedicamento())
                        .nombreMedicamento(event.getNombreMedicamento())
                        .stockMedicamento(event.getStockMedicamento())
                        .idSede(event.getIdSede())
                        .nombreSede(event.getNombreSede())
                        .leida(false)
                        .fechaCreacion(LocalDateTime.now())
                        .build();

        Notificacion saved = repository.save(notification);

        LogEvento log = LogEvento.builder()
                .accion("SAVE_NOTIFICATION")
                .detalle("Notification saved for user "
                        + saved.getUsuarioId()
                        + " title: "
                        + saved.getTitulo())
                .fecha(LocalDateTime.now())
                .build();

        logRepository.save(log);
    }

    @Override
    public List<NotificacionResponseDTO> findByUser(
            Long userId
    ) {

        return repository.findByUsuarioId(userId)
                .stream()
                .map(notification ->

                        new NotificacionResponseDTO(
                                notification.getId(),
                                notification.getUsuarioId(),
                                notification.getTitulo(),
                                notification.getMensaje(),
                                notification.getTipo(),
                                notification.getLeida(),
                                notification.getIdMedicamento(),
                                notification.getNombreMedicamento(),
                                notification.getStockMedicamento(),
                                notification.getIdSede(),
                                notification.getNombreSede()
                        )
                )
                .toList();
    }

}
