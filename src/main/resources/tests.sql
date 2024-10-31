/**ej1**/
select p.nombre, p.precio from producto p;

/**ej2**/
select p.precio=p.precio*1.08 from producto as p;

/**ej3**/

/**ej4**/

/**ej5??????**/
select codigo_fabricante from producto as p;

/**ej6**/

/**ej7**/

/**ej8**/

/**ej9**/
/**offset se salta 3**/
select * from fabricante limit 2 offset 3;

/**ej10**/

/**ej11**/

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

/**ej30**/

/**ej31**/

/**ej32**/

/**ej33**/

/**ej34**/

/**ej35**/

/**ej36**/

/**ej37**/

/**ej38**/

/**ej39**/

/**j40**/

/**ej41**/

/**ej42**/

/**ej43**/

/**ej44**/

/**ej45**/

/**ej46**/