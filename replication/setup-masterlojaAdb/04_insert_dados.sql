\c lojaAdb;

INSERT INTO lojas (id_loja, nome_loja, url_loja, port)
VALUES ('ccaae316-0dac-45e8-8751-cc3c9205f27b','Loja A','localhost', 8086);

INSERT INTO produtos(id_produto, nome_produto, descricao_produto, id_loja, stockQuantity, preco_produto)
VALUES
('1d520c21-9a33-4652-a01b-25bc349cb316','Melão','Produto da Loja A','ccaae316-0dac-45e8-8751-cc3c9205f27b', 50, 199.99),
('f2daf853-7fa9-488d-9803-042c1221647c','Manga','Produto da Loja A','ccaae316-0dac-45e8-8751-cc3c9205f27b', 50, 199.99);




