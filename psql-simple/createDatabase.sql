-- Instalar a extensão dblink
CREATE EXTENSION IF NOT EXISTS dblink;

-- Verificar se a base de dados já existe, caso contrário, criá-la
DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'mydb') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE mydb');
   END IF;
END
$$;

-- Usar a base de dados
\c mydb;

-- Criar uma tabela de exemplo
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inserir dados de exemplo na tabela 'users'
INSERT INTO users (username, email) VALUES ('paulo', 'paulo@example.com');
INSERT INTO users (username, email) VALUES ('admin', 'admin@example.com');

-- Exibir os dados da tabela 'users'
SELECT * FROM users;
