# Sistema de registro de pedidos – Juguería Bananos

App móvil Android para que los clientes de la Juguería Bananos registren sus pedidos sin pasar por WhatsApp, con su API REST. Proyecto del curso Desarrollo de Aplicaciones Móviles I (IDAT).

La app tiene 6 módulos: Autenticación (registro, inicio de sesión y recuperación de contraseña), Catálogo, Carrito, Registrar pedido, Pedidos (historial y detalle) y Perfil.

## Estructura del repositorio

```
├── app-pedidos/       App Android (Kotlin + XML)
└── backend_pedidos/   API REST (Spring Boot)
```

## Tecnologías

| Parte | Tecnologías |
|---|---|
| Backend | Java 21, Spring Boot 4, Spring Data JPA / Hibernate, Bean Validation, Spring Mail, BCrypt (spring-security-crypto), Lombok |
| Base de datos | MySQL |
| App móvil | Kotlin, XML con View Binding, Retrofit 2 + Gson, Room (SQLite), Glide, Navigation Component, Material 3 |

El backend está organizado en capas: `entity`, `repository`, `service`, `controller`, `dto`, `mapper` y `exception`.

## Cómo levantar el backend

1. **Crear la base de datos.** En MySQL, ejecutar en este orden los scripts de `backend_pedidos/src/main/resources/data/`:
   1. `creacion.sql`: crea la base `bd_bananos_app`, sus tablas y las sucursales.
   2. `carga_carta.sql`: carga las categorías, los productos y las presentaciones con sus precios.
   3. `carga_imagenes.sql`: asigna la imagen de cada producto.
2. **Configurar las credenciales.** Copiar `backend_pedidos/.env.example` como `backend_pedidos/.env` y completar los valores:

   | Variable | Descripción |
   |---|---|
   | `DB_URL`, `DB_USER`, `DB_PASSWORD` | Conexión a MySQL |
   | `MAIL_HOST`, `MAIL_PORT` | Servidor SMTP (Gmail: `smtp.gmail.com`, `587`) |
   | `MAIL_USERNAME`, `MAIL_PASSWORD` | Cuenta que envía la contraseña temporal. En Gmail, `MAIL_PASSWORD` es una *contraseña de aplicación* de 16 letras |
   | `MAIL_FROM_NAME` | Nombre del remitente de los correos |

   El archivo `.env` está en el `.gitignore`. **Nunca lo subas al repositorio.**
3. **Ejecutar la API** desde `backend_pedidos/`:

   ```bash
   ./mvnw spring-boot:run        # en Windows: mvnw.cmd spring-boot:run
   ```

   La API queda en `http://localhost:8081/api`. Las imágenes de la carta se sirven desde `http://localhost:8081/img/productos/`.

### Endpoints

| Método | Ruta | Uso |
|---|---|---|
| POST | `/api/auth/registro` | Registrar cliente |
| POST | `/api/auth/login` | Iniciar sesión |
| POST | `/api/auth/recuperar-contrasena` | Enviar una contraseña temporal al correo |
| GET / PUT | `/api/clientes/{idCliente}` | Ver y editar el perfil |
| PUT | `/api/clientes/{idCliente}/contrasena` | Cambiar contraseña |
| GET | `/api/catalogo/categorias` | Categorías de la carta |
| GET | `/api/catalogo/productos?idCategoria=&buscar=` | Productos con búsqueda y filtro |
| GET | `/api/sucursales` | Sucursales de atención |
| POST / GET | `/api/clientes/{idCliente}/pedidos` | Registrar pedido / Mis pedidos |
| GET | `/api/clientes/{idCliente}/pedidos/{idPedido}` | Detalle de pedido |

## Cómo ejecutar la app móvil

1. Abrir la carpeta `app-pedidos/` en Android Studio y esperar a que termine el Gradle Sync.
2. Revisar la URL del backend en `app/src/main/java/pe/idat/app_pedidos/util/Constantes.kt` (`BASE_URL`):
   - **Emulador:** `http://10.0.2.2:8081/` (10.0.2.2 apunta al `localhost` de tu PC). Si dejas `http://localhost:8081/`, ejecuta antes `adb reverse tcp:8081 tcp:8081`.
   - **Celular físico:** la IP de tu PC en la misma red Wi-Fi, por ejemplo `http://192.168.1.50:8081/`.
3. Ejecutar la app con el backend levantado.

La sesión del cliente y el carrito se guardan en SQLite (Room), así que se mantienen aunque se cierre la app.

## Integrantes

- Capcha
- Vasquez Cayo
- Vasquez Huacause
- Zuñiga
