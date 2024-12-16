\c headquartersdb;

CREATE SUBSCRIPTION sub_lojaA
    CONNECTION 'host=asi_lojaA port=5432 dbname=lojaAdb user=replicator password=asi_lojaA'
    PUBLICATION lojaA_publication
    WITH (copy_data = true);

CREATE SUBSCRIPTION sub_lojaB
    CONNECTION 'host=asi_lojaB port=5432 dbname=lojaBdb user=replicator password=asi_lojaB'
    PUBLICATION lojaB_publication
    WITH (copy_data = true);

