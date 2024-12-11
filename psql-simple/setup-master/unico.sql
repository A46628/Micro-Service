-- Criação das bases de dados
CREATE DATABASE lojaadb;
CREATE DATABASE lojabdb;
CREATE DATABASE headquarters;

-- Conexão com o banco lojaadb
\c lojaadb;

-- Criação da extensão
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação das tabelas
CREATE TABLE lojas (
    id_loja UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_loja VARCHAR(100) NOT NULL
);

CREATE TABLE clientes (
    nif_cliente VARCHAR(15) NOT NULL PRIMARY KEY,
    nome_cliente VARCHAR(100) NOT NULL,
    endereco_cliente VARCHAR(255) NOT NULL,
    telefone_cliente VARCHAR(20),
    id_loja UUID,
    CONSTRAINT fk_clientes_lojas FOREIGN KEY (id_loja) REFERENCES lojas(id_loja)
);

CREATE TABLE produtos (
    id_produto UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_produto VARCHAR(100) NOT NULL,
    descricao_produto VARCHAR(255),
    id_loja UUID,
    preco_produto DECIMAL(10, 2),
    CONSTRAINT fk_produtos_lojas FOREIGN KEY (id_loja) REFERENCES lojas(id_loja)
);

CREATE TABLE vendas (
    id_venda UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nif_cliente VARCHAR(15),
    id_produto UUID,
    data_venda TIMESTAMP NOT NULL,
    quantidade INT NOT NULL,
    total_venda DECIMAL(10, 2),
    CONSTRAINT fk_vendas_clientes FOREIGN KEY (nif_cliente) REFERENCES clientes(nif_cliente),
    CONSTRAINT fk_vendas_produtos FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
);

-- Conexão com o banco lojabdb
\c lojabdb;

-- Criação da extensão
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação das tabelas
CREATE TABLE lojas (
    id_loja UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_loja VARCHAR(100) NOT NULL
);

CREATE TABLE clientes (
    nif_cliente VARCHAR(15) NOT NULL PRIMARY KEY,
    nome_cliente VARCHAR(100) NOT NULL,
    endereco_cliente VARCHAR(255) NOT NULL,
    telefone_cliente VARCHAR(20),
    id_loja UUID,
    CONSTRAINT fk_clientes_lojas FOREIGN KEY (id_loja) REFERENCES lojas(id_loja)
);

CREATE TABLE produtos (
    id_produto UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_produto VARCHAR(100) NOT NULL,
    descricao_produto VARCHAR(255),
    id_loja UUID,
    preco_produto DECIMAL(10, 2),
    CONSTRAINT fk_produtos_lojas FOREIGN KEY (id_loja) REFERENCES lojas(id_loja)
);

CREATE TABLE vendas (
    id_venda UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nif_cliente VARCHAR(15),
    id_produto UUID,
    data_venda TIMESTAMP NOT NULL,
    quantidade INT NOT NULL,
    total_venda DECIMAL(10, 2),
    CONSTRAINT fk_vendas_clientes FOREIGN KEY (nif_cliente) REFERENCES clientes(nif_cliente),
    CONSTRAINT fk_vendas_produtos FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
);

-- Conexão com o banco headquarters
\c headquarters;

-- Criação da extensão
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação das tabelas
CREATE TABLE lojas (
    id_loja UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_loja VARCHAR(100) NOT NULL
);

CREATE TABLE clientes (
    nif_cliente VARCHAR(15) NOT NULL PRIMARY KEY,
    nome_cliente VARCHAR(100) NOT NULL,
    endereco_cliente VARCHAR(255) NOT NULL,
    id_loja UUID,
    CONSTRAINT fk_clientes_lojas FOREIGN KEY (id_loja) REFERENCES lojas(id_loja)
);

CREATE TABLE produtos (
    id_produto UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_produto VARCHAR(100) NOT NULL,
    descricao_produto VARCHAR(255),
    id_loja UUID,
    CONSTRAINT fk_produtos_lojas FOREIGN KEY (id_loja) REFERENCES lojas(id_loja)
);

CREATE TABLE vendas (
    id_venda UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nif_cliente VARCHAR(15),
    id_produto UUID,
    data_venda TIMESTAMP NOT NULL,
    quantidade INT NOT NULL,
    total_venda DECIMAL(10, 2),
    CONSTRAINT fk_vendas_clientes FOREIGN KEY (nif_cliente) REFERENCES clientes(nif_cliente),
    CONSTRAINT fk_vendas_produtos FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
);

\c lojaadb;

-- Criação dos usuários para replicação
CREATE USER replicator WITH REPLICATION ENCRYPTED PASSWORD 'asi_lojaA';
GRANT SELECT ON ALL TABLES IN SCHEMA public TO replicator;

-- Criação da publicação para loja A
CREATE PUBLICATION lojaA_publication FOR TABLE lojas, 
    clientes(id_loja, nif_cliente, nome_cliente, endereco_cliente), -- exclui o telefone
    produtos(id_loja, id_produto, nome_produto, descricao_produto), -- exclui o preço
    vendas WITH (publish = 'insert, update, delete');

-- Conexão com a loja B e criação do usuário
\c lojabdb;
CREATE USER replicatorB WITH REPLICATION ENCRYPTED PASSWORD 'asi_lojaB';
GRANT SELECT ON ALL TABLES IN SCHEMA public TO replicatorB;

-- Criação da publicação para loja B
CREATE PUBLICATION lojaB_publication FOR TABLE lojas, 
    clientes(id_loja, nif_cliente, nome_cliente, endereco_cliente), -- exclui o telefone
    produtos(id_loja, id_produto, nome_produto, descricao_produto), -- exclui o preço
    vendas WITH (publish = 'insert, update, delete');

-- Criação da assinatura para loja A
\c headquarters;
CREATE SUBSCRIPTION sub_lojaA
    CONNECTION 'host=asi_psql port=5432 dbname=lojaadb user=replicator password=asi_lojaA'
    PUBLICATION lojaA_publication
    WITH (copy_data = true);

-- Criação da assinatura para loja B
CREATE SUBSCRIPTION sub_lojaB
    CONNECTION 'host=asi_psql port=5432 dbname=lojabdb user=replicatorB password=asi_lojaB'
    PUBLICATION lojaB_publication
    WITH (copy_data = true);
