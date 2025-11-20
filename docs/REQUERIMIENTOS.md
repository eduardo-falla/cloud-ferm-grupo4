# Requerimientos del proyecto CLOUD-FERM WEB

## 1. Contexto del proyecto

CLOUD-FERM WEB es un **Sistema de Información y Gestión de Trazabilidad** diseñado para la Finca Don Valentín. Su objetivo es digitalizar el proceso de fermentación de cacao, sustituyendo las bitácoras manuales en papel por una aplicación web responsiva.

El sistema permite al productor y a los operarios registrar manualmente las variables críticas (temperatura, humedad), documentar las actividades de control (volteos/mezclas) y visualizar el comportamiento histórico de los lotes mediante gráficos estadísticos.

El alcance de este proyecto es **exclusivamente de software** (Aplicación Web), utilizando una arquitectura moderna con **Angular (Frontend)**, **Java Spring Boot (Backend)** y **Supabase (Base de Datos)**. No incluye integración con sensores físicos ni hardware IoT.

---

## 2. Épicas

### Épica 1: Gestión de Seguridad y Usuarios
**Descripción:**
Garantizar que solo el personal autorizado pueda acceder al sistema, diferenciando entre quienes administran el negocio y quienes operan en campo.

### Épica 2: Gestión de Lotes de Producción
**Descripción:**
Permitir la creación, seguimiento y cierre de lotes de cacao, asignando códigos únicos y características específicas (variedad, peso) para garantizar la trazabilidad desde el inicio.

### Épica 3: Bitácora Digital (Registro Manual)
**Descripción:**
Proveer interfaces amigables (optimizadas para móviles) para que los operarios ingresen diariamente los datos de las variables ambientales y las actividades de manejo realizadas en cada fermentador.

### Épica 4: Visualización y Reportes
**Descripción:**
Transformar los datos ingresados manualmente en información visual (gráficos y tablas) para la toma de decisiones y permitir la exportación de datos para auditoría o venta.

---

## 3. Características (Features) e Historias de Usuario

### ÉPICA 1 – Gestión de Seguridad y Usuarios

#### Feature 1.1 – Autenticación y Roles
**Descripción:** Sistema de login seguro y diferenciación de permisos.

* **HU-01:** **Como** administrador, **quiero** iniciar sesión con un usuario y contraseña encriptada **para** proteger la información confidencial de la finca.
* **HU-02:** **Como** administrador, **quiero** crear cuentas para los operarios con permisos limitados (solo registro) **para** evitar que modifiquen configuraciones críticas o eliminen lotes por error.

---

### ÉPICA 2 – Gestión de Lotes de Producción

#### Feature 2.1 – Administración de Lotes (CRUD)
**Descripción:** Funcionalidad para dar de alta nuevos procesos de fermentación.

* **HU-03:** **Como** productor (Don Valentín), **quiero** registrar un nuevo lote ingresando su código único, variedad de cacao (ej. CCN51) y peso bruto **para** iniciar el seguimiento de una nueva cosecha.
* **HU-04:** **Como** productor, **quiero** cerrar un lote cuando el proceso de fermentación haya terminado **para** que pase al histórico y no permita más registros de datos.
* **HU-05:** **Como** productor, **quiero** ver una lista de todos los lotes "En Proceso" **para** saber rápidamente cuántos cajones están activos actualmente.

---

### ÉPICA 3 – Bitácora Digital (Registro Manual)

#### Feature 3.1 – Registro de Variables Ambientales
**Descripción:** Formulario web para el ingreso de datos tomados con instrumentos manuales (termómetros/higrómetros).

* **HU-06:** **Como** operario (Jarvis), **quiero** seleccionar un lote activo desde mi celular y registrar la Temperatura de la Masa (°C) y la Temperatura Ambiente **para** guardar la evidencia del comportamiento térmico del día.
* **HU-07:** **Como** operario, **quiero** registrar el porcentaje de Humedad (%) leído del instrumento **para** completar la ficha técnica del día.
* **HU-08:** **Como** operario, **quiero** que el sistema registre automáticamente la fecha y hora de mi reporte **para** evitar errores de transcripción temporal.

#### Feature 3.2 – Registro de Actividades (Volteo)
**Descripción:** Registro de las acciones físicas realizadas sobre el cacao.

* **HU-09:** **Como** operario, **quiero** marcar en el sistema una casilla o botón que indique que se realizó el "Volteo" o "Mezcla" del cajón **para** certificar que se cumplió con la oxigenación requerida.
* **HU-10:** **Como** operario, **quiero** agregar observaciones de texto libre (ej. "Olor a vinagre", "Presencia de insectos") **para** reportar incidencias cualitativas que los números no reflejan.

---

### ÉPICA 4 – Visualización y Reportes

#### Feature 4.1 – Gráficas de Tendencia
**Descripción:** Visualización de la curva de fermentación basada en los datos históricos.

* **HU-11:** **Como** productor, **quiero** seleccionar un lote y ver un gráfico de línea de "Temperatura vs. Tiempo" **para** analizar si la curva de fermentación es la adecuada (fase anaeróbica/aeróbica).
* **HU-12:** **Como** productor, **quiero** ver una tabla resumen con todos los registros diarios de un lote específico **para** revisar los datos en detalle.

#### Feature 4.2 – Exportación de Datos
**Descripción:** Generación de archivos para uso externo.

* **HU-13:** **Como** productor, **quiero** descargar el historial completo de un lote cerrado en formato Excel (.xlsx) o PDF **para** compartirlo con clientes o certificar la calidad del proceso.

---

## 4. Requerimientos No Funcionales (Técnicos)

1.  **Responsividad (Mobile-First):** La aplicación web (Angular) debe visualizarse y operar correctamente en dispositivos móviles (Android/iOS) para facilitar la entrada de datos en campo.
2.  **Arquitectura:** El sistema debe seguir una arquitectura desacoplada con API REST en Java Spring Boot y Frontend en Angular.
3.  **Persistencia:** Todos los datos deben almacenarse de forma segura en Supabase (PostgreSQL).
4.  **Usabilidad:** Los formularios de registro deben tener botones grandes y validaciones numéricas para minimizar errores de dedo por parte de los operarios.
5.  **Disponibilidad:** El sistema debe estar disponible 24/7 para consulta y registro.
