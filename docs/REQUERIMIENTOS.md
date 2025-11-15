# Requerimientos del proyecto CLOUD-FERM

## 1. Contexto del proyecto

CLOUD-FERM es un **Sistema Cloud de Gestión de Fermentación de Cacao** que permite al productor y al personal de la Finca Don Valentín supervisar, desde la nube, las variables críticas del proceso de fermentación (temperatura, humedad, CO₂, COVs), ejecutar comandos de actuación remota (mezclado, reinicio) y gestionar la trazabilidad histórica de los lotes de cacao mediante reportes almacenados en una base de datos en la nube.

El alcance de este proyecto se limita al **componente de software en la nube** (aplicación web + servicios de integración), quedando fuera la implementación física de hardware y sensores en campo.

---

## 2. Épicas

### Épica 1: Monitoreo y visualización en tiempo real de la fermentación

**Descripción:**  
Permitir que los usuarios visualicen en tiempo real las variables críticas del proceso de fermentación de cacao a través de un panel web centralizado (dashboard), utilizando la telemetría recibida desde los dispositivos de campos.

---

### Épica 2: Actuación y control remoto del proceso

**Descripción:**  
Proporcionar una interfaz en la aplicación web para enviar comandos a los dispositivos de campo (mezclado, reinicio, configuración de umbrales), de manera que el proceso de fermentación pueda ser gestionado a distancia.

---

### Épica 3: Gestión de lotes y trazabilidad histórica

**Descripción:**  
Registrar los datos de cada lote de cacao, almacenar el historial de telemetría y eventos de control en una base de datos en la nube, y ofrecer la consulta/exportación de esa información para análisis y auditoría.

---

### Épica 4: Administración de usuarios y configuración del sistema

**Descripción:**  
Gestionar usuarios, roles y parámetros globales del sistema, incluyendo permisos de acceso, registro de dispositivos y parámetros de operación, asegurando la seguridad y mantenibilidad de la plataforma.


## 3. Características (Features) por épica e Historias de Usuario

## ÉPICA 1 – Monitoreo y visualización en tiempo real

### Feature 1.1 – Panel de control general (Dashboard)

**Descripción:**  
Pantalla principal que muestra el estado general de la fermentación, los fermentadores activos y un resumen de variables críticas.

**Historias de usuario:**

1. **Como** operario de la finca, **quiero** ver en un panel el estado general de todos los fermentadores activos **para** identificar rápidamente si algún lote presenta valores fuera de rango.

2. **Como** productor (Don Valentín), **quiero** ver un indicador visual (semáforo o alerta) cuando la temperatura u otra variable supere un umbral crítico **para** tomar decisiones oportunas sobre el proceso.

3. **Como** administrador del sistema, **quiero** poder seleccionar el fermentador desde una lista en el dashboard **para** acceder fácilmente al detalle de telemetría de cada lote.

---

### Feature 1.2 – Gráficas de telemetría en tiempo real

**Descripción:**  
Visualización gráfica de la evolución de temperatura, humedad, CO₂, COVs y estado de la batería o energía.

**Historias de usuario:**

1. **Como** operario, **quiero** visualizar gráficos de temperatura y humedad en tiempo real **para** verificar que el proceso de fermentación se mantiene dentro de los rangos recomendados.

2. **Como** analista de calidad, **quiero** filtrar los gráficos por rango de fecha y por parámetro (temperatura, CO₂, etc.) **para** analizar tendencias específicas de cada lote.

3. **Como** productor, **quiero** ver el estado de la batería y de la conectividad del dispositivo **para** asegurarme de que el sistema de monitoreo no se interrumpa durante la fermentación.

---

### Feature 1.3 – Visualización de imágenes del proceso (cámara)

**Descripción:**  
Mostrar en la aplicación web la última imagen capturada por el módulo de campo, así como un pequeño historial de imágenes relevantes.

**Historias de usuario:**

1. **Como** productor, **quiero** ver la última imagen del interior del fermentador **para** confirmar visualmente el estado físico del cacao sin desplazarme a la finca.

2. **Como** operario, **quiero** poder actualizar manualmente la imagen desde la interfaz **para** corroborar el estado del lote después de una acción de mezclado.

3. **Como** analista de calidad, **quiero** acceder al historial básico de imágenes etiquetadas por fecha y lote **para** correlacionar cambios visuales con los datos de telemetría.

---

## ÉPICA 2 – Actuación y control remoto

### Feature 2.1 – Envío de comandos de mezclado (volteo)

**Descripción:**  
Desde la interfaz web, permitir la activación remota del sistema de mezclado (volteo) del tambor para un lote específico.

**Historias de usuario:**

1. **Como** operario, **quiero** enviar un comando de “Iniciar mezclado” desde la web **para** realizar el volteo del cacao sin estar físicamente en la finca.

2. **Como** productor, **quiero** ver una confirmación de que el comando de mezclado fue recibido y ejecutado correctamente **para** tener confianza en la actuación remota.

3. **Como** administrador del sistema, **quiero** que todos los comandos enviados queden registrados con fecha, hora y usuario **para** mantener una trazabilidad completa de las acciones realizadas.

---

### Feature 2.2 – Configuración de frecuencia y duración del mezclado

**Descripción:**  
Permitir programar la frecuencia (cada cuántas horas) y la duración del mezclado para cada lote, desde la aplicación web.

**Historias de usuario:**

1. **Como** operario, **quiero** configurar la frecuencia de mezclado (por ejemplo, cada 24 horas) **para** adaptar el proceso de fermentación al tipo de cacao y a las recomendaciones técnicas.

2. **Como** productor, **quiero** definir la duración del mezclado en minutos **para** asegurar que el volteo sea suficiente, pero sin consumir energía de forma innecesaria.

3. **Como** administrador, **quiero** poder desactivar temporalmente la programación automática de mezclado **para** realizar pruebas o mantenimiento del sistema.

---

### Feature 2.3 – Gestión de umbrales y alertas

**Descripción:**  
Definir umbrales máximos/mínimos de temperatura y humedad, así como los canales de notificación (ej. correo) para alertas.

**Historias de usuario:**

1. **Como** productor, **quiero** configurar valores máximos y mínimos de temperatura para cada lote **para** recibir alertas cuando haya riesgo de sobre-fermentación o problemas de calidad.

2. **Como** operario, **quiero** recibir una notificación cuando la humedad supere el umbral permitido **para** tomar acciones preventivas (por ejemplo, ajustar la ventilación o revisar el sistema).

3. **Como** administrador, **quiero** configurar los correos electrónicos o teléfonos de contacto para cada tipo de alerta **para** asegurar que la información llegue a las personas correctas.

---


## ÉPICA 3 – Gestión de lotes y trazabilidad histórica

### Feature 3.1 – Registro de nuevos lotes de fermentación

**Descripción:**  
Formulario para registrar los datos iniciales de cada lote (fecha, variedad, peso, etc.) y asociarlos a un identificador único.

**Historias de usuario:**

1. **Como** operario, **quiero** registrar un nuevo lote con datos como fecha de inicio, variedad de cacao y peso húmedo **para** iniciar formalmente el proceso de fermentación en el sistema.

2. **Como** productor, **quiero** que a cada lote se le asigne un ID único **para** poder rastrear su historial de forma sencilla.

3. **Como** analista de calidad, **quiero** registrar observaciones iniciales (por ejemplo, nivel de madurez del grano) **para** correlacionarlas después con los resultados de fermentación.

---

### Feature 3.2 – Consulta de historial de telemetría y eventos

**Descripción:**  
Permitir buscar lotes históricos, visualizar su telemetría y los comandos ejecutados durante el proceso.

**Historias de usuario:**

1. **Como** analista de calidad, **quiero** buscar lotes por ID o por rango de fechas **para** revisar su comportamiento histórico y las decisiones tomadas.

2. **Como** productor, **quiero** visualizar el historial de eventos (comandos de mezclado, alertas, cambios de configuración) asociados a un lote **para** entender cómo se gestionó el proceso.

3. **Como** operario, **quiero** ver un resumen del historial de un lote (tiempo total de fermentación, número de mezclados, alertas disparadas) **para** mejorar mi toma de decisiones en futuros lotes.

---

### Feature 3.3 – Exportación de datos a CSV/Excel y generación de reportes

**Descripción:**  
Exportar la información histórica de un lote o de un conjunto de lotes a formatos CSV/Excel para análisis externo.

**Historias de usuario:**

1. **Como** analista de calidad, **quiero** exportar los datos de telemetría de un lote a Excel **para** realizar análisis estadísticos y comparaciones con otros lotes.

2. **Como** productor, **quiero** descargar un reporte de trazabilidad de un lote **para** presentarlo a potenciales compradores que exigen evidencia del control del proceso.

3. **Como** administrador, **quiero** definir el rango de fechas y las variables a incluir en la exportación **para** generar reportes específicos según las necesidades del negocio.

## ÉPICA 4 – Administración de usuarios y configuración del sistema

### Feature 4.1 – Gestión de usuarios y roles

**Descripción:**  
Módulo para registrar usuarios, asignar roles (operario, productor, administrador) y gestionar credenciales de acceso.

**Historias de usuario:**

1. **Como** administrador, **quiero** registrar nuevos usuarios en la plataforma **para** que el personal de la finca pueda acceder al sistema con sus propias credenciales.

2. **Como** administrador, **quiero** asignar roles (solo lectura, operador, administrador) **para** controlar qué acciones puede realizar cada tipo de usuario.

3. **Como** usuario del sistema, **quiero** poder cambiar mi contraseña de forma segura **para** proteger mi cuenta.

---

### Feature 4.2 – Registro y configuración de dispositivos de campo

**Descripción:**  
Pantalla para administrar el catálogo de dispositivos (fermentadores) conectados a la plataforma, con su configuración básica.

**Historias de usuario:**

1. **Como** administrador, **quiero** registrar un nuevo fermentador con su ID de dispositivo IoT **para** vincularlo correctamente a la aplicación web.

2. **Como** administrador, **quiero** marcar un dispositivo como “fuera de servicio” **para** evitar que genere datos o alarmas mientras está en mantenimiento.

3. **Como** operario, **quiero** ver la lista de fermentadores disponibles y su estado (activo, en mantenimiento, fuera de línea) **para** saber qué unidades están operativas.

---

### Feature 4.3 – Seguridad y auditoría básica

**Descripción:**  
Registrar acciones relevantes (login, envío de comandos, cambios de configuración) para fines de auditoría.

**Historias de usuario:**

1. **Como** administrador, **quiero** que el sistema registre los accesos al panel web **para** detectar posibles usos indebidos de las cuentas.

2. **Como** administrador, **quiero** que cada comando de mezclado quede asociado a un usuario y a una fecha/hora **para** mantener una trazabilidad completa de las acciones críticas.

3. **Como** auditor interno, **quiero** poder consultar un log básico de eventos **para** revisar el uso del sistema en periodos específicos.

---

## 4. Notas sobre alcance y exclusiones

- El alcance de este documento se centra en el **componente de software** (aplicación web y servicios en la nube).
- La **implementación física de sensores, microcontroladores, sistema de mezcla y paneles solares** se considera fuera del alcance del presente proyecto de software y es responsabilidad del cliente o de terceros.
- Se asume que existirá conectividad a internet en la finca (por ejemplo, mediante Starlink) para que los dispositivos de campo puedan enviar telemetría a la nube.
---
