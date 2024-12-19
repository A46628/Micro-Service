1) Em cada módulo Maven:

		Order
->Execute os comandos na seguinte ordem:
	mvn package
	mvn install

No terminal dentro do módulo Order, execute:
	docker build -t imageorder .

		Payment

->Execute os comandos na seguinte ordem:
	mvn package
	mvn install

->No terminal dentro do módulo Payment, execute:
	docker build -t imagepayment .


	StockGlobal

->Execute os comandos na seguinte ordem:
	mvn package
	mvn install

->No terminal dentro do módulo StockGlobal, execute:
	docker build -t imagestockglobal .

	StoreDA

->Execute os comandos na seguinte ordem:
	mvn package
	mvn install

->No terminal dentro do módulo StoreDA, execute:
	docker build -t imagestorea .

	StoreDB

->Execute os comandos na seguinte ordem:
	mvn package
	mvn install

->No terminal dentro do módulo StoreDB, execute:
	docker build -t imagestoreb .

2) Criar todos os containers
->Após criar todas as imagens Docker, será necessário criar os containers:

->No terminal, localize o arquivo docker-compose.yml do trabalho.
Execute o seguinte comando:
	docker-compose up -d


3) Confirmar o estado dos containers
->Certifique-se de que todos os containers estão em execução (RUN), usando:
	docker ps


####################################################################################################################
Teste no Postman: Replication
Na pasta "Stock with Replication"
->Obter todas as lojas (Get all Stores).
->Obter todos os produtos (Get all Products) e confirmar que existem produtos replicados das lojas A e B.

Na pasta "LojaA" e "LojaB"
->Criar um novo produto:
->Após a criação, confirmar na pasta Stock with Replication se o produto foi replicado.
->Apagar um produto por ID:
->Confirmar na pasta Stock with Replication se o produto foi removido.
->Obter todos os produtos de uma loja específica (A ou B) na pasta correspondente (LojaA ou LojaB).

############################################################################################################################
Teste de Microserviços, Replicação e Saga
Fazer um pedido na pasta order_Service:

Post order 1:
->Todos os produtos existem e o pagamento mais ocorreu um erro de pagamento sem credito suficiente .

Post order 2: 
->Todos os produtos disponível e credito suficiente.

Post Order 3: 
-> Produto não disponível em nenhuma das lojas;

Post Order 4: Produto existente na loja A e na Loja B, mais a loja A encontra se indisponível mais a reserva tem que ser feita na loja B, sem nenhum erro do sistema


get all Saga -> De modo a ver todas as sagas e o estado final. 

get all Saga pending -> Para ver sagas pendentes e necessário fazer um pedido por exemplo o Post Order 1, e desligar o stock service, como vai dar um erro na payment ao tentar fazer a compensação da erro de contevidade com o stock e a saga e terminada mais e guardada numa outra tabela de saga pending,  ao fazer o get all pending deve ver a informação da saga, o serviço ao volta acima a saga sera executada e eliminada da saga pending 


