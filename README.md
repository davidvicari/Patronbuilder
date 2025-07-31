👤 API REST de Usuarios — Patrón de Diseño Builder

API REST desarrollada con Spring Boot para la gestión de usuarios. Este proyecto se destaca por la implementación del patrón de diseño Builder, aplicado en la construcción de objetos DTO de manera flexible y legible. A través de este patrón se logra desacoplar la creación de objetos complejos, mejorando así la claridad del código, su mantenibilidad y escalabilidad.

📚 Tecnologías Utilizadas
Tecnología	    Propósito
Java 17	        Lenguaje de programación principal del proyecto.
Spring Boot	    Framework que agiliza la creación de aplicaciones Java con mínima configuración.
Spring Web	    Permite construir APIs RESTful de manera sencilla.
Spring Data JPA	Facilita la interacción con bases de datos usando repositorios y entidades JPA.
H2 Database	    Base de datos en memoria para desarrollo y pruebas sin instalación externa.
Gradle	        Sistema de construcción y gestión de dependencias del proyecto.
Lombok	        Reduce el código repetitivo generando automáticamente getters, setters, etc.
MapStruct	      Realiza conversiones entre DTOs y entidades de forma clara y eficiente.
SpringDoc OpenAPI	Genera documentación automática e interactiva de la API (Swagger UI).

🎯 Objetivos del Proyecto
    • Implementar una API REST clara, organizada y mantenible.
    • Aplicar el patrón de diseño Builder para construir objetos complejos.
    • Validar correctamente los datos de entrada.
    • Utilizar DTOs y Mappers para mantener una arquitectura desacoplada.
    • Aplicar buenas prácticas de desarrollo como manejo centralizado de errores.

✨ Funcionalidades
    • Crear un usuario (POST)
    • Obtener todos los usuarios (GET)
    • Obtener un usuario por ID (GET)
    • Actualizar parcialmente los datos de un usuario (PUT)
    • Eliminar un usuario por ID (DELETE)
    • Validación de datos robusta en la capa de servicio
    • Manejo global de errores y respuestas consistentes

📁 Arquitectura en Capas
 controller: Contiene los controladores REST, encargados de recibir y responder las solicitudes HTTP.
 dto: Define los objetos de transferencia de datos (DTO), utilizados para comunicar información entre la capa de presentación y la lógica de negocio.
 entity: Contiene las entidades JPA que representan las tablas de la base de datos.
 exception: Maneja las excepciones globalmente y proporciona respuestas coherentes ante errores.
 mapper: Contiene las clases que transforman objetos entre DTOs y entidades mediante MapStruct.
 repository: Acceso a datos usando Spring Data JPA. Se encarga de las operaciones con la base de datos.
 service: Contiene la lógica de negocio, validaciones y operaciones relacionadas con los datos.
 PatronBuilderApplication.java: Clase principal que arranca la aplicación Spring Boot.



🧱 Patrón de Diseño: Builder
El patrón Builder se implementa en la clase UsuarioDTO, permitiendo construir objetos con múltiples atributos de forma clara, flexible y controlada. 
Es útil especialmente cuando algunos campos son opcionales y se quiere evitar constructores con muchos parámetros.




Ejemplo de uso:
UsuarioDTO usuario = new UsuarioDTO.Builder()
    .id(1L)
    .nombre("Julian")
    .apellido("Gonzalez")
    .email("julian@example.com")
    .fechaDeNacimiento(LocalDate.of(2005, 5, 20))
    .genero("Masculino")
    .estadoCivil("Soltero")
    .build();

    
Este enfoque mejora:
    • La legibilidad del código.
    • La extensibilidad ante futuros cambios (agregar/quitar campos).
    • La robustez del modelo al evitar constructores largos y difíciles de mantener.


  
✅ Validación de Datos
Toda la lógica de validación se encuentra encapsulada dentro del servicio UsuarioService.
Se validan:
    • Campos obligatorios en la creación (nombre, email).
    • Campos condicionales en la actualización (se valida solo si el campo está presente en el JSON).
    • Formatos de nombre, apellido, email, género y estado civil.
    • Fecha de nacimiento en el pasado y edad mínima de 13 años.
Estas validaciones son realizadas con expresiones regulares, lógica de fecha (LocalDate), y reglas de negocio específicas para mantener la integridad de los datos ingresados.



    🧠 Manejo Global de Errores
El proyecto cuenta con una clase GlobalExceptionHandler ubicada en el paquete exception, que centraliza la gestión de errores. Esta clase permite devolver respuestas personalizadas y coherentes ante distintas excepciones, como:
    • ResponseStatusException: utilizada para validar datos inválidos o devolver errores con códigos HTTP específicos, como 400 Bad Request o 404 Not Found.
    • RuntimeException: utilizada para manejar errores inesperados, como cuando no se encuentra un recurso durante operaciones de actualización o eliminación.
Gracias a esta centralización del manejo de errores, se mejora la experiencia del cliente de la API y se mantiene una estructura limpia y mantenible en el código.


🏛️ Arquitectura REST
Método	Endpoint	                Descripción
GET	    /usuarios	                Obtiene todos los usuarios
POST	 /usuarios/crear	          Crea un nuevo usuario
PUT	   /usuarios/actualizar/{id}	Actualiza los datos de un usuario por ID
DELETE	/usuarios/eliminar/{id}	  Elimina un usuario por su ID


📌 Ejemplos de Uso
1. 🚀 Crear Usuario 1
URL: POST http://localhost:8080/usuarios/crear
Body (JSON):
{
  "nombre": "Lucas",
  "apellido": "Fernández",
  "email": "lucas.fernandez@gmail.com",
  "fechaDeNacimiento": "2000-05-10",
  "genero": "masculino",
  "estadoCivil": "soltero"
}

2. 🚀 Crear Usuario 2
URL: POST http://localhost:8080/usuarios/crear
Body (JSON):
{
  "nombre": "María",
  "apellido": "Gómez",
  "email": "maria.gomez@example.com",
  "fechaDeNacimiento": "1995-03-20",
  "genero": "femenino",
  "estadoCivil": "casado"
}

3. 📥 Obtener todos los usuarios
URL: GET http://localhost:8080/usuarios
Respuesta esperada:
[
  {
    "id": 1,
    "nombre": "Lucas",
    "apellido": "Fernández",
    "email": "lucas.fernandez@gmail.com",
    "fechaDeNacimiento": "2000-05-10",
    "genero": "masculino",
    "estadoCivil": "soltero"
  },
  {
    "id": 2,
    "nombre": "María",
    "apellido": "Gómez",
    "email": "maria.gomez@example.com",
    "fechaDeNacimiento": "1995-03-20",
    "genero": "femenino",
    "estadoCivil": "casado"
  }
]

4. 🔁 Actualizar datos del usuario con ID 1
URL: PUT http://localhost:8080/usuarios/actualizar/1
Body (JSON):
{
  "nombre": "Lucas Updated",
  "email": "lucas.updated@gmail.com",
  "estadoCivil": "casado"
}

5.  🗑️ Eliminar usuario con ID 2
URL: DELETE http://localhost:8080/usuarios/eliminar/2
(No body requerido)

6. 📥 Obtener todos los usuarios nuevamente
URL: GET http://localhost:8080/usuarios
Respuesta esperada:
[
  {
    "id": 1,
    "nombre": "Lucas Updated",
    "apellido": "Fernández",
    "email": "lucas.updated@gmail.com",
    "fechaDeNacimiento": "2000-05-10",
    "genero": "masculino",
    "estadoCivil": "casado"
  }
]


🎓 Conceptos Aplicados
    • Patrón Builder para creación flexible de objetos DTO.
    • DTO Pattern para encapsular y exponer solo los datos necesarios.
    • Validación de entradas robusta y centralizada.
    • Manejo global de errores con @ControllerAdvice.
    • Spring Data JPA para persistencia automática.
    • Arquitectura desacoplada en capas (controller, service, repository,etc).



👨‍💻 Autor
David Vicari
Estudiante de la Tecnicatura Universitaria en Programación
Universidad Tecnológica Nacional (UTN)





