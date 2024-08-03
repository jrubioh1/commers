-- Inserta roles si no existen
INSERT INTO rol (name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO rol (name) VALUES ('ROLE_CLIENT') ON CONFLICT DO NOTHING;

-- Insertar usuarios (empleados) con rol de administrador
INSERT INTO users (name, email, password) VALUES ('Employee1', 'employee1@example.com', 'password1');
INSERT INTO users (name, email, password) VALUES ('Employee2', 'employee2@example.com', 'password2');
INSERT INTO users (name, email, password) VALUES ('Employee3', 'employee3@example.com', 'password3');
INSERT INTO users (name, email, password) VALUES ('Employee4', 'employee4@example.com', 'password4');
INSERT INTO users (name, email, password) VALUES ('Employee5', 'employee5@example.com', 'password5');

-- Asociar los usuarios con el rol de administrador
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_ADMIN') FROM users WHERE email = 'employee1@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_ADMIN') FROM users WHERE email = 'employee2@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_ADMIN') FROM users WHERE email = 'employee3@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_ADMIN') FROM users WHERE email = 'employee4@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_ADMIN') FROM users WHERE email = 'employee5@example.com';

-- Insertar empleados
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee1@example.com';
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee2@example.com';
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee3@example.com';
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee4@example.com';
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee5@example.com';

-- Inserta usuarios (clientes)
INSERT INTO users (name, email, password) VALUES ('John Doe', 'john.doe@example.com', 'password123');
INSERT INTO users (name, email, password) VALUES ('Jane Smith', 'jane.smith@example.com', 'password123');
INSERT INTO users (name, email, password) VALUES ('Robert Johnson', 'robert.johnson@example.com', 'password123');
INSERT INTO users (name, email, password) VALUES ('Emily Davis', 'emily.davis@example.com', 'password123');
INSERT INTO users (name, email, password) VALUES ('Michael Brown', 'michael.brown@example.com', 'password123');

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
