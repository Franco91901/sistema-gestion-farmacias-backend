package com.proyecto.messaging.producer;

import com.proyecto.core.notificacion.application.dto.NotificacionEventoDTO;
import com.proyecto.messaging.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificacionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarNotificacion(NotificacionEventoDTO evento) {
        log.info("Enviando evento a RabbitMQ: {}", evento);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, evento);
        log.info("Evento enviado correctamente");
    }
}
