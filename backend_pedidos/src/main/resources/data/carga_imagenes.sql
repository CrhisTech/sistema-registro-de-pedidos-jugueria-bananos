-- Asigna la URL relativa de imagen a los productos que tienen foto.
-- Las imágenes se sirven como recursos estáticos de Spring Boot: src/main/resources/static/img/productos/
-- La app móvil antepone la URL base del servidor (BASE_URL).
-- Ejecutar después de carga_carta.sql.

USE bd_bananos_app;

-- Desayunos
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Continental' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/continental.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Light' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/light.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Criollo' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/criollo.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Chosicano' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/chosicano.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Tortilla de Champiñones' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/tortilla-de-champinones.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Americano' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/americano.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Huachano' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/huachano.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Tamal' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/tamal.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Desayunos' AND ped_producto.V_NOMBRE = 'Pechuga a la Plancha' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pechuga-a-la-plancha.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Jugos Simples
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Plátano' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/platano.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Melón' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/melon.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Naranja' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/naranja.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Maracuyá' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/maracuya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Sandía' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/sandia.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Guanábana' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/guanabana.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Lúcuma' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/lucuma.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Fresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Arándanos' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/arandano.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Manzana' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/manzana.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Simples' AND ped_producto.V_NOMBRE = 'Limonada Brasileña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/limonada-brasilena.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Jugos Combinados
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Papaya + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/papaya-pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Melón + Fresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/melon-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Plátano + Fresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/platano-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Maracuyá + Plátano' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/maracuya-platano.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Fresa + Papaya' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/fresa-papaya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Fresa + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/fresa-pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Naranja + Papaya' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/naranja-papaya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Naranja + Maracuyá' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/naranja-maracuya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Papaya + Maracuyá' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/papaya-maracuya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Mango + Maracuyá' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/mango-maracuya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Guanábana + Papaya' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/guanabana-papaya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Papaya + Durazno' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/papaya-durazno.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Uva + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pina-uva.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Uva + Fresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/uva-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Lúcuma + Plátano' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/lucuma-platano.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Mango + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/mango-pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Fresa + Lúcuma' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/lucuma-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Mango + Durazno' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/mango-durazno.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Naranja + Durazno' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/naranja-durazno.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Fresa + Guanábana' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/guanabana-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Guanábana + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/guanabana-pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Guanábana + Durazno' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/guanabana-durazno.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Mango + Lúcuma' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/mango-lucuma.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Papaya + Plátano' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/papaya-platano.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Fresa + Melón' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/melon-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Papaya + Mango' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/mango-papaya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Sandía + Fresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/fresa-sandia.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Plátano + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/platano-pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Jugos Combinados' AND ped_producto.V_NOMBRE = 'Naranja + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pina-naranja.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Mix de Frutas
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Mix de Frutas' AND ped_producto.V_NOMBRE = 'Tuna + Mango + Fresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/tuna-fresa-mango.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Mix de Frutas' AND ped_producto.V_NOMBRE = 'Mango + Durazno + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/mango-durazno-pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Mix de Frutas' AND ped_producto.V_NOMBRE = 'Kiwi + Plátano + Melón' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/melon-kiwi-platano.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Mix de Frutas' AND ped_producto.V_NOMBRE = 'Arándanos + Fresa + Plátano' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/platano-arandano-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Mix de Frutas' AND ped_producto.V_NOMBRE = 'Naranja + Zanahoria + Tuna' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/naranja-zanahoria-tuna.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Mix de Frutas' AND ped_producto.V_NOMBRE = 'Piña + Naranja + Melón' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pina-naranja-melon.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Mix de Frutas' AND ped_producto.V_NOMBRE = 'Mango + Uva + Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/uva-mango-pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Mix de Frutas' AND ped_producto.V_NOMBRE = 'Surtido (Mix Frutas)' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/surtido.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Batidos
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Batidos' AND ped_producto.V_NOMBRE = 'Fresa + Leche' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/fresa-con-leche.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Batidos' AND ped_producto.V_NOMBRE = 'Mango + Leche' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/mango-con-leche.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Batidos' AND ped_producto.V_NOMBRE = 'Plátano + Leche' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/platano-con-leche.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Batidos' AND ped_producto.V_NOMBRE = 'Arándanos + Fresa + Mango + Leche' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/arandano-fresa-mango-con-leche.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Batidos' AND ped_producto.V_NOMBRE = 'Súper Especial' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/super-especial.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Frozen
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen' AND ped_producto.V_NOMBRE = 'Fresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen' AND ped_producto.V_NOMBRE = 'Piña' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-pina.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen' AND ped_producto.V_NOMBRE = 'Limonada' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-limonada.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen' AND ped_producto.V_NOMBRE = 'Maracuyá' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-maracuya.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen' AND ped_producto.V_NOMBRE = 'Mango' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-mango.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Frozen Mix
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen Mix' AND ped_producto.V_NOMBRE = 'Maracumango' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-mix-maracumango.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen Mix' AND ped_producto.V_NOMBRE = 'Arándanos + Mango' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-mix-arandano-mango.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen Mix' AND ped_producto.V_NOMBRE = 'Maracufresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-mix-maracufresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Frozen Mix' AND ped_producto.V_NOMBRE = 'Piña + Fresa' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/frozen-mix-pina-fresa.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Ensalada de Frutas
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Ensalada de Frutas' AND ped_producto.V_NOMBRE = 'Hawaiana' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/ensalada-frutas-hawaiana.jpg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Ensalada de Frutas' AND ped_producto.V_NOMBRE = 'Tropical' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/tropical.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Ensalada de Frutas' AND ped_producto.V_NOMBRE = 'Torbellino' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/torbellino.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Ensalada de Frutas' AND ped_producto.V_NOMBRE = 'Donofruits' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/donofruits.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Yogurt
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Yogurt' AND ped_producto.V_NOMBRE = 'Copa Tropical' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/copa-tropical.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Yogurt' AND ped_producto.V_NOMBRE = 'Copa Mega' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/copa-mega.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Yogurt' AND ped_producto.V_NOMBRE = 'Copa Ultra Mix' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/copa-ultramix.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Yogurt' AND ped_producto.V_NOMBRE = 'Ultra Bananos' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/ultra-bananos.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Energéticos
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Energéticos' AND ped_producto.V_NOMBRE = 'Maca Power' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/maca-power.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Energéticos' AND ped_producto.V_NOMBRE = 'Super Power' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/super-power.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Energéticos' AND ped_producto.V_NOMBRE = 'Maca Arándanos' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/maca-arandanos.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Energéticos' AND ped_producto.V_NOMBRE = 'Fresa + Almendra + Leche' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/fresa-almendra-leche.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Energéticos' AND ped_producto.V_NOMBRE = 'Plátano + Almendra + Leche' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/platano-almendra-leche.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Energéticos' AND ped_producto.V_NOMBRE = 'Mango + Almendra + Leche' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/mango-almendra-leche.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Energéticos' AND ped_producto.V_NOMBRE = 'Appifruit' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/appifruit.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Extractos Naturales
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Extractos Naturales' AND ped_producto.V_NOMBRE = 'Extracto de Naranja' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/extracto-de-naranja.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Extractos Naturales' AND ped_producto.V_NOMBRE = 'Extracto Verde' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/extracto-verde.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Extractos Naturales' AND ped_producto.V_NOMBRE = 'Extracto Rojo' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/extracto-rojo.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;

-- Sangwiches
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Sangwiches' AND ped_producto.V_NOMBRE = 'Pan c/ Pollo Deshilachado' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pan-con-pollo-deshilachado.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Sangwiches' AND ped_producto.V_NOMBRE = 'Pan c/ Filete de Pollo' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pan-con-filete-de-pollo.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Sangwiches' AND ped_producto.V_NOMBRE = 'Pan c/ Aceituna' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pan-con-aceituna.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
SET @I_ID_PRODUCTO = (SELECT ped_producto.I_ID_PRODUCTO FROM ped_producto INNER JOIN ped_categoria ON ped_categoria.I_ID_CATEGORIA = ped_producto.I_ID_CATEGORIA WHERE ped_categoria.V_NOMBRE = 'Sangwiches' AND ped_producto.V_NOMBRE = 'Pan c/ Queso' LIMIT 1);
UPDATE ped_producto SET ped_producto.V_URL_IMAGEN = '/img/productos/pan-con-queso.jpeg' WHERE ped_producto.I_ID_PRODUCTO = @I_ID_PRODUCTO;
