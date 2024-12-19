\c lojaBdb;

INSERT INTO lojas (id_loja,nome_loja, url_loja, port)
VALUES ('e4bc2f41-1bf5-4be4-ab6b-161709649ee1', 'Loja B', 'storeDB-service', 8085);

INSERT INTO produtos (id_produto, nome_produto, descricao_produto, id_loja, stockQuantity, preco_produto)
VALUES 
('afe21672-da34-4706-a487-795aa66cc3fa', 'Manga', 'Produto da Loja B', 'e4bc2f41-1bf5-4be4-ab6b-161709649ee1', 50, 199.99),
('5661ec4f-bd63-4c34-822c-afea2d4e4ee6', 'Pera', 'Produto da Loja B', 'e4bc2f41-1bf5-4be4-ab6b-161709649ee1', 50, 199.99),
('c66c7987-2c81-4577-be02-406702df9aee', 'Uva', 'Produto da Loja B', 'e4bc2f41-1bf5-4be4-ab6b-161709649ee1', 50, 199.99);
