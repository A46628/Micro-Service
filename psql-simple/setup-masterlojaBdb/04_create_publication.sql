\c lojaBdb;
CREATE USER replicator WITH REPLICATION ENCRYPTED PASSWORD 'asi_lojaB';
GRANT SELECT ON ALL TABLES IN SCHEMA public TO replicator;

CREATE PUBLICATION lojaB_publication FOR TABLE lojas, 
    clientes(id_loja, nif_cliente, nome_cliente, endereco_cliente), -- exclui o telefone
    produtos(id_loja, id_produto, nome_produto,descricao_produto, stockQuantity), -- exclui o preço
    vendas WITH (publish = 'insert, update, delete');;