//docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <nome_do_container>
// Comando para ip do container

// sudo chmod 664 /caminho/para/pg_hba.conf
// dar acesso para escrever num determinado ficheiro 
// sudo chmod 775 data_lojaA data_lojaB data_headquarters
// sudo chmod 644 /home/ASI/asi/psql-simple/data_lojaA/postgresql.conf
//sudo chown paulo:paulo /home/ASI/asi/psql-simple/data_lojaA/postgresql.conf
/*
CREATE SUBSCRIPTION sub_lojaA
    CONNECTION 'host=asi_lojaA port=5433 dbname=lojaAdb user=postgres password=asi_lojaA' 
    PUBLICATION pub_lojaA
    WITH (copy_data = true);
*/

//host    all             replicator      172.18.0.0/24       md5
//CREATE USER replicator WITH REPLICATION ENCRYPTED PASSWORD 'asi_lojaA';
//Criação de um novo usuaurio
//Comando importante para dar permissão
// Comandos imoportantes
/*DROP SUBSCRIPTION IF EXISTS sub_lojaA;

CREATE SUBSCRIPTION sub_lojaA
    CONNECTION 'host=asi_lojaA port=5432 dbname=lojaAdb user=replicator password=asi_lojaA'
    PUBLICATION pub_lojaa
    WITH (copy_data = true);

    */
//GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE vendas TO replicator;
//Quando der o erro das permissões

/*

 Verifique o Status da Assinatura
Se a replicação ainda não funcionar, verifique o status da assinatura em headquarters:

sql
Copiar código
SELECT * FROM pg_stat_subscription;

*/


/*
DROP SUBSCRIPTION IF EXISTS sub_lojaA;

*/


/*


CREATE PUBLICATION pub_lojaA FOR TABLE vendas;


*/

/*
    inserir dados para teste
    INSERT INTO vendas (produto, quantidade, preco, data_venda) 
    VALUES ('Produto Teste', 3, 20.00, NOW());


*/


/*
-- Criação da publicação
CREATE PUBLICATION loja_publicationB FOR TABLE lojas, 
    clientes(id_loja, nif_cliente, nome_cliente, endereco_cliente), -- exclui o telefone
    produtos(id_loja, id_produto, nome_produto, descricao_produto), -- exclui o preço
    vendas;

-- Verificar se as colunas desejadas estão corretas
SELECT * FROM pg_publication_tables;


*/

/*CREATE SUBSCRIPTION sub_lojaA
    CONNECTION 'host=asi_lojaA port=5432 dbname=lojaAdb user=replicator password=asi_lojaA'
    PUBLICATION loja_publication
    WITH (copy_data = true);

*/


/*INSERT INTO lojas (id_loja, nome_loja) 
VALUES (50,'Loja A');

*/


/*

INSERT INTO lojas (id_loja, nome_loja) VALUES
(1, 'Loja B');

INSERT INTO clientes (nif_cliente, nome_cliente, endereco_cliente, id_loja) VALUES
('123456789', 'Cliente A1', 'Rua A1, 123', 1),
('234567890', 'Cliente A2', 'Rua A2, 456', 1),
('345678901', 'Cliente A3', 'Rua A3, 789', 1),
('456789012', 'Cliente A4', 'Rua A4, 321', 1),
('567890123', 'Cliente A5', 'Rua A5, 654', 1);


*/

/*


INSERT INTO lojas (id_loja, nome_loja) VALUES
(1, 'Loja A');

INSERT INTO clientes (nif_cliente, nome_cliente, endereco_cliente, telefone_cliente, id_loja) VALUES
('123456789', 'Cliente A1', 'Rua A1, 123', '912345678', 1),
('234567890', 'Cliente A2', 'Rua A2, 456', '923456789', 1),
('345678901', 'Cliente A3', 'Rua A3, 789', '934567890', 1),
('456789012', 'Cliente A4', 'Rua A4, 321', '945678901', 1),
('567890123', 'Cliente A5', 'Rua A5, 654', '956789012', 1);

INSERT INTO produtos (id_produto, nome_produto, descricao_produto, id_loja, preco_produto) VALUES
(1, 'Produto A1', 'Descrição do Produto A1', 1, 10.50),
(2, 'Produto A2', 'Descrição do Produto A2', 1, 15.75),
(3, 'Produto A3', 'Descrição do Produto A3', 1, 8.99),
(4, 'Produto A4', 'Descrição do Produto A4', 1, 12.00),
(5, 'Produto A5', 'Descrição do Produto A5', 1, 20.00);


*/
/**
 * ver se e necessario configurar essas notificações 
 * CREATE PUBLICATION pub_lojaA FOR TABLE vendas WITH (publish = 'insert, update, delete');
 * Ver na aula com o professor o que pode passar;
 * CREATE PUBLICATION pub_lojaA FOR TABLE vendas WITH (publish = 'insert, update, delete');
 * DROP PUBLICATION IF EXISTS pub_lojaa;
 */

/*

INSERT INTO lojas (id_loja, nome_loja) VALUES
(2, 'Loja B');

INSERT INTO clientes (nif_cliente, nome_cliente, endereco_cliente, telefone_cliente, id_loja) VALUES
('678901234', 'Cliente B1', 'Rua B1, 123', '967890123', 2),
('789012345', 'Cliente B2', 'Rua B2, 456', '978901234', 2),
('890123456', 'Cliente B3', 'Rua B3, 789', '989012345', 2),
('901234567', 'Cliente B4', 'Rua B4, 321', '990123456', 2),
('012345678', 'Cliente B5', 'Rua B5, 654', '991234567', 2);

INSERT INTO produtos (id_produto, nome_produto, descricao_produto, id_loja, preco_produto) VALUES
(6, 'Produto B1', 'Descrição do Produto B1', 2, 11.50),
(7, 'Produto B2', 'Descrição do Produto B2', 2, 16.75),
(8, 'Produto B3', 'Descrição do Produto B3', 2, 9.99),
(9, 'Produto B4', 'Descrição do Produto B4', 2, 13.00),
(10, 'Produto B5', 'Descrição do Produto B5', 2, 22.00);

*/

/*
INSERT INTO vendas (id_venda, nif_cliente, id_produto, data_venda, quantidade, total_venda) VALUES
(1, '123456789', 1, '2024-10-01 10:00:00', 2, 21.00), -- Cliente A1 compra 2 Produto A1
(2, '234567890', 2, '2024-10-01 11:00:00', 1, 15.75), -- Cliente A2 compra 1 Produto A2
(3, '345678901', 3, '2024-10-02 12:00:00', 3, 26.97), -- Cliente A3 compra 3 Produto A3
(4, '456789012', 4, '2024-10-02 13:00:00', 1, 12.00), -- Cliente A4 compra 1 Produto A4
(5, '567890123', 5, '2024-10-03 14:00:00', 1, 20.00); -- Cliente A5 compra 1 Produto A5
*/

/*
INSERT INTO vendas (id_venda, nif_cliente, id_produto, data_venda, quantidade, total_venda) VALUES
(6, '678901234', 6, '2024-10-01 15:00:00', 2, 23.00), -- Cliente B1 compra 2 Produto B1
(7, '789012345', 7, '2024-10-01 16:00:00', 1, 16.75), -- Cliente B2 compra 1 Produto B2
(8, '890123456', 8, '2024-10-02 17:00:00', 3, 29.97), -- Cliente B3 compra 3 Produto B3
(9, '901234567', 9, '2024-10-02 18:00:00', 1, 13.00), -- Cliente B4 compra 1 Produto B4
(10, '012345678', 10, '2024-10-03 19:00:00', 1, 22.00); -- Cliente B5 compra 1 Produto B5
*/


/*
    Criar Script automaticamente de inicio ao fim 

*/


DROP SUBSCRIPTION sub_lojaB;

//o que tenho que fazer agora para garantir a eliminação dos dados 

//Subscription nas lojas do lado do headquarters
/*
     CREATE SUBSCRIPTION sub_lojaB
     CONNECTION 'host=asi_lojaB port=5432 dbname=lojaB user=replicator password=asi_lojaB'
     PUBLICATION loja_publicationB
     WITH (copy_data = true);


    NOTICE:  created replication slot "sub_lojab" on publisher
    CREATE SUBSCRIPTION

    CREATE SUBSCRIPTION sub_lojaA
    CONNECTION 'host=asi_lojaA port=5432 dbname=lojaAdb user=replicator password=asi_lojaA'
    PUBLICATION loja_publication
    WITH (copy_data = true);
    WARNING:  publication "loja_publicationa" does not exist on the publisher
    NOTICE:  created replication slot "sub_lojaa" on publisher
*/

/*
CREATE PUBLICATION loja_publication FOR TABLE lojas,
     clientes(id_loja, nif_cliente, nome_cliente, endereco_cliente), -- exclui o telefone
     produtos(id_loja, id_produto, nome_produto, descricao_produto), -- exclui o preço
    vendas WITH (publish = 'insert, update, delete');

*/

/*
    -- Usar a base de dados
DO $$ 
BEGIN
   -- Verificar se a base de dados já existe, caso contrário, criá-la
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'lojaAdb') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE lojaAdb');
   END IF;
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'lojaBdb') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE lojaBdb');
   END IF;
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'headquartersDB') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE headquartersDB');
   END IF;
END
$$;
*/