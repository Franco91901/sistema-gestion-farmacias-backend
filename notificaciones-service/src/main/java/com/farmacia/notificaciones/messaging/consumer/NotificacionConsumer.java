package com.farmacia.notificaciones.messaging.consumer;

import com.farmacia.notificaciones.dto.NotificacionEvento;
import com.farmacia.notificaciones.messaging.config.RabbitMQConfig;
import com.farmacia.notificaciones.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificacionConsumer {
    private final NotificacionService notificationService;

    @RabbitListener(
            queues = RabbitMQConfig.QUEUE
    )
    public void receive(NotificacionEvento event) {

        log.info("Evento recibido: {}", event);

        notificationService.save(event);

        log.info("Notificación guardada");
    }
}
