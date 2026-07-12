-- ============================================================
-- DATA.SQL  — Auth Service · Datos iniciales (MySQL)
--
-- CREDENCIALES (password: "password" para todos)
--
--   ADMIN        : admin@farmacia.pe
--   GESTOR       : gestor@farmacia.pe  / gestor2@farmacia.pe
--   FARMACÉUTICO : farmac1@farmacia.pe (sede 1)
--                  farmac2@farmacia.pe (sede 2)
--                  farmac3@farmacia.pe (sede 3)
--   TRANSPORTISTA: transportista@farmacia.pe
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ------------------------------------------------------------
-- ROLES
-- ------------------------------------------------------------
INSERT IGNORE INTO rol (id_rol, nombre) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_GESTOR'),
(3, 'ROLE_FARMACEUTICO'),
(4, 'ROLE_TRANSPORTISTA');

-- ------------------------------------------------------------
-- USUARIOS  (contraseña: "password")
-- id_sede referencia la sede en farmacia-service (sin FK real)
-- ------------------------------------------------------------
INSERT IGNORE INTO usuario (id_usuario, username, nombre, apellido, email, password, telefono, dni, activo, fecha_creacion, id_rol, id_sede) VALUES
(1, 'admin',         'Carlos', 'Mendoza', 'admin@farmacia.pe',         '$2a$12$qxt0EGSyeEkEeJHLKJ04u.S/RBy.QxPQBJlTI2ShN9uiw6iU0Gy2u', '987654321', '12345678', 1, '2024-01-01 08:00:00', 1, 1),
(2, 'gestor',        'María',  'Torres',  'gestor@farmacia.pe',        '$2a$12$qxt0EGSyeEkEeJHLKJ04u.S/RBy.QxPQBJlTI2ShN9uiw6iU0Gy2u', '987654322', '23456789', 1, '2024-01-01 08:00:00', 2, 1),
(3, 'transportista', 'Luis',   'García',  'transportista@farmacia.pe', '$2a$12$qxt0EGSyeEkEeJHLKJ04u.S/RBy.QxPQBJlTI2ShN9uiw6iU0Gy2u', '987654323', '34567890', 1, '2024-01-01 08:00:00', 4, 1),
(4, 'farmac1',       'Ana',    'Quispe',  'farmac1@farmacia.pe',       '$2a$12$qxt0EGSyeEkEeJHLKJ04u.S/RBy.QxPQBJlTI2ShN9uiw6iU0Gy2u', '987654324', '45678901', 1, '2024-01-01 08:00:00', 3, 1),
(5, 'farmac2',       'Pedro',  'Vargas',  'farmac2@farmacia.pe',       '$2a$12$qxt0EGSyeEkEeJHLKJ04u.S/RBy.QxPQBJlTI2ShN9uiw6iU0Gy2u', '987654325', '56789012', 1, '2024-01-01 08:00:00', 3, 2),
(6, 'farmac3',       'Rosa',   'Mamani',  'farmac3@farmacia.pe',       '$2a$12$qxt0EGSyeEkEeJHLKJ04u.S/RBy.QxPQBJlTI2ShN9uiw6iU0Gy2u', '987654326', '67890123', 1, '2024-01-01 08:00:00', 3, 3),
(7, 'gestor2',       'Jorge',  'Ramos',   'gestor2@farmacia.pe',       '$2a$12$qxt0EGSyeEkEeJHLKJ04u.S/RBy.QxPQBJlTI2ShN9uiw6iU0Gy2u', '987654327', '78901234', 1, '2024-01-01 08:00:00', 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
