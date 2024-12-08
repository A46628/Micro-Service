drop table if exists payment;
drop table if exists creditcard;
drop table if exists creditcardtype;
drop table if exists customer;

create table customer (
    id serial primary key not null,
    name varchar(256) not null
);

create table creditcardtype (
    creditcardtype_id int primary key not null,
    name varchar(255) not null
);

CREATE TABLE creditcard (
    id serial primary key not null,
    customer_id int not null,
    creditcardtype_id int not null,
    cardnumber varchar(19) unique not null,
    plafond numeric(6,2) default 1000,
    constraint creditcard_customer_fk FOREIGN KEY (customer_id) REFERENCES customer(id),
    constraint creditcard_creditcardtype_fk FOREIGN KEY (creditcardtype_id) REFERENCES creditcardtype(creditcardtype_id)
);

create table payment(
    id serial primary key not null,
    creditcard_id int not null,
    amount numeric(5,2) not null,
    issuer varchar(256) not null,
    transaction varchar(256) not null,
    created_at TIMESTAMP WITH TIME ZONE not null, 
    constraint payment_creditcard_fk FOREIGN KEY (creditcard_id) REFERENCES creditcard(id)
);
