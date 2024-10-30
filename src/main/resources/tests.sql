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
