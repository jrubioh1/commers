-- Cambiar el nombre de la tabla a "rol"
INSERT INTO rol (id, name) VALUES (1, 'ROLE_ADMIN');

-- Insertar usuarios (empleados) con rol de administrador
INSERT INTO users (id, name, email, password) VALUES (1, 'Employee1', 'employee1@example.com', 'password1');
INSERT INTO users (id, name, email, password) VALUES (2, 'Employee2', 'employee2@example.com', 'password2');
INSERT INTO users (id, name, email, password) VALUES (3, 'Employee3', 'employee3@example.com', 'password3');
INSERT INTO users (id, name, email, password) VALUES (4, 'Employee4', 'employee4@example.com', 'password4');
INSERT INTO users (id, name, email, password) VALUES (5, 'Employee5', 'employee5@example.com', 'password5');

-- Asociar los usuarios con el rol de administrador
INSERT INTO users_roles (user_id, rol_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, rol_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, rol_id) VALUES (3, 1);
INSERT INTO users_roles (user_id, rol_id) VALUES (4, 1);
INSERT INTO users_roles (user_id, rol_id) VALUES (5, 1);

-- Insertar empleados
INSERT INTO employees (id) VALUES (1);
INSERT INTO employees (id) VALUES (2);
INSERT INTO employees (id) VALUES (3);
INSERT INTO employees (id) VALUES (4);
INSERT INTO employees (id) VALUES (5);