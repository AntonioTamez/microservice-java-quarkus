# Comandos cURL para probar el microservicio Vehículos

Este archivo contiene todos los comandos cURL necesarios para probar completamente el microservicio de vehículos.

## 🔐 Autenticación

### 1. Obtener token JWT
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "secret"
  }'
```

### 2. Guardar token en variable (Linux/Mac)
```bash
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "secret"}' | \
  jq -r '.token')
```

### 3. Guardar token en variable (Windows PowerShell)
```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8080/auth/login" -Method Post -ContentType "application/json" -Body '{"username": "admin", "password": "secret"}'
$TOKEN = $response.token
```

## 🚗 Operaciones CRUD de Vehículos

### 4. Listar todos los vehículos
```bash
curl -X GET http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN"
```

### 5. Listar vehículos con paginación
```bash
curl -X GET "http://localhost:8080/vehiculos?page=0&size=3" \
  -H "Authorization: Bearer $TOKEN"
```

### 6. Filtrar vehículos por marca
```bash
curl -X GET "http://localhost:8080/vehiculos?marca=Toyota" \
  -H "Authorization: Bearer $TOKEN"
```

### 7. Filtrar vehículos por marca y modelo
```bash
curl -X GET "http://localhost:8080/vehiculos?marca=Toyota&modelo=Corolla" \
  -H "Authorization: Bearer $TOKEN"
```

### 8. Ordenar vehículos por precio
```bash
curl -X GET "http://localhost:8080/vehiculos?sortBy=precio" \
  -H "Authorization: Bearer $TOKEN"
```

### 9. Obtener vehículo por ID
```bash
curl -X GET http://localhost:8080/vehiculos/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 10. Crear nuevo vehículo - Toyota Camry
```bash
curl -X POST http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Toyota",
    "modelo": "Camry",
    "anio": 2023,
    "precio": 32000.00,
    "color": "Plata",
    "transmision": "AUTOMATICA",
    "combustible": "GASOLINA",
    "puertas": 4
  }'
```

### 11. Crear nuevo vehículo - Tesla Model Y
```bash
curl -X POST http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Tesla",
    "modelo": "Model Y",
    "anio": 2024,
    "precio": 55000.00,
    "color": "Blanco",
    "transmision": "AUTOMATICA",
    "combustible": "ELECTRICO",
    "puertas": 5
  }'
```

### 12. Crear nuevo vehículo - Ford Mustang
```bash
curl -X POST http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Ford",
    "modelo": "Mustang",
    "anio": 2023,
    "precio": 45000.00,
    "color": "Rojo",
    "transmision": "MANUAL",
    "combustible": "GASOLINA",
    "puertas": 2
  }'
```

### 13. Actualizar vehículo existente (ID 1)
```bash
curl -X PUT http://localhost:8080/vehiculos/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Toyota",
    "modelo": "Corolla",
    "anio": 2024,
    "precio": 26500.00,
    "color": "Negro",
    "transmision": "AUTOMATICA",
    "combustible": "HIBRIDO",
    "puertas": 4
  }'
```

### 14. Eliminar vehículo (ID 2)
```bash
curl -X DELETE http://localhost:8080/vehiculos/2 \
  -H "Authorization: Bearer $TOKEN"
```

## ❌ Casos de error

### 15. Acceso sin token (401 Unauthorized)
```bash
curl -X GET http://localhost:8080/vehiculos
```

### 16. Token inválido (401 Unauthorized)
```bash
curl -X GET http://localhost:8080/vehiculos \
  -H "Authorization: Bearer invalid-token"
```

### 17. Vehículo no encontrado (404 Not Found)
```bash
curl -X GET http://localhost:8080/vehiculos/999 \
  -H "Authorization: Bearer $TOKEN"
```

### 18. Crear vehículo con datos inválidos (400 Bad Request)
```bash
curl -X POST http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "",
    "modelo": "Test",
    "anio": 1800,
    "precio": -1000.00
  }'
```

### 19. Login con credenciales incorrectas (401 Unauthorized)
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "wrongpassword"
  }'
```

## 🔍 Verificar respuestas

### 20. Verificar estructura de respuesta con jq (Linux/Mac)
```bash
curl -s -X GET http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" | jq '.'
```

### 21. Contar total de vehículos
```bash
curl -s -X GET http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" | jq '.total'
```

### 22. Obtener solo las marcas de vehículos
```bash
curl -s -X GET http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" | jq '.vehiculos[].marca'
```

## 📊 Pruebas de rendimiento

### 23. Crear múltiples vehículos en secuencia
```bash
for i in {1..5}; do
  curl -X POST http://localhost:8080/vehiculos \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d "{
      \"marca\": \"Marca$i\",
      \"modelo\": \"Modelo$i\",
      \"anio\": $((2020 + i)),
      \"precio\": $((20000 + i * 1000)).00,
      \"color\": \"Color$i\",
      \"transmision\": \"AUTOMATICA\",
      \"combustible\": \"GASOLINA\",
      \"puertas\": 4
    }"
  echo "Vehículo $i creado"
done
```

## 🏥 Endpoints de salud

### 24. Verificar salud de la aplicación
```bash
curl -X GET http://localhost:8080/q/health
```

### 25. Verificar métricas
```bash
curl -X GET http://localhost:8080/q/metrics
```

### 26. Obtener especificación OpenAPI
```bash
curl -X GET http://localhost:8080/q/openapi
```

## 📝 Notas importantes

1. **Token JWT**: Los tokens tienen una duración de 1 hora (3600 segundos)
2. **Usuarios disponibles**: `admin/secret` y `user/secret`
3. **Base URL**: Cambiar `localhost:8080` por la URL de tu servidor si es diferente
4. **Headers requeridos**: 
   - `Authorization: Bearer <token>` para endpoints protegidos
   - `Content-Type: application/json` para requests con body
5. **Validaciones**: 
   - Marca y modelo son obligatorios
   - Año debe ser > 1900 y < 2030
   - Precio debe ser > 0
   - Puertas debe estar entre 1 y 10

## 🚀 Script completo de prueba

```bash
#!/bin/bash
echo "=== Prueba completa del microservicio Vehículos ==="

# 1. Obtener token
echo "1. Obteniendo token..."
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "secret"}' | jq -r '.token')

if [ "$TOKEN" = "null" ]; then
  echo "Error: No se pudo obtener el token"
  exit 1
fi

echo "Token obtenido: ${TOKEN:0:20}..."

# 2. Listar vehículos
echo "2. Listando vehículos..."
curl -s -X GET http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" | jq '.total'

# 3. Crear vehículo
echo "3. Creando nuevo vehículo..."
RESPONSE=$(curl -s -X POST http://localhost:8080/vehiculos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Nissan",
    "modelo": "Altima",
    "anio": 2023,
    "precio": 29000.00,
    "color": "Azul",
    "transmision": "AUTOMATICA",
    "combustible": "GASOLINA",
    "puertas": 4
  }')

VEHICLE_ID=$(echo $RESPONSE | jq -r '.id')
echo "Vehículo creado con ID: $VEHICLE_ID"

# 4. Obtener vehículo por ID
echo "4. Obteniendo vehículo por ID..."
curl -s -X GET http://localhost:8080/vehiculos/$VEHICLE_ID \
  -H "Authorization: Bearer $TOKEN" | jq '.marca'

echo "=== Prueba completada ==="
```
