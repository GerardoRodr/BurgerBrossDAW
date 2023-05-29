DROP DATABASE burgerbross;

CREATE DATABASE BurgerBross;

USE BurgerBross;

CREATE TABLE tb_categoria_productos(
	id_categoria_prod int not null primary key auto_increment,
    nombre_categoria varchar(256)
    );

CREATE TABLE tb_productos(
	id_producto int not null primary key auto_increment,
    nombre_producto varchar(256),
    id_categoria_prod int not null,
    desc_prod varchar(500),
    stock int,
    precio_producto decimal(7, 2),
    FOREIGN KEY (id_categoria_prod) REFERENCES tb_categoria_productos(id_categoria_prod)
);

CREATE TABLE tb_pedidos(
	id_pedido int not null primary key auto_increment,
    total_pedido decimal(7, 2),
    nombre_cliente varchar(256),
    fecha_pedido datetime,
    estado_pedido boolean
);

CREATE TABLE tb_detalle_pedido(
	id_detalle_pedido int not null auto_increment,
	id_pedido int not null,
    id_producto int, -- 1
    precio_producto decimal(7, 2), -- 23.50
    cantidad int, -- 1
    subtotal decimal(7, 2) GENERATED ALWAYS AS (precio_producto * cantidad) VIRTUAL,
    primary key (id_detalle_pedido),
    foreign key (id_pedido) REFERENCES tb_pedidos(id_pedido),
    foreign key (id_producto) REFERENCES tb_productos(id_producto)
);

CREATE TABLE tb_roles(
	id_rol int not null primary key auto_increment,
    nombre_rol varchar(150)
    );

CREATE TABLE tb_usuarios(
	id_usuario int not null primary key auto_increment,
	usuario varchar(150),
    password varchar(150),
    id_rol int,
    foreign key (id_rol) references tb_roles(id_rol)
    );

INSERT INTO tb_categoria_productos values 
(null, 'Hamburguesas'), (null, 'Bebidas'), (null, 'Complementos'), (null, 'Adicionales');

INSERT INTO tb_productos values
		(null, 'Hamburguesa Royal', 1, 'Hamburguesa con huevo', 50, 6),
        (null, 'Hamburguesa Simple', 1, 'Hamburguesa simple', 50, 5),
        (null, 'Hamburguesa Cheese', 1, 'Hamburguesa con queso', 50, 6.50);

INSERT INTO tb_roles values (null, 'Cocina'), (null, 'Mesero');

INSERT INTO tb_usuarios values (null, 'admin', '112233', 1);