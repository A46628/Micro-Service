-- 04_headquartersDB_tables.sql

\c headquartersdb;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE lojas (
    id_loja UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_loja VARCHAR(100) NOT NULL,
    url_loja VARCHAR(100) NOT NULL,
    port INT NOT NULL
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
    stockQuantity INT NOT NULL, 

    --preco_produto DECIMAL(10, 2),
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
