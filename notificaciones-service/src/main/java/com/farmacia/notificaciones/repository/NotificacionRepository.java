package com.farmacia.notificaciones.repository;

import com.farmacia.notificaciones.entity.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends MongoRepository<Notificacion, String> {
    List<Notificacion> findByUsuarioId(Long usuarioId);
}
