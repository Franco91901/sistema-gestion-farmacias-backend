-- ============================================================
-- DATA.SQL  — Farmacia Multisede · Datos iniciales (PostgreSQL)
-- ============================================================

-- ------------------------------------------------------------
-- SEDES
-- ------------------------------------------------------------
INSERT INTO sede (id_sede, nombre, direccion) VALUES
(1, 'Sede Central',    'Av. Abancay 491, Lima'),
(2, 'Sede Miraflores', 'Av. Larco 123, Miraflores'),
(3, 'Sede San Isidro', 'Calle Los Rosales 456, San Isidro')
ON CONFLICT DO NOTHING;

-- ------------------------------------------------------------
-- MEDICAMENTOS
-- ------------------------------------------------------------
INSERT INTO medicamento (id_medicamento, nombre, descripcion, precio_venta) VALUES
(1,  'Paracetamol 500mg',              'Caja x 20 Tabs',            5.50),
(2,  'Ibuprofeno 400mg',               'Caja x 20 Tabs',            8.00),
(3,  'Amoxicilina 500mg',              'Caja x 12 Caps',           15.00),
(4,  'Omeprazol 20mg',                 'Caja x 14 Caps',           10.00),
(5,  'Losartán 50mg',                  'Caja x 30 Tabs',           12.00),
(6,  'Metformina 850mg',               'Caja x 30 Tabs',            9.50),
(7,  'Atorvastatina 20mg',             'Caja x 30 Tabs',           18.00),
(8,  'Diclofenaco 75mg',               'Caja x 3 Amp',              6.50),
(9,  'Azitromicina 500mg',             'Caja x 3 Tabs',            25.00),
(10, 'Clonazepam 2mg',                 'Caja x 30 Tabs',           12.00),
(11, 'Salbutamol inhalador 200 dosis', 'Inhalador 200 dosis',      35.00),
(12, 'Insulina NPH 100UI/mL',          'Vial 10mL',                65.00)
ON CONFLICT DO NOTHING;

-- Asegurar precios en registros existentes
UPDATE medicamento SET precio_venta = 5.50  WHERE id_medicamento = 1  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 8.00  WHERE id_medicamento = 2  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 15.00 WHERE id_medicamento = 3  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 10.00 WHERE id_medicamento = 4  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 12.00 WHERE id_medicamento = 5  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 9.50  WHERE id_medicamento = 6  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 18.00 WHERE id_medicamento = 7  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 6.50  WHERE id_medicamento = 8  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 25.00 WHERE id_medicamento = 9  AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 12.00 WHERE id_medicamento = 10 AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 35.00 WHERE id_medicamento = 11 AND precio_venta = 0;
UPDATE medicamento SET precio_venta = 65.00 WHERE id_medicamento = 12 AND precio_venta = 0;

-- ------------------------------------------------------------
-- MEDICAMENTO_SEDE
-- ------------------------------------------------------------
INSERT INTO medicamento_sede (id, id_medicamento, id_sede, stock_total) VALUES
-- Sede 1
(1,  1,  1, 150), (2,  2,  1, 140), (3,  3,  1,  75), (4,  4,  1, 200),
(5,  5,  1, 180), (6,  6,  1, 160), (7,  7,  1, 100), (8,  8,  1,  80),
(9,  9,  1,  90), (10, 10, 1,  45), (11, 11, 1,  40), (12, 12, 1,  60),
-- Sede 2
(13, 1,  2, 100), (14, 2,  2,  90), (15, 3,  2,  70), (16, 4,  2, 150),
(17, 5,  2, 120), (18, 6,  2, 100), (19, 7,  2,  70), (20, 8,  2,  60),
(21, 9,  2,  50), (22, 10, 2,  30), (23, 11, 2,  20), (24, 12, 2,  40),
-- Sede 3 (bajo stock)
(25, 1,  3,  30), (26, 2,  3,  12), (27, 3,  3,   8), (28, 4,  3,  50),
(29, 5,  3,  25), (30, 6,  3,  20), (31, 7,  3,   4), (32, 8,  3,  40),
(33, 9,  3,   9), (34, 10, 3,   3), (35, 11, 3,   7), (36, 12, 3,  15)
ON CONFLICT DO NOTHING;

-- ------------------------------------------------------------
-- LOTES — Sede 1 (Central)
-- ------------------------------------------------------------
INSERT INTO lote (id_lote, codigo_lote, fecha_caducidad, stock_lote, id_medicamento, id_sede) VALUES
(1,  'LOT-202801-011A', '2028-01-01', 100, 1,  1),
(2,  'LOT-202606-011B', '2026-06-20',  50, 1,  1),
(3,  'LOT-202803-021A', '2028-03-01',  80, 2,  1),
(4,  'LOT-202806-021B', '2028-06-01',  60, 2,  1),
(5,  'LOT-202706-031A', '2027-06-01',  70, 3,  1),
(6,  'LOT-202604-031B', '2026-04-15',   5, 3,  1),
(7,  'LOT-202901-041A', '2029-01-01', 200, 4,  1),
(8,  'LOT-202801-051A', '2028-01-01', 100, 5,  1),
(9,  'LOT-202706-051B', '2027-06-01',  80, 5,  1),
(10, 'LOT-202712-061A', '2027-12-01', 160, 6,  1),
(11, 'LOT-202703-071A', '2027-03-01', 100, 7,  1),
(12, 'LOT-202801-081A', '2028-01-01',  80, 8,  1),
(13, 'LOT-202709-091A', '2027-09-01',  90, 9,  1),
(14, 'LOT-202606-101A', '2026-06-15',  45, 10, 1),
(15, 'LOT-202701-111A', '2027-01-01',  40, 11, 1),
(16, 'LOT-202606-121A', '2026-06-28',  60, 12, 1)
ON CONFLICT DO NOTHING;

-- Sede 2 (Miraflores)
INSERT INTO lote (id_lote, codigo_lote, fecha_caducidad, stock_lote, id_medicamento, id_sede) VALUES
(17, 'LOT-202801-012A', '2028-01-01', 100, 1,  2),
(18, 'LOT-202706-022A', '2027-06-01',  90, 2,  2),
(19, 'LOT-202706-032A', '2027-06-01',  70, 3,  2),
(20, 'LOT-202901-042A', '2029-01-01', 150, 4,  2),
(21, 'LOT-202801-052A', '2028-01-01', 120, 5,  2),
(22, 'LOT-202712-062A', '2027-12-01', 100, 6,  2),
(23, 'LOT-202703-072A', '2027-03-01',  70, 7,  2),
(24, 'LOT-202801-082A', '2028-01-01',  60, 8,  2),
(25, 'LOT-202709-092A', '2027-09-01',  50, 9,  2),
(26, 'LOT-202701-102A', '2027-01-01',  30, 10, 2),
(27, 'LOT-202606-112A', '2026-06-25',  20, 11, 2),
(28, 'LOT-202606-122A', '2026-06-28',  40, 12, 2)
ON CONFLICT DO NOTHING;

-- Sede 3 (San Isidro)
INSERT INTO lote (id_lote, codigo_lote, fecha_caducidad, stock_lote, id_medicamento, id_sede) VALUES
(29, 'LOT-202706-013A', '2027-06-01',  30, 1,  3),
(30, 'LOT-202703-023A', '2027-03-01',  12, 2,  3),
(31, 'LOT-202701-033A', '2027-01-01',   8, 3,  3),
(32, 'LOT-202801-043A', '2028-01-01',  50, 4,  3),
(33, 'LOT-202706-053A', '2027-06-01',  25, 5,  3),
(34, 'LOT-202712-063A', '2027-12-01',  20, 6,  3),
(35, 'LOT-202703-073A', '2027-03-01',   4, 7,  3),
(36, 'LOT-202801-083A', '2028-01-01',  40, 8,  3),
(37, 'LOT-202709-093A', '2027-09-01',   9, 9,  3),
(38, 'LOT-202606-103A', '2026-06-12',   3, 10, 3),
(39, 'LOT-202701-113A', '2027-01-01',   7, 11, 3),
(40, 'LOT-202606-123A', '2026-06-25',  15, 12, 3)
ON CONFLICT DO NOTHING;

-- ------------------------------------------------------------
-- MOVIMIENTO_STOCK
-- ------------------------------------------------------------
INSERT INTO movimiento_stock (id_movimiento, tipo, cantidad, fecha, observacion, id_medicamento, id_sede, id_lote, id_usuario) VALUES
(1,  'ENTRADA',       100, '2024-01-15 09:00:00', 'Ingreso inicial de lote',         1,  1, 1,  4),
(2,  'ENTRADA',        50, '2024-01-15 09:05:00', 'Ingreso inicial de lote',         1,  1, 2,  4),
(3,  'ENTRADA',       200, '2024-01-15 09:10:00', 'Ingreso inicial de lote',         4,  1, 7,  4),
(4,  'ENTRADA',       100, '2024-01-15 09:15:00', 'Ingreso inicial de lote',         5,  1, 8,  4),
(5,  'SALIDA',         30, '2026-05-10 10:00:00', 'Despacho por receta médica',      1,  1, 1,  4),
(6,  'SALIDA',         20, '2026-05-15 11:00:00', 'Despacho por receta médica',      2,  1, 3,  4),
(7,  'VENCIMIENTO',     5, '2026-05-20 08:00:00', 'Retiro por caducidad',            3,  1, 6,  4),
(8,  'TRANSFERENCIA',  30, '2026-05-20 14:00:00', 'Transferencia a Sede San Isidro', 3,  1, 5,  2),
(9,  'ENTRADA',        30, '2026-05-20 16:00:00', 'Recepción de transferencia',      3,  3, NULL, 6),
(10, 'SALIDA',         10, '2026-05-28 09:00:00', 'Despacho por receta médica',      6,  2, 22, 5)
ON CONFLICT DO NOTHING;

-- ------------------------------------------------------------
-- ORDENES
-- ------------------------------------------------------------
INSERT INTO orden (id_orden, tipo, estado, fecha, id_usuario, nombre_usuario, id_sede, id_sede_destino) VALUES
(1, 'TRANSFERENCIA', 'COMPLETADA', '2026-05-10 14:00:00', 2, 'María Torres', 1, 3),
(2, 'COMPRA',        'PENDIENTE',  '2026-05-25 10:30:00', 2, 'María Torres', 1, NULL),
(3, 'TRANSFERENCIA', 'APROBADA',   '2026-05-28 09:00:00', 7, 'Jorge Ramos',  2, 3)
ON CONFLICT DO NOTHING;

-- ------------------------------------------------------------
-- DETALLE_ORDEN
-- ------------------------------------------------------------
INSERT INTO detalle_orden (id_detalle, cantidad, estado, id_orden, id_medicamento) VALUES
(1, 50, 'ENTREGADO',      1, 3),
(2, 50, 'PENDIENTE',      2, 7),
(3, 30, 'PENDIENTE',      2, 10),
(4, 20, 'EN_RUTA',        3, 7),
(5, 15, 'EN_PREPARACION', 3, 9)
ON CONFLICT DO NOTHING;

-- ------------------------------------------------------------
-- RESET SEQUENCES (after explicit-ID inserts)
-- ------------------------------------------------------------
SELECT setval(pg_get_serial_sequence('medicamento',    'id_medicamento'),    (SELECT MAX(id_medicamento)    FROM medicamento));
SELECT setval(pg_get_serial_sequence('lote',           'id_lote'),           (SELECT MAX(id_lote)           FROM lote));
SELECT setval(pg_get_serial_sequence('medicamento_sede','id'),(SELECT MAX(id) FROM medicamento_sede));
SELECT setval(pg_get_serial_sequence('orden',          'id_orden'),          (SELECT MAX(id_orden)          FROM orden));
SELECT setval(pg_get_serial_sequence('detalle_orden',  'id_detalle'),        (SELECT MAX(id_detalle)        FROM detalle_orden));
SELECT setval(pg_get_serial_sequence('movimiento_stock','id_movimiento'),   (SELECT MAX(id_movimiento)     FROM movimiento_stock));
