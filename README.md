# Vehículos Service - Microservicio Quarkus

Un microservicio completo desarrollado con Java 17 y Quarkus para la gestión de un catálogo de vehículos con autenticación JWT.

## 🚀 Características

- **Framework**: Quarkus 3.2.12.Final
- **Base de datos**: SQL Server con JPA/Panache
- **Autenticación**: JWT (JSON Web Tokens)
- **Documentación**: Swagger/OpenAPI
- **Validaciones**: Bean Validation
- **Pruebas**: JUnit 5 + REST-assured
- **Paginación y filtrado**: Soporte completo

## 📋 Prerrequisitos

- Java 17 o superior
- Maven 3.8+
- SQL Server (localhost:1433)
- Usuario: `sa`, Contraseña: `Murcielago*`

## 🛠️ Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone <repository-url>
cd vehiculos-service
```

### 2. Configurar SQL Server
Asegúrate de tener SQL Server ejecutándose en `localhost:1433` con:
- Usuario: `sa`
- Contraseña: `Murcielago*`
- La base de datos `vehiculos_db` se creará automáticamente

### 3. Ejecutar en modo desarrollo
```bash
mvn quarkus:dev
```

El servicio estará disponible en: `http://localhost:8080`

### 4. Acceder a Swagger UI
Una vez iniciado el servicio, accede a la documentación interactiva:
```
http://localhost:8080/q/swagger-ui
```

## 🔐 Autenticación

### Obtener Token JWT
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "secret"
  }'
```

**Respuesta:**
```json
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9...",
  "username": "admin",
  "expiresIn": 3600
}
```

### Usuarios disponibles
- `admin` / `secret`
- `user` / `secret`

## 🚗 API Endpoints

### Autenticación
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/login` | Iniciar sesión |

### Vehículos (Requieren autenticación)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/vehiculos` | Listar vehículos (con paginación y filtros) |
| GET | `/vehiculos/{id}` | Obtener vehículo por ID |
| POST | `/vehiculos` | Crear nuevo vehículo |
| PUT | `/vehiculos/{id}` | Actualizar vehículo |
| DELETE | `/vehiculos/{id}` | Eliminar vehículo |

## 📝 Ejemplos de uso con cURL

### 1. Obtener token de autenticación
```bash
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "secret"}' | \
  jq -r '.token')
```

### 2. Listar todos los vehículos
```bash
curl -X GET http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN"
```

### 3. Listar vehículos con paginación y filtros
```bash
curl -X GET "http://localhost:8080/vehiculos?page=0&size=5&marca=Toyota&sortBy=anio" \
  -H "Authorization: Bearer $TOKEN"
```

### 4. Obtener vehículo por ID
```bash
curl -X GET http://localhost:8080/vehiculos/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 5. Crear nuevo vehículo
```bash
curl -X POST http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Nissan",
    "modelo": "Sentra",
    "anio": 2023,
    "precio": 27000.00,
    "color": "Azul",
    "transmision": "AUTOMATICA",
    "combustible": "GASOLINA",
    "puertas": 4
  }'
```

### 6. Actualizar vehículo
```bash
curl -X PUT http://localhost:8080/vehiculos/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Toyota",
    "modelo": "Corolla",
    "anio": 2024,
    "precio": 26000.00,
    "color": "Negro",
    "transmision": "AUTOMATICA",
    "combustible": "HIBRIDO",
    "puertas": 4
  }'
```

### 7. Eliminar vehículo
```bash
curl -X DELETE http://localhost:8080/vehiculos/1 \
  -H "Authorization: Bearer $TOKEN"
```

## 🏗️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/vehiculos/
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── model/              # Entidades JPA
│   │   ├── resource/           # Controladores REST
│   │   └── service/            # Lógica de negocio
│   └── resources/
│       ├── META-INF/resources/ # Claves JWT
│       ├── application.properties
│       └── import.sql          # Datos iniciales
└── test/
    └── java/com/example/vehiculos/
        ├── resource/           # Pruebas de integración
        └── service/            # Pruebas unitarias
```

## 🧪 Ejecutar Pruebas

### Pruebas unitarias
```bash
mvn test
```

### Pruebas de integración
```bash
mvn verify
```

### Ejecutar todas las pruebas
```bash
mvn clean test
```

## 📊 Modelo de Datos

### Vehículo
```json
{
  "id": "Long (autogenerado)",
  "marca": "String (obligatorio, máx 50 chars)",
  "modelo": "String (obligatorio, máx 50 chars)",
  "anio": "Integer (obligatorio, > 1900)",
  "precio": "BigDecimal (obligatorio, > 0)",
  "color": "String (opcional, máx 30 chars)",
  "transmision": "Enum [MANUAL, AUTOMATICA]",
  "combustible": "Enum [GASOLINA, DIESEL, ELECTRICO, HIBRIDO]",
  "puertas": "Integer (1-10)"
}
```

### Validaciones
- `marca`, `modelo`, `anio`, `precio`: Obligatorios
- `anio`: Debe ser mayor a 1900 y menor a 2030
- `precio`: Debe ser mayor a 0
- `puertas`: Entre 1 y 10 (si se especifica)

## 🔧 Configuración

### application.properties
```properties
# Base de datos
quarkus.datasource.db-kind=mssql
quarkus.datasource.username=sa
quarkus.datasource.password=Murcielago*
quarkus.datasource.jdbc.url=jdbc:sqlserver://localhost:1433;databaseName=vehiculos_db;trustServerCertificate=true

# JWT
mp.jwt.verify.publickey.location=META-INF/resources/publicKey.pem
mp.jwt.verify.issuer=https://vehiculos-service.com

# Swagger
quarkus.swagger-ui.always-include=true
quarkus.swagger-ui.path=/q/swagger-ui
```

## 🚀 Despliegue

### Compilar para producción
```bash
mvn clean package
```

### Ejecutar JAR compilado
```bash
java -jar target/quarkus-app/quarkus-run.jar
```

### Compilación nativa (opcional)
```bash
mvn clean package -Pnative
```

## 📈 Monitoreo y Salud

- **Health Check**: `http://localhost:8080/q/health`
- **Métricas**: `http://localhost:8080/q/metrics`
- **OpenAPI Spec**: `http://localhost:8080/q/openapi`

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 🆘 Soporte

Si encuentras algún problema o tienes preguntas:

1. Revisa la documentación de Swagger UI
2. Verifica que SQL Server esté ejecutándose
3. Asegúrate de usar el token JWT en las peticiones autenticadas
4. Consulta los logs de la aplicación para más detalles

---

**Desarrollado con ❤️ usando Quarkus**
