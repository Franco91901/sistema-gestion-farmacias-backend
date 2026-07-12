package com.farmacia.notificaciones.controller;

import com.farmacia.notificaciones.dto.NotificacionResponseDTO;
import com.farmacia.notificaciones.service.NotificacionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionServiceImpl service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificacionResponseDTO>>
    findByUser(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                service.findByUser(userId)
        );
    }
}
