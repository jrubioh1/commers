-- Insertar usuarios en la tabla users
INSERT INTO users (name, email, password) VALUES ('Alice', 'alice@example.com', 'password1');
INSERT INTO users (name, email, password) VALUES ('Bob', 'bob@example.com', 'password2');
INSERT INTO users (name, email, password) VALUES ('Charlie', 'charlie@example.com', 'password3');
INSERT INTO users (name, email, password) VALUES ('David', 'david@example.com', 'password4');
INSERT INTO users (name, email, password) VALUES ('Eve', 'eve@example.com', 'password5');

-- Obtener los IDs generados automáticamente y usarlos para insertar en employees
INSERT INTO employees (id) VALUES (1); -- Correspondiente a Alice
INSERT INTO employees (id) VALUES (2); -- Correspondiente a Bob
INSERT INTO employees (id) VALUES (3); -- Correspondiente a Charlie
INSERT INTO employees (id) VALUES (4); -- Correspondiente a David
INSERT INTO employees (id) VALUES (5); -- Correspondiente a Eve
