# Sistema de Gestión de Stock y Ventas de Medicamentos

Sistema de gestión farmacéutica multi-sede desarrollado con arquitectura de microservicios.

---

## Tecnologías principales

| Capa | Tecnología |
|---|---|
| Framework | Spring Boot 4.0.0 / Spring Cloud 2025.1.1 |
| Descubrimiento de servicios | Netflix Eureka |
| API Gateway | Spring Cloud Gateway (WebFlux) |
| Seguridad | JWT (HMAC-SHA256) + Spring Security |
| BD relacional (farmacia) | PostgreSQL 16 |
| BD relacional (auth/anuncios) | MySQL 8 |
| BD documental | MongoDB 7 |
| Mensajería | RabbitMQ 3 |
| SOAP | Spring Web Services |
| Frontend | Angular 18+ (componentes standalone) |
| Contenedores | Docker / Docker Compose |

---

## Arquitectura

```
Frontend Angular (4200)
        │
        ▼
  API Gateway (8080)  ──── Validación JWT
        │
  ┌─────┼─────────────┐
  ▼     ▼             ▼
auth  farmacia   [/api/**]
(8081) (8082)
        │
        ▼ (RabbitMQ)
  notificaciones
     (8083)

soapAnuncio (8085) ◄── Angular llama directo (sin gateway)

Eureka Server (8761) ◄── todos los microservicios se registran excepto soapAnuncio
```

El gateway valida el JWT en cada petición y propaga los datos del usuario como headers HTTP (`X-User-Email`, `X-User-Roles`, `X-User-IdSede`, `X-User-IdUsuario`, `X-User-Nombre`) hacia los servicios downstream. Los endpoints `/api/auth/**` y `/api/sedes` son públicos (no requieren token).

---

## Microservicios

### eureka-server — Puerto 8761
Servidor de descubrimiento de servicios. Todos los microservicios (excepto soapAnuncio) se registran aquí al arrancar. Permite que el gateway los localice por nombre.

**Arrancar primero.**

---

### auth-service — Puerto 8081
Responsable de autenticación y gestión de usuarios. Usa **MySQL** (`bd_auth`).

**Funcionalidades:**
- Login → genera JWT con claims: `sub` (email), `roles`, `idSede`, `idUsuario`, `nombre`
- Registro de nuevos usuarios
- CRUD de usuarios (admin)
- Gestión de roles: `ROLE_ADMIN`, `ROLE_GESTOR`, `ROLE_FARMACEUTICO`, `ROLE_TRANSPORTISTA`
- Gestión de sedes (listado público, sin token)

**Endpoints principales:**
| Método | Ruta | Acceso |
|---|---|---|
| POST | `/api/auth/login` | Público |
| POST | `/api/auth/register` | Público |
| GET | `/api/sedes` | Público |
| GET | `/api/usuarios` | ADMIN |
| PUT | `/api/admin/usuarios/{id}` | ADMIN |

---

### farmacia-service — Puerto 8082
Núcleo del sistema. Concentra la mayor parte de la lógica de negocio. Usa **PostgreSQL** (`bd_farmacia`).

**Funcionalidades:**
- **Medicamentos:** CRUD de medicamentos y stock por sede
- **Lotes:** registro de lotes con fecha de caducidad, descuento FIFO en ventas
- **Inventario / Stock:** movimientos de entrada y salida, resumen por sede
- **Ventas:** procesamiento de ventas con descuento de stock automático por FIFO, registro de movimientos
- **Historial de ventas:** listado filtrado por sede del usuario autenticado
- **Órdenes de transferencia:** gestión de órdenes entre sedes (gestor y transportista)
- **Notificaciones:** alertas de bajo stock y próximos a caducar (almacenadas en PostgreSQL)
- **Sedes:** listado de sedes disponibles

**Seguridad:** reconstruye el `SecurityContext` leyendo los headers del gateway. `@PreAuthorize` activo con `@EnableMethodSecurity`.

**Endpoints principales:**
| Método | Ruta | Roles |
|---|---|---|
| GET/POST | `/api/medicamentos` | FARMACEUTICO, ADMIN |
| GET/POST | `/api/lotes` | FARMACEUTICO, ADMIN |
| GET | `/api/stock` | Autenticado |
| POST | `/api/ventas` | FARMACEUTICO, ADMIN |
| GET | `/api/ventas` | FARMACEUTICO, GESTOR, ADMIN |
| GET/POST | `/api/notificaciones` | Autenticado |
| GET/POST | `/api/ordenes` | GESTOR, TRANSPORTISTA |

---

### notificaciones-service — Puerto 8083
Servicio de registro de eventos vía mensajería. Usa **MongoDB** (`bd_notificaciones`) y **RabbitMQ**.

**Funcionalidades:**
- Escucha eventos en la cola RabbitMQ y los persiste en MongoDB
- Expone historial de notificaciones de eventos recibidos por usuario

**Nota:** este servicio almacena eventos de mensajería asíncrona. Las notificaciones de stock que ve el frontend provienen de farmacia-service directamente.

---

### soapAnuncio-service — Puerto 8085
Servicio SOAP de anuncios. Usa la misma instancia de **MySQL** que auth-service (`bd_auth`, tabla `anuncio`). No está integrado con Eureka ni el gateway — el frontend lo consume directamente.

**Funcionalidades:**
- Expone un endpoint SOAP con los anuncios activos de la farmacia
- La tabla `anuncio` se crea automáticamente al arrancar (`schema.sql`)
- CORS habilitado para consumo directo desde Angular

**WSDL:** `http://localhost:8085/ws/anuncio.wsdl`

**Operación disponible:**
- `ObtenerAnunciosRequest` → devuelve lista de anuncios (`id`, `titulo`, `descripcion`, `imagenUrl`)

---

## Bases de datos

| BD | Motor | Puerto | Usado por |
|---|---|---|---|
| `bd_auth` | MySQL 8 | 3306 | auth-service, soapAnuncio-service |
| `bd_farmacia` | PostgreSQL 16 | 5432 | farmacia-service |
| `bd_notificaciones` | MongoDB 7 | 27017 | notificaciones-service |

RabbitMQ corre en el puerto `5672` (panel de administración en `15672`).

---

## Levantar la infraestructura

```bash
# 1. Levantar bases de datos y RabbitMQ
docker-compose up -d

# 2. Levantar microservicios en orden
#    eureka-server → auth-service → farmacia-service → api-gateway
#    notificaciones-service y soapAnuncio-service pueden arrancar en cualquier orden

# 3. Verificar registro en Eureka
#    http://localhost:8761
```

---

## Credenciales por defecto

| Servicio | Usuario | Contraseña |
|---|---|---|
| PostgreSQL | `postgres` | `postgres` |
| MySQL | `root` | `mysql` |
| RabbitMQ | `guest` | `guest` |

---

## Orden de arranque recomendado

1. `docker-compose up -d`
2. `eureka-server`
3. `auth-service`
4. `farmacia-service`
5. `api-gateway`
6. `notificaciones-service`
7. `soapAnuncio-service`

---

## Vista previa

https://github.com/user-attachments/assets/6747c4a8-babb-4e5f-baf3-08895d3041f2
