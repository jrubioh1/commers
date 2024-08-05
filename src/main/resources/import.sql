-- Inserta roles si no existen
INSERT INTO rol (name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO rol (name) VALUES ('ROLE_EMPLOYEE') ON CONFLICT DO NOTHING;
INSERT INTO rol (name) VALUES ('ROLE_CLIENT') ON CONFLICT DO NOTHING;

-- Insertar usuarios (empleados) con rol de administrador
INSERT INTO users (name, email, password, serial_user) VALUES ('Employee1', 'employee1@example.com', 'password1','1');
INSERT INTO users (name, email, password, serial_user) VALUES ('Employee2', 'employee2@example.com', 'password2','2');
INSERT INTO users (name, email, password, serial_user) VALUES ('Employee3', 'employee3@example.com', 'password3','3');
INSERT INTO users (name, email, password, serial_user) VALUES ('Employee4', 'employee4@example.com', 'password4','4');
INSERT INTO users (name, email, password, serial_user) VALUES ('Employee5', 'employee5@example.com', 'password5','5');


-- Asociar los usuarios con el rol de administrador
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_ADMIN') FROM users WHERE email = 'employee1@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_ADMIN') FROM users WHERE email = 'employee2@example.com';

-- Asociar los usuarios con el rol de employee

INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_EMPLOYEE') FROM users WHERE email = 'employee1@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_EMPLOYEE') FROM users WHERE email = 'employee2@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_EMPLOYEE') FROM users WHERE email = 'employee3@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_EMPLOYEE') FROM users WHERE email = 'employee4@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_EMPLOYEE') FROM users WHERE email = 'employee5@example.com';


-- Insertar empleados
INSERT INTO employees (id, active) SELECT id, true FROM users WHERE email = 'employee1@example.com';
INSERT INTO employees (id, active) SELECT id, true FROM users WHERE email = 'employee2@example.com';
INSERT INTO employees (id, active) SELECT id, true FROM users WHERE email = 'employee3@example.com';
INSERT INTO employees (id, active) SELECT id, true FROM users WHERE email = 'employee4@example.com';
INSERT INTO employees (id, active) SELECT id, true FROM users WHERE email = 'employee5@example.com';

-- Inserta usuarios (clientes)
INSERT INTO users (name, email, password, serial_user) VALUES ('John Doe', 'john.doe@example.com', 'password123','6');
INSERT INTO users (name, email, password, serial_user) VALUES ('Jane Smith', 'jane.smith@example.com', 'password123','7');
INSERT INTO users (name, email, password, serial_user) VALUES ('Robert Johnson', 'robert.johnson@example.com', 'password123','8');
INSERT INTO users (name, email, password, serial_user) VALUES ('Emily Davis', 'emily.davis@example.com', 'password123','9');
INSERT INTO users (name, email, password, serial_user) VALUES ('Michael Brown', 'michael.brown@example.com', 'password123','10');

-- Insertar clientes
INSERT INTO clients (id) SELECT id FROM users WHERE email = 'john.doe@example.com';
INSERT INTO clients (id) SELECT id FROM users WHERE email = 'jane.smith@example.com';
INSERT INTO clients (id) SELECT id FROM users WHERE email = 'robert.johnson@example.com';
INSERT INTO clients (id) SELECT id FROM users WHERE email = 'emily.davis@example.com';
INSERT INTO clients (id) SELECT id FROM users WHERE email = 'michael.brown@example.com';

-- Asigna el rol 'ROLE_CLIENT' a cada usuario 
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'john.doe@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'jane.smith@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'robert.johnson@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'emily.davis@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'michael.brown@example.com';

-- Insertar productos
INSERT INTO product (id, name, price) VALUES (1, 'Producto A', 10.50);
INSERT INTO product (id, name, price) VALUES (2, 'Producto B', 15.75);
INSERT INTO product (id, name, price) VALUES (3, 'Producto C', 20.00);
INSERT INTO product (id, name, price) VALUES (4, 'Producto D', 5.00);
INSERT INTO product (id, name, price) VALUES (5, 'Producto E', 8.25);
