USE burgerbross;

INSERT INTO tb_pedidos values (null, 17, 'Wilhelm', now(), 1);
INSERT INTO tb_detalle_pedido (id_pedido, id_producto, precio_producto, cantidad) 
values (1, 1, 6, 2), (1, 2, 5, 1);

INSERT INTO tb_pedidos values (null, 17, 'Gerardo', now(), false);
INSERT INTO tb_detalle_pedido (id_pedido, id_producto, precio_producto, cantidad) 
values (2, 1, 6, 2), (2, 2, 5, 1);

SELECT * FROM tb_pedidos;
SELECT * FROM tb_detalle_pedido;
SELECT * FROM tb_usuarios;
SELECT * FROM tb_roles;
SELECT * FROM tb_categoria_productos