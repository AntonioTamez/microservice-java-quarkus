-- Initial data for testing
INSERT INTO usuario (id, username, password) VALUES (1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'); -- password: secret
INSERT INTO usuario (id, username, password) VALUES (2, 'user', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'); -- password: secret

-- Sample vehicles
INSERT INTO vehiculo (id, marca, modelo, anio, precio, color, transmision, combustible, puertas) VALUES (1, 'Toyota', 'Corolla', 2023, 25000.00, 'Blanco', 'AUTOMATICA', 'GASOLINA', 4);
INSERT INTO vehiculo (id, marca, modelo, anio, precio, color, transmision, combustible, puertas) VALUES (2, 'Honda', 'Civic', 2022, 28000.00, 'Negro', 'MANUAL', 'GASOLINA', 4);
INSERT INTO vehiculo (id, marca, modelo, anio, precio, color, transmision, combustible, puertas) VALUES (3, 'Tesla', 'Model 3', 2023, 45000.00, 'Rojo', 'AUTOMATICA', 'ELECTRICO', 4);
INSERT INTO vehiculo (id, marca, modelo, anio, precio, color, transmision, combustible, puertas) VALUES (4, 'Ford', 'F-150', 2023, 35000.00, 'Azul', 'AUTOMATICA', 'GASOLINA', 4);
INSERT INTO vehiculo (id, marca, modelo, anio, precio, color, transmision, combustible, puertas) VALUES (5, 'BMW', 'X5', 2022, 65000.00, 'Gris', 'AUTOMATICA', 'HIBRIDO', 5);
