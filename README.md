Documentación de Funcionalidades: StudyClass
StudyClass es una solución móvil desarrollada para la plataforma Android diseñada para optimizar la gestión, consulta y reserva de horarios de clases de estudio. La aplicación utiliza una arquitectura robusta basada en Java para la lógica principal, XML para el diseño de interfaces tradicionales y Kotlin para componentes auxiliares de soporte.
1. Sistema de Autenticación y Perfiles
La aplicación implementa un sistema de control de acceso diferenciado por roles:
•
Registro de Usuarios: Permite la creación de nuevas cuentas de tipo "USER" mediante un flujo dinámico con validaciones en tiempo real (evita duplicados, campos vacíos y protege nombres de sistema).
•
Roles Predefinidos:
◦
USER: Estudiantes que buscan reservar espacios.
◦
ADMIN: Personal encargado de la gestión de la disponibilidad académica.
•
Persistencia en Memoria: Utiliza un componente DataStore centralizado que gestiona las colecciones de usuarios y horarios sin depender de bases de datos externas, garantizando rapidez en la ejecución.
2. Panel del Usuario (UserHomeActivity)
Diseñado como un centro de control personal, ofrece:
•
Gestión Académica: Acceso directo a la tabla de horarios para realizar nuevas reservas.
•
Conectividad (Intents Implícitos):
◦
Ubicación: Apertura de Google Maps para localizar el centro de estudios.
◦
Web Institucional: Acceso al portal oficial mediante el navegador del dispositivo.
◦
Contacto Telefónico: Marcación rápida para soporte o consultas.
◦
Soporte vía Correo: Envío de correos electrónicos pre-configurados para dudas académicas.
3. Panel de Administración (AdminHomeActivity)
Proporciona herramientas de control global sobre el sistema:
•
Control de Inscripciones: Capacidad de abrir o cerrar el proceso de reservas para todos los usuarios con un solo toque.
•
Monitorización de Estado: Visualización en tiempo real del estado del sistema de inscripciones.
•
Gestión de Disponibilidad: Acceso a la configuración avanzada de cada bloque horario.
4. Gestión de Horarios y Reservas (HorariosActivity)
Es el núcleo funcional de la app, donde se visualizan las clases de Lunes a Viernes en bloques de 2 horas.
•
Lógica de Reserva (User): Un usuario puede reservar un ramo si las inscripciones están abiertas, el horario está habilitado y no tiene otra clase en el mismo bloque.
•
Lógica de Cancelación: El usuario puede cancelar sus propias reservas; el administrador tiene el poder de cancelar cualquier reserva y habilitar o deshabilitar ramos según la necesidad.
•
Integración con Calendario: Los usuarios pueden exportar sus reservas confirmadas directamente al calendario nativo de Android mediante un Intent implícito, asegurando recordatorios automáticos.
•
Optimización con Threads: La carga y el procesamiento de la tabla de horarios se realiza en un hilo secundario (Thread), evitando que la interfaz de usuario se congele y garantizando una navegación fluida.
5. Especificaciones Técnicas y Diseño
•
Interoperabilidad: Uso de ScheduleHelper en Kotlin para procesar estados visuales complejos mediante métodos estáticos accesibles desde Java.
•
Interfaz de Usuario (UI): Diseño limpio y moderno basado en TableLayout y ScrollView, con un esquema de colores azul profesional y el uso de emojis para una mejor experiencia de usuario (UX).
•
Seguridad y Validación: Verificación previa de la existencia de aplicaciones compatibles (resolveActivity) antes de ejecutar cualquier acción externa, evitando errores de ejecución (crashes).
Este ecosistema funcional convierte a StudyClass en una herramienta integral, eficiente y lista para entornos de producción académica.

Intents aplicados en la aplicacion:

Explicitos:

MainActivity -> UserHomeActivity
MainActivity -> AdminHomeActivity
Home -> HorariosActivity

Implicitos:

Maps: Localizacion de la sede (UserHomeActivity)
Web: Enlace Institucional (UserHomeActivity)
Telefono: Marcación segura (UserHomeActivity)
Correo: Soporte directo (UserHomeActivity)
Calendario: Agendar reserva (HorariosActivity)

Capturas de pantalla 
1<img width="343" height="758" alt="App login studyclass" src="https://github.com/user-attachments/assets/44e83235-48c5-4b14-8487-793b1ac4081c" />

2<img width="352" height="755" alt="App usuariohome studyclass" src="https://github.com/user-attachments/assets/b36b5b07-64e1-4321-9a34-a9ab93633740" />


3<img width="342" height="751" alt="App horarios studyclass" src="https://github.com/user-attachments/assets/bbfd87b2-289f-446d-8f8f-d8acbd03a55c" />


4<img width="347" height="763" alt="App horarioadmin studyclass" src="https://github.com/user-attachments/assets/c39ace4b-4f98-4c87-a6a5-e099783763ea" />


