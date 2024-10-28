/**ej1**/
select p.nombre, p.precio from producto p;

/**ej2**/
select p.precio=p.precio*1.08 from producto as p;

/**ej9**/
/**offset se salta 3**/
select * from fabricante limit 2 offset 3;

/**ej12**/
select nombre from producto p where p.codigo_fabricante=2;

/**ej13**/ select nombre from producto p where p.precio>=120;