\c lojaadb;
CREATE USER replicator WITH REPLICATION ENCRYPTED PASSWORD 'asi_lojaA';
GRANT SELECT ON ALL TABLES IN SCHEMA public TO replicator;


CREATE PUBLICATION lojaA_publication FOR TABLE lojas, 
    clientes(id_loja, nif_cliente, nome_cliente, endereco_cliente), -- exclui o telefone
    produtos(id_loja, id_produto, nome_produto, descricao_produto), -- exclui o preço
    vendas WITH (publish = 'insert, update, delete');