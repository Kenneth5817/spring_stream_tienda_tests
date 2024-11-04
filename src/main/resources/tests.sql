/**ej1**/
select p.nombre, p.precio from producto p;

/**ej2**/
select p.precio=p.precio*1.08 from producto as p;

/**ej3**/
select upper(nombre) AS nombre_producto, precio from producto;

/**ej4**/
select nombre, UPPER(LEFT(nombre, 2)) as 2primerasMayus FROM fabricante;

/**ej5**/
select f.codigo from fabricante f
    join producto p on f.codigo = p.fabricante_codigo group by f.codigo;

/**ej6**/
select nombre from fabricante order nombre desc;

/**ej7**/
select nombre from producto order by nombre asc, precio desc;

/**ej8**/
select * from fabricante limit 5;

/**ej9**/
/**offset se salta 3**/
select * from fabricante limit 2 offset 3;

/**ej10**/
select nombre, precio from producto order by precio asc limit 1;
/**ej11**/
select nombre, precio from producto order by precio desc limit 1;

/**ej12**/
select nombre from producto p where p.codigo_fabricante=2;

/**ej13**/
select nombre from producto p where p.precio>=120;

/**ej14**/
select * from producto p where p.precio>=400;

/**ej15**/
select * from producto p where p.precio between 80 and 300;

/**ej16**/
select * from producto p where p.precio>200 and p.codigo_fabricante=6;

/**ej17**/
select * from producto p where p.codigo_fabricante=1 or p.codigo_fabricante=3 or p.codigo_fabricante=5;

/**ej18**/
select p.nombre, p.precio*100 from producto as p;

/**ej19**/
select nombre from fabricante where nombre like 's%';

/**ej20**/
select * from producto where nombre like '%Port_til%';

/**ej21**/
select * from producto as p where p.nombre like '%Monitor%' and precio < 215;

/**ej22**/
select nombre, precio from producto as p where p.precio>=180 order by precio desc, nombre ASC;

/**ej23**/
select * from fabricante as f where nombre order by nombre asc;

/**ej24**/
select * from producto order by nombre desc limit 1;

/**ej25**/
select p.nombre from producto p join fabricante f on p.codigo_fabricante=f.codigo
                          where p.precio>200 and f.nombre like 'Crucial';

/**ej26**/
select p.* from producto p join fabricante f on p.codigo_fabricante=f.codigo
           where f.nombre in ('Asus', 'Hewlett-Packard', 'Seagate');
/**ej27**/
select   p.nombre, p.precio from producto p join fabricante f on p.codigo_fabricante=f.codigo
    where p.precio >= 180 order by p.precio desc, p.nombre asc;

/**ej28**/
select f.nombre, p.nombre from fabricante f
    join producto p on f.codigo =p.codigo_fabricante;

/**ej29**/
select f.* from fabricante f left join producto p on f.codigo = p.codigo_fabricante
    where p.codigo_fabricante is null;

/**ej30**/
select count(*) as total_productos from producto;

/**ej31**/
select count(distinct codigo_fabricante) as num_fabricante from producto;

/**ej32**/
select avg(precio) from producto;

/**ej33**/
select min(precio) AS min_precio from producto;

/**ej34**/
select sum(precio) from producto;

/**ej35**/
select count(*) as num_productos from producto
     where codigo_fabricante = (select codigo from fabricante where nombre = 'Asus');

/**ej36**/
select avg(precio) as media_precio from producto
    where codigo_fabricante = (select codigo from fabricante where nombre = 'Asus');

/**ej37**/
select max(precio) AS precio_maximo, min(precio) AS precio_minimo, avg(precio) AS precio_medio,
    count(*) AS total_productos from producto
    where codigo_fabricante = (select codigo from fabricante where nombre = 'Crucial');

/**ej38**/
select f.nombre as Fabricante, count(p.codigo) as num_productos from fabricante f
    left join producto p on f.codigo = p.codigo_fabricante group by f.nombre
    order by num_productos desc;

/**ej39**/
select f.nombre as Fabricante, max(p.precio) as Precio_Maximo, min(p.precio) as Precio_Minimo, avg(p.precio) as Precio_Medio
    from fabricante f left join producto p on f.codigo = p.codigo_fabricante
    group by f.nombre;

/**j40**/
select f.codigo as Fabricante_Codigo, max(p.precio) as Precio_Maximo, min(p.precio) as Precio_Minimo,
    avg(p.precio) as Precio_Medio, count(p.codigo) as Total_Productos
    from fabricante f join producto p on f.codigo = p.codigo_fabricante
    group by f.codigo having avg(p.precio) > 200;

/**ej41**/
select f.nombre as Fabricante from fabricante f
    join producto p on f.codigo = p.codigo_fabricante
    group by f.nombre
    having count(p.codigo) >= 2;

/**ej42**/
select f.nombre as Fabricante, count(p.codigo) as Numero_Productos from fabricante f
    join producto p on f.codigo = p.codigo_fabricante where p.precio >= 220
    group by f.nombre
    order by Numero_Productos desc;
/**ej43**/
select f.nombre as Fabricante from fabricante f join producto p on f.codigo = p.codigo_fabricante
    group by f.nombre having sum(p.precio) > 1000;

/**ej44**/
select f.nombre as Fabricante, SUM(p.precio) as Suma_Precio from fabricante f
    join producto p on f.codigo = p.codigo_fabricante
    group by f.nombre having sum(p.precio) > 1000
    order by Suma_Precio asc;

/**ej45**/
select p.nombre as Nombre_Producto, p.precio as Precio, f.nombre as Fabricante from producto p
    join fabricante f ON p.codigo_fabricante = f.codigo where p.precio = (
    select max(p2.precio) from producto p2 where p2.codigo_fabricante = f.codigo)
order by f.nombre asc;


/**ej46**/
select p.nombre as Nombre_Producto, p.precio as Precio, f.nombre as Fabricante
    from producto p join fabricante f on p.codigo_fabricante = f.codigo where p.precio >= (
    select avg(p2.precio) from producto p2 where p2.codigo_fabricante = p.codigo_fabricante)
    order by f.nombre asc, p.precio desc;
