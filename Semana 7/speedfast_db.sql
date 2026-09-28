CREATE DATABASE IF NOT EXISTS speedfast_db;

USE speedfast_db;

CREATE TABLE repartidor (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedido (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        direccion VARCHAR(150) NOT NULL,
                        distancia DECIMAL(10,2) NOT NULL,
                        tipo VARCHAR(30) NOT NULL,
                        estado VARCHAR(20) NOT NULL
);

CREATE TABLE entrega (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         id_pedido INT NOT NULL UNIQUE,
                         id_repartidor INT NOT NULL,
                         fecha DATE NOT NULL,
                         hora TIME NOT NULL,

                         CONSTRAINT fk_entrega_pedido
                             FOREIGN KEY (id_pedido)
                                 REFERENCES pedido(id),

                         CONSTRAINT fk_entrega_repartidor
                             FOREIGN KEY (id_repartidor)
                                 REFERENCES repartidor(id)
);

INSERT INTO repartidor (nombre) VALUES
                                    ('Camila Soto'),
                                    ('Daniela Tapia'),
                                    ('Luis Díaz');

SHOW TABLES;

SELECT * FROM repartidor;