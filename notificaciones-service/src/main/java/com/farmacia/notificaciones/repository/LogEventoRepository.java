package com.farmacia.notificaciones.repository;

import com.farmacia.notificaciones.entity.LogEvento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEventoRepository
        extends MongoRepository<LogEvento, String> {
}