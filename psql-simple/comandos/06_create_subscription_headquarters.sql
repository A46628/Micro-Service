CREATE SUBSCRIPTION sub_lojaA
    CONNECTION 'host=127.0.0.1 port=5432 dbname=lojaadb user=replicator password=asi_lojaA'
    PUBLICATION lojaA_publication
    WITH (copy_data = true);

CREATE SUBSCRIPTION sub_lojaA
    CONNECTION 'host=127.0.0.1 port=5432 dbname=lojabdb user=replicatorB password=asi_lojaB'
    PUBLICATION lojaA_publication
    WITH (copy_data = true);
