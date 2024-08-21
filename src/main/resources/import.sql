-- Inserta roles si no existen
INSERT INTO rol (name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO rol (name) VALUES ('ROLE_EMPLOYEE') ON CONFLICT DO NOTHING;
INSERT INTO rol (name) VALUES ('ROLE_CLIENT') ON CONFLICT DO NOTHING;

-- Insertar usuarios (empleados) con rol de administrador
INSERT INTO users (name,lastname,telephone_number ,  email, password, serial_user, enabled) VALUES ('Employee1', 'last1','456456556', 'employee1@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','1', true);
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user, enabled) VALUES ('Employee2','last1','456456556', 'employee2@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','2', true);
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user, enabled) VALUES ('Employee3','last1','456456556', 'employee3@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','3', true);
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user, enabled) VALUES ('Employee4','last1', '456456556','employee4@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','4', true);
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user, enabled) VALUES ('Employee5','last1', '456456556','employee5@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','5', true);


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
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee1@example.com';
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee2@example.com';
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee3@example.com';
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee4@example.com';
INSERT INTO employees (id) SELECT id FROM users WHERE email = 'employee5@example.com';

-- Inserta usuarios (clientes)
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user) VALUES ('John Doe', 'last1','456456556','john.doe@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','6');
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user) VALUES ('Jane Smith','last1','456456556', 'jane.smith@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','7');
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user) VALUES ('Robert Johnson','last1','456456556', 'robert.johnson@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','8');
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user) VALUES ('Emily Davis','last1','456456556', 'emily.davis@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','9');
INSERT INTO users (name,lastname,telephone_number , email, password, serial_user) VALUES ('Michael Brown','last1','456456556', 'michael.brown@example.com', '$2a$10$LqDl4BcvGSc7RgLOtfluEuu4VE/MQZj0aYtAL5JbboPi5bk2ZMe0i','10');

-- Insertar clientes

INSERT INTO clients (id, address) VALUES ((SELECT id FROM users WHERE email = 'john.doe@example.com'), 'address1');
INSERT INTO clients (id, address) VALUES ((SELECT id FROM users WHERE email = 'jane.smith@example.com'), 'address1');
INSERT INTO clients (id, address) VALUES ((SELECT id FROM users WHERE email = 'robert.johnson@example.com'), 'address1');
INSERT INTO clients (id, address) VALUES ((SELECT id FROM users WHERE email = 'emily.davis@example.com'), 'address1');
INSERT INTO clients (id, address) VALUES ((SELECT id FROM users WHERE email = 'michael.brown@example.com'), 'address1');




-- Asigna el rol 'ROLE_CLIENT' a cada usuario 
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'john.doe@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'jane.smith@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'robert.johnson@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'emily.davis@example.com';
INSERT INTO users_roles (user_id, rol_id) SELECT id, (SELECT id FROM rol WHERE name = 'ROLE_CLIENT') FROM users WHERE email = 'michael.brown@example.com';
