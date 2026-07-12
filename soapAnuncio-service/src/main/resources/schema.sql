DROP TABLE IF EXISTS anuncio;

CREATE TABLE anuncio (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo      VARCHAR(100)  NOT NULL,
    descripcion TEXT          NOT NULL,
    imagen_url  VARCHAR(255),
    fecha_inicio DATE,
    fecha_fin    DATE,
    estado      BOOLEAN DEFAULT TRUE,
    UNIQUE KEY uq_titulo (titulo)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO anuncio (titulo, descripcion, imagen_url, fecha_inicio, fecha_fin, estado) VALUES
(
    'Promoción de vitaminas',
    '20% de descuento en todas las vitaminas y suplementos durante junio. Fortalece tu sistema inmunológico con nuestras marcas líderes: Vitamina C, D, Zinc y Omega-3.',
    'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=400',
    '2026-06-01', '2026-06-30', 1
),
(
    'Campaña de vacunación',
    'Vacunación gratuita para adultos mayores de 60 años. Disponible en todas nuestras sedes. Presenta tu DNI y carnet de vacunación. Sin cita previa, atención de lunes a sábado.',
    'https://images.unsplash.com/photo-1611689342806-0863700ce1e4?w=400',
    '2026-06-01', '2026-07-15', 1
),
(
    'Descuento en antibióticos',
    '15% de descuento en línea completa de antibióticos con receta médica válida. Incluye Amoxicilina, Azitromicina y Ciprofloxacino. Oferta válida hasta agotar stock.',
    'https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=400',
    '2026-06-15', '2026-06-30', 1
),
(
    'Control de presión arterial',
    'Medición gratuita de presión arterial en todas nuestras sedes sin necesidad de cita. Además, 10% de descuento en medicamentos antihipertensivos como Losartán y Enalapril.',
    'https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?w=400',
    '2026-06-01', '2026-07-31', 1
);
