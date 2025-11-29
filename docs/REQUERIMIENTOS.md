# Requerimientos del proyecto CLOUD-FERM WEB

## 1. Contexto del proyecto

CLOUD-FERM WEB es un **Sistema de Información y Gestión de Trazabilidad** diseñado para la Finca Don Valentín. Su objetivo es digitalizar el proceso de fermentación de cacao, sustituyendo las bitácoras manuales en papel por una aplicación web responsiva.

El sistema permite al productor gestionar lotes asignando responsables específicos y permite a los operarios registrar manualmente las variables críticas (temperatura, humedad) y actividades de control.

El alcance es **exclusivamente de software** (Aplicación Web), utilizando **Angular (Frontend)**, **Java Spring Boot (Backend)** y **Supabase (Base de Datos)**.

---

## 2. Lista Formal de Requerimientos del Sistema

Esta sección detalla las capacidades técnicas y restricciones que el sistema debe cumplir, sirviendo como base para el desarrollo y las pruebas de aceptación.

### 2.1 Requerimientos Funcionales (RF)

| ID | Módulo | Descripción | Prioridad |
| :--- | :--- | :--- | :--- |
| **RF-001** | Seguridad | El sistema debe autenticar a los usuarios mediante credenciales (correo/contraseña) encriptadas. | Alta |
| **RF-002** | Seguridad | El sistema debe gestionar dos roles de usuario: **Administrador** (Acceso total) y **Operario** (Acceso restringido a registro). | Alta |
| **RF-003** | Lotes | El sistema debe permitir al Administrador crear un nuevo lote registrando: Código único, Variedad, Tiempo esti. de fermentación ,Peso y **asignando un Operario Responsable** de la lista de usuarios. | **Crítica** |
| **RF-004** | Lotes | El sistema debe permitir cambiar el estado de un lote (En Proceso -> Finalizado) para bloquear ediciones futuras. | Media |
| **RF-005** | Bitácora | El sistema debe proveer un formulario para que el Operario registre manualmente el **PH**, **Temperatura de la Masa**, **Temperatura Ambiente** y **Humedad Relativa**. | **Crítica** |
| **RF-006** | Bitácora | El sistema debe permitir al Operario registrar la ejecución de un **Volteo/Mezcla**, guardando automáticamente la fecha y hora del registro. | Alta |
| **RF-007** | Bitácora | El sistema debe permitir ingresar observaciones de texto libre (cualitativas) asociadas a un registro diario. | Media |
| **RF-008** | Visualización | El sistema debe generar un gráfico de líneas (Curva de Fermentación) mostrando la evolución de la temperatura masa , temperatura de ambiente , humedad masa, ph   vs. tiempo por lote. | Alta |
| **RF-009** | Reportes | El sistema debe permitir la exportación de los datos de un lote en formatos **Excel (.xlsx)** y **PDF**. | Baja |

### 2.2 Requerimientos No Funcionales (RNF)

| ID | Atributo | Descripción |
| :--- | :--- | :--- |
| **RNF-001** | **Responsividad** | La interfaz de usuario debe ser *Mobile-First*, garantizando una visualización correcta y funcional en dispositivos móviles (Android/iOS) utilizados en campo. |
| **RNF-002** | **Arquitectura** | El sistema debe seguir una arquitectura desacoplada tipo **API REST**, separando el Frontend (Angular) del Backend (Spring Boot). |
| **RNF-003** | **Persistencia** | Todos los datos transaccionales deben almacenarse en una base de datos relacional PostgreSQL gestionada por **Supabase**. |
| **RNF-004** | **Seguridad** | Las contraseñas de los usuarios deben almacenarse utilizando un algoritmo de hash robusto (ej. BCrypt). |
| **RNF-005** | **Disponibilidad** | El sistema debe estar disponible para consulta y registro en modalidad 24/7 (SaaS). |
| **RNF-006** | **Usabilidad** | Los campos de entrada numéricos en la versión móvil deben activar el teclado numérico del dispositivo para facilitar la captura de datos. |

---

## 3. Épicas

### Épica 1: Gestión de Seguridad y Usuarios
**Descripción:**
Garantizar que solo el personal autorizado acceda al sistema y asegurar que existan cuentas de operarios registradas para poder asignarles responsabilidades sobre los lotes.

### Épica 2: Gestión de Lotes y Asignación de Responsables
**Descripción:**
Permitir la creación de lotes de cacao, donde el Administrador no solo define las características del grano, sino que **designa explícitamente qué operario es el responsable** de dicho lote.

### Épica 3: Bitácora Digital (Registro Manual)
**Descripción:**
Proveer interfaces móviles para que los operarios ingresen diariamente los datos de las variables ambientales y las actividades de manejo realizadas en los lotes bajo su responsabilidad.

### Épica 4: Visualización y Reportes
**Descripción:**
Transformar los datos en gráficos de curvas de fermentación y permitir la exportación de datos para auditoría.

---

## 4. Características (Features) e Historias de Usuario

### ÉPICA 1 – Gestión de Seguridad y Usuarios

#### Feature 1.1 – Autenticación y Administración de Personal
**Descripción:** Login seguro y gestión del directorio de empleados.

* **HU-01:** **Como** administrador, **quiero** iniciar sesión de forma segura **para** acceder al panel de gestión.
* **HU-02:** **Como** administrador, **quiero** registrar, editar o desactivar cuentas de operarios (con Nombre y Apellido) **para** mantener una lista actualizada del personal disponible para asignar a los lotes.

---

### ÉPICA 2 – Gestión de Lotes y Asignación

#### Feature 2.1 – Alta y Asignación de Lotes
**Descripción:** Configuración inicial del proceso productivo.

* **HU-03 (Relacionado a RF-003):** **Como** productor (Admin), **quiero** crear un nuevo lote ingresando su Código, Variedad, Peso y **seleccionar de una lista desplegable al Operario Responsable** **para** delegar oficialmente la supervisión de ese cajón a una persona específica.
* **HU-04:** **Como** productor, **quiero** editar la información del lote (incluyendo cambiar al responsable si es necesario) **para** corregir errores o gestionar cambios de turno.
* **HU-05:** **Como** productor, **quiero** cerrar un lote finalizado **para** bloquear la edición de datos y archivarlo en el histórico.

---

### ÉPICA 3 – Bitácora Digital (Registro Manual)

#### Feature 3.1 – Registro de Variables Ambientales
**Descripción:** Formulario web optimizado para carga de datos en campo.

* **HU-06:** **Como** operario, **quiero** visualizar una lista de los lotes que tengo asignados (o todos los activos) **para** seleccionar rápidamente aquel sobre el cual voy a reportar.
* **HU-07:** **Como** operario, **quiero** registrar manualmente la **Temperatura de la Masa (°C)**, **Temperatura Ambiente** y **Humedad (%)** **para** digitalizar la lectura del termómetro/higrómetro.
* **HU-08:** **Como** operario, **quiero** que el sistema guarde automáticamente la fecha/hora y mi identidad al guardar el registro **para** asegurar la trazabilidad de quién midió y cuándo.

#### Feature 3.2 – Registro de Actividades
**Descripción:** Documentación de acciones físicas.

* **HU-09:** **Como** operario, **quiero** registrar la ejecución de un **"Volteo/Mezcla"** **para** confirmar que se realizó la oxigenación del grano según el cronograma.
* **HU-10:** **Como** operario, **quiero** añadir observaciones cualitativas (texto) **para** alertar sobre olores, colores o incidencias visuales en el grano.

---

### ÉPICA 4 – Visualización y Reportes

#### Feature 4.1 – Análisis de Calidad
**Descripción:** Herramientas visuales para el productor.

* **HU-11:** **Como** productor, **quiero** ver la **Curva de Fermentación** (Gráfico Temp vs. Tiempo) de un lote específico **para** evaluar si el operario asignado está manteniendo el proceso en rangos óptimos.
* **HU-12:** **Como** productor, **quiero** exportar la ficha técnica del lote en **Excel/PDF** **para** entregarla al comprador o cooperativa como garantía de calidad.
