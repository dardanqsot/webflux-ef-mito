# Proyecto: Trabajo Final del curso Spring Webflux

Este proyecto es un entregable de fin curso "Spring Webflux, Mitocode".
Existen 3 controladores: Student, Course y Enrollment, cada uno con sus Endpoints, siendo dos servicios principales solicitados en el entregable de Trabajo Final.

## Servicio 1: Listar estudiantes ordenados de forma ascendente/descendente por edad
Este servicio te permite obtener lista de estudiantes ordenados de forma ascendente/descendente en base a su edad

### Ruta de Consumo forma clásica
GET:  
- localhost:8080/students/ordered-by-age?order=asc
- localhost:8080/students/ordered-by-age?order=desc

## Servicio 2: Listar estudiantes ordenados de forma ascendente/descendente por edad con functional endpoints
Este servicio te permite obtener lista de estudiantes ordenados de forma ascendente/descendente en base a su edad

### Ruta de Consumo que utiliza Functional Endpoints
GET:  
- localhost:8080/v2/students/ordered-by-age?order=asc
- localhost:8080/v2/students/ordered-by-age?order=desc

## Ejecución del Proyecto

1. Clonar el repositorio o descarga los archivos.

2. Abrir una terminal en el directorio raíz del proyecto.

3. Ejecutar el siguiente comando para compilar y ejecutar la aplicación:

mvn spring-boot:run
