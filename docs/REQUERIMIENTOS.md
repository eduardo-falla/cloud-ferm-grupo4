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

---

## 3. Características (Features) por épica e Historias de Usuario

---