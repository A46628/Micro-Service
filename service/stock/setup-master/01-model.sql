drop table if exists stock;


CREATE TABLE stock (
    id INT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unitPrice FLOAT NOT NULL
);
