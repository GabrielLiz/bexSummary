DROP TABLE btca IF EXISTS;

create table btca (
id BIGINT IDENTITY NOT NULL PRIMARY KEY,
id_trans VARCHAR(60),
version VARCHAR(50),
status VARCHAR(20),
isin VARCHAR(30),
sent VARCHAR (10),
trade_type VARCHAR(30),
fecha_operativa VARCHAR(10),
assset_class VARCHAR (30)
);

DROP TABLE rfq IF EXISTS;

create table rfq (
id BIGINT IDENTITY NOT NULL PRIMARY KEY,
version VARCHAR(100),
status VARCHAR(20),
fecha_operativa VARCHAR(10));

DROP TABLE trade IF EXISTS;

create table trade (
id BIGINT IDENTITY NOT NULL PRIMARY KEY,
busqueda VARCHAR(100),
valores INTEGER
);
