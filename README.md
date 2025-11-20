# CLOUD-FERM WEB – Sistema de Gestión Digital de Fermentación de Cacao

## Descripción general

**CLOUD-FERM WEB** es una aplicación web diseñada para **digitalizar, gestionar y trazar** el proceso de fermentación de cacao en la Finca Don Valentín.

El sistema sustituye las bitácoras manuales en papel por una plataforma digital segura y accesible vía web/móvil, permitiendo:

- **Registro Manual Digital:** Ingreso diario de variables críticas (temperatura, humedad) y actividades de control (volteos/mezclas) por parte de los operarios en campo.
- **Gestión de Lotes:** Creación, seguimiento y cierre de lotes de producción para asegurar la trazabilidad desde la cosecha.
- **Visualización y Toma de Decisiones:** Generación automática de gráficas (curvas de fermentación) y reportes históricos para el análisis de calidad.

Este proyecto corresponde al curso **Capstone / Administración de Proyectos de Software**.

## Integrantes del equipo

- **Eduardo Falla Delgado** – Rol Líder técnico
- **Myke Perez Arzapalo** – Rol Analista de requisitos
- **Diana Aragon Vilela** – Rol Product owner

## Enlace a la documentación

- [`docs/REQUERIMIENTOS.md`](docs/REQUERIMIENTOS.md)

## Arquitectura y Tecnologías

El proyecto implementa una arquitectura de software moderna y desacoplada:

- **Frontend (Cliente Web):**
  - **Framework:** Angular 16+ (TypeScript).
  - **UI/UX:** Angular Material (Diseño Responsivo Mobile-First).
- **Backend (API REST):**
  - **Lenguaje:** Java 17 / 21.
  - **Framework:** Spring Boot 3 (Spring Security, JPA).
- **Base de Datos:**
  - **Plataforma:** Supabase.
  - **Motor:** PostgreSQL (Almacenamiento relacional de usuarios, lotes y registros).
- **Control de versiones:** Git + GitHub.
