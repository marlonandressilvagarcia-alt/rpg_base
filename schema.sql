CREATE DATABASE IF NOT EXISTS rpg_grupal_db;
USE rpg_grupal_db;

CREATE TABLE IF NOT EXISTS heroes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL, -- 'Guerrero' o 'Mago'
    vida_maxima INT NOT NULL,
    nivel INT NOT NULL
    );