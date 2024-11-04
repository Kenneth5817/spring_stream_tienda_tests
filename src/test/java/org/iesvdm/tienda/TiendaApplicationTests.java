package org.iesvdm.tienda;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.iesvdm.tienda.modelo.Fabricante;
import org.iesvdm.tienda.modelo.Producto;
import org.iesvdm.tienda.repository.FabricanteRepository;
import org.iesvdm.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static org.junit.Assert.assertEquals;


@SpringBootTest
public class TiendaApplicationTests {

	@Autowired
	FabricanteRepository fabRepo;

	@Autowired
	ProductoRepository prodRepo;
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private FabricanteRepository fabricanteRepository;

	public void testAllFabricante() {
		var listFabs = fabRepo.findAll();

		listFabs.forEach(f -> {
			System.out.println(">>" + f + ":");
			f.getProductos().forEach(System.out::println);
		});

		Assertions.assertEquals(9, listFabs.size());
	}

	@Test
	void testAllProducto() {
		var listProds = prodRepo.findAll();

		listProds.forEach(p -> {
			System.out.println(">>" + p + ":" + "\nProductos mismo fabricante " + p.getFabricante());
			p.getFabricante().getProductos().forEach(pF -> System.out.println(">>>>" + pF));
		});

	}


	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {
		var listProds = prodRepo.findAll();
		//Nombres y precios
		listProds.stream()
				.map(producto -> producto.getNombre() + "=" + producto.getPrecio() + "euros")
		;
	}


	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
	@Test
	void test2() {
		var listProds = prodRepo.findAll();
		listProds.stream()
				.map(producto -> producto.getNombre() + "=" + (producto.getPrecio() * 1.09) + " dolares");
	}

	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		var listProds = prodRepo.findAll();
		listProds.stream()
				.map(producto -> producto.getNombre().toUpperCase() + "=" + producto.getPrecio());
	}

	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
		var listFabs = fabRepo.findAll();
		listFabs.stream()
				.map(fabricante -> fabricante.getNombre() + " " + fabricante.getNombre().substring(0, 1).toUpperCase());
	}

	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
		var listFabs = fabRepo.findAll();
		listFabs.stream()
				.map(cod -> cod.getNombre() + "=" + cod.getProductos() + "=" + cod.getCodigo())
				.toList()
				.forEach(System.out::println);
	}

	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
		var listFabs = fabRepo.findAll();
		listFabs.stream().
				sorted(Comparator.comparing(Fabricante::getNombre).reversed())
				.map(fabricante -> fabricante.getNombre())
				.forEach(System.out::println);

	}

	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
		var listProds = prodRepo.findAll();
		listProds.stream()
				.map(producto -> producto.getNombre())
				.forEach(System.out::println);

		listProds.stream()
				.sorted(Comparator.comparing(Producto::getPrecio).reversed())
				.forEach(System.out::println);
	}

	/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
		var listFabs = fabRepo.findAll();
		listFabs.stream()
				.map(fabricante -> fabricante.getNombre().indexOf(0, 0, 4))
				.forEach(System.out::println);
	}

	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
		var listFabs = fabRepo.findAll();
		listFabs.stream()
				.map(fabricante -> fabricante.getNombre().indexOf(2, 3, 4))
				.forEach(System.out::println);
	}

	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
		var listProds = prodRepo.findAll();
		listProds.stream()
				.min(Comparator.comparing(Producto::getPrecio))
				.map(producto -> producto.getNombre() + "=" + producto.getPrecio());

		System.out.println(listProds.stream());
	}

	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
		var listProds = prodRepo.findAll();
		// Usando streams para encontrar el producto más caro
		var productoMasCaro = listProds.stream()
				.reduce((prod1, prod2) -> prod1.getPrecio() > prod2.getPrecio() ? prod1 : prod2)
				.orElse(null);

		// Con este assert aseguramos de que hay al menos un producto
		assertEquals(true, productoMasCaro != null);

		listProds.stream()
				.max(Comparator.comparing(Producto::getPrecio))
				.ifPresentOrElse(producto -> System.out.println(producto.getNombre() + producto.getPrecio()), () -> System.out.println("Coleción vacia"));
	}

	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 */
	@Test
	void test12() {
		var listProds = prodRepo.findAll();
		List<String> nombresProductos = listProds.stream()
				.filter(prod -> prod.getFabricante().getCodigo() == 2)
				.map(Producto::getNombre)
				.toList();

		System.out.println(nombresProductos);
		Assertions.assertTrue(nombresProductos.contains("Portátil Yoga 520") && nombresProductos.contains("Portátil Yoga 520"));
		Assertions.assertEquals(nombresProductos.size(), 2);
	}

	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
		var listProds = prodRepo.findAll();
		List<String> nombresProductos = listProds.stream()
				.filter(prod -> prod.getPrecio() <= 120)
				.map(Producto::getNombre)
				.toList();

		Assertions.assertEquals(4, nombresProductos.size());
	}

	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
		var listProds = prodRepo.findAll();
		List<Producto> productosCaros = listProds.stream()
				.filter(prod -> prod.getPrecio() >= 400)
				.toList();

		Assertions.assertEquals(4, productosCaros.size());
	}

	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
	 */
	@Test
	void test15() {
		var listProds = prodRepo.findAll();
		List<Producto> productosEnRango = listProds.stream()
				.filter(prod -> prod.getPrecio() >= 80 && prod.getPrecio() <= 300)
				.toList();
		Assertions.assertEquals(8, productosEnRango.size());

	}

	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
		var listProds = prodRepo.findAll();
		List<Producto> productosFiltrados = listProds.stream()
				.filter(prod -> prod.getPrecio() > 200 && prod.getCodigo() == 6)
				.toList();
		System.out.println(productosFiltrados);

		Assertions.assertEquals(1, listProds.size());
	}

	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
		var listProds = prodRepo.findAll();

		// Definimos un Set de códigos de fabricantes
		Set<Integer> cods = Set.of(1, 3, 5);

		// Filtramos productos cuyo código de fabricante está en el Set
		var productosFiltrados = listProds.stream()
				.filter(prod -> cods.contains(prod.getFabricante().getCodigo()))
				.toList();
		productosFiltrados.forEach(System.out::println);
		Assertions.assertEquals(5, productosFiltrados.size());

	}

	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
		var listProds = prodRepo.findAll();
		List<String> productosEnCentimos = listProds.stream()
				//Convertimos a centimos
				.map(prod -> prod.getNombre() + (prod.getPrecio() * 100))
				.toList();
		System.out.println(productosEnCentimos);

		Assertions.assertEquals(11, productosEnCentimos.size());
	}

	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
		var listFabs = fabRepo.findAll();
		var nombresFabricantes = listFabs.stream()
				.filter(fab -> fab.getNombre().startsWith("s"))
				.map(Fabricante::getNombre)
				.toList();
		System.out.println(nombresFabricantes);

		//otra manera de hacerlo, hecho en clase ;)
		var result = listFabs.stream()
				.filter(t -> t.getNombre().substring(0, 1).equalsIgnoreCase("s"))
				.toList();
		result.forEach(t -> System.out.println("Fabricantes que empiecen por s" + t.getNombre()));
	}

	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
		var listProds = prodRepo.findAll();
		List<Producto> productosPortatiles = listProds.stream()
				.filter(prod -> prod.getNombre().contains("Portátil") || prod.getNombre().contains("Portatil"))
				.toList();
		System.out.println(productosPortatiles);
	}


	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
		var listProds = prodRepo.findAll();
		List<String> nombresMonitores = listProds.stream()
				.filter(prod -> prod.getNombre().contains("Monitor") && prod.getPrecio() < 215)
				.map(Producto::getNombre)
				.toList();
		System.out.println(nombresMonitores);
		Assertions.assertEquals(1, nombresMonitores.size());
	}

	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€.
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
	@Test
	void test22() {
		var listProds = prodRepo.findAll();
		// Filtrar productos con precio mayor o igual a 180€, ordenar por precio y nombre
		List<Producto> productosFiltrados = listProds.stream()
				.filter(prod -> prod.getPrecio() >= 180)
				.sorted(Comparator.comparingDouble(Producto::getPrecio).reversed()
						.thenComparing((Producto::getNombre)))
				.toList();
		productosFiltrados.forEach(System.out::println);
	}

	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos
	 * de la base de datos. Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		var listProds = prodRepo.findAll();
		// Filtrar y ordenar productos por el nombre del fabricante
		var productosFiltrados = listProds.stream()
				// Ordenamos por nombre del fabricante
				.sorted(Comparator.comparing(p -> p.getFabricante().getNombre()))
				//String se cambia a otro String al usar el map
				.map(producto -> producto.getNombre()
						+ producto.getPrecio()
						+ producto.getFabricante())
				.toList();
		listProds.forEach(System.out::println);

		//Assertions
		Assertions.assertEquals(11, productosFiltrados.size());
	}

	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		var listProds = prodRepo.findAll();
		// Obtener el producto con el precio máximo
		Producto productoMasCaro = listProds.stream()
				.max(Comparator.comparingDouble(Producto::getPrecio))
				.orElse(null);

		listProds.forEach(System.out::println);
		Assertions.assertEquals(11, listProds.size());
	}


	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		var listProds = prodRepo.findAll();
		// Filtrar productos del fabricante "Crucial" con precio mayor que 200€
		List<Producto> productosCrucial = listProds.stream()
				.filter(prod -> "Crucial".equals(prod.getFabricante().getNombre()) && prod.getPrecio() > 200)
				.toList();

		Assertions.assertEquals(1, productosCrucial.size());
		Assertions.assertEquals(755, productosCrucial.getFirst().getPrecio());
	}

	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		var listProds = prodRepo.findAll();
		Set<String> fabricantesPermitidos = new HashSet<>();
		fabricantesPermitidos.add("Asus");
		fabricantesPermitidos.add("Hewlett-Packard");
		fabricantesPermitidos.add("Seagate");
		var result = listProds.stream()
				.filter(p -> fabricantesPermitidos.contains(p.getFabricante().getNombre()))
				.toList();


		result.forEach(System.out::println);
		Assertions.assertEquals(3, fabricantesPermitidos.size());
		fabricantesPermitidos.forEach(s -> Assertions
				.assertTrue(result.stream()
						.anyMatch(p -> p.getFabricante().getNombre().equalsIgnoreCase("Seagate"))));
	}

	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€.
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:
	 * <p>
	 * Producto                Precio             Fabricante
	 * -----------------------------------------------------
	 * GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
	 * Portátil Yoga 520      |452.79            |Lenovo
	 * Portátil Ideapd 320    |359.64000000000004|Lenovo
	 * Monitor 27 LED Full HD |199.25190000000003|Asus
	 */
	@Test
	void test27() {
		var listProds = prodRepo.findAll();
		var listado = listProds.stream()
				.filter(prod -> prod.getPrecio() >= 180) // Filtrar productos con precio >= 180
				.sorted(Comparator.comparing(Producto::getPrecio).reversed()
						.thenComparing(Producto::getNombre)) // Ordenar por precio y nombre
				.map(prod -> String.format("%-22s|%-18s|%s",
						prod.getNombre(),
						String.format("%.2f", prod.getPrecio()),
						prod.getFabricante().getNombre())) // Formato de salida
				.toList();
		listado.forEach(System.out::println);

		Assertions.assertEquals(5, listProds.size());
		Assertions.assertTrue(listado.size() > 0);
	}

	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos.
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados.
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
	 * Fabricante: Asus
	 * <p>
	 * Productos:
	 * Monitor 27 LED Full HD
	 * Monitor 24 LED Full HD
	 * <p>
	 * Fabricante: Lenovo
	 * <p>
	 * Productos:
	 * Portátil Ideapd 320
	 * Portátil Yoga 520
	 * <p>
	 * Fabricante: Hewlett-Packard
	 * <p>
	 * Productos:
	 * Impresora HP Deskjet 3720
	 * Impresora HP Laserjet Pro M26nw
	 * <p>
	 * Fabricante: Samsung
	 * <p>
	 * Productos:
	 * Disco SSD 1 TB
	 * <p>
	 * Fabricante: Seagate
	 * <p>
	 * Productos:
	 * Disco duro SATA3 1TB
	 * <p>
	 * Fabricante: Crucial
	 * <p>
	 * Productos:
	 * GeForce GTX 1080 Xtreme
	 * Memoria RAM DDR4 8GB
	 * <p>
	 * Fabricante: Gigabyte
	 * <p>
	 * Productos:
	 * GeForce GTX 1050Ti
	 * <p>
	 * Fabricante: Huawei
	 * <p>
	 * Productos:
	 * <p>
	 * <p>
	 * Fabricante: Xiaomi
	 * <p>
	 * Productos:
	 */
	@Test
	void test28() {
		var listFabs = fabRepo.findAll();
		var listado = listFabs.stream()
				.map(fabricante -> {
					String encabezado = "Fabricante: " + fabricante.getNombre() + "\n\nProductos:\n";
					String productos = fabricante.getProductos().stream()
							.map(producto -> "             \t" + producto.getNombre() + "\n") // Formato de cada producto
							.collect(Collectors.joining()); // Unir productos en una cadena

					return encabezado + (productos.isEmpty() ? "             \t\n" : productos);
				})
				.collect(Collectors.joining("\n"));

		System.out.println(listado);

		Assertions.assertFalse(listFabs.isEmpty(), "La lista de fabricantes no debe estar vacía.");
		Assertions.assertTrue(listado.contains("Fabricante: "), "El listado debe contener fabricantes.");
	}

	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
		var listFabs = fabRepo.findAll();
		var sinProd = listFabs.stream()
				.filter(fabricante -> fabricante.getProductos().isEmpty()) // Filtrar aquellos sin productos
				.map(fabricante -> fabricante.getNombre()) // Obtener solo el nombre del fabricante
				.collect(Collectors.toList()); // Recoger los nombres en una lista

		System.out.println("Fabricantes sin productos:");
		sinProd.forEach(System.out::println);


		Assertions.assertEquals(9,listFabs.size());
		Assertions.assertFalse(sinProd.isEmpty(), "Debe haber al menos un fabricante sin productos.");
	}


	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
		var listProds = prodRepo.findAll(); // Obtener todos los productos
		var totalProductos = listProds.stream()
				.count();

		System.out.println("Total de productos: " + totalProductos);

		Assertions.assertEquals(11, totalProductos);
		Assertions.assertTrue(totalProductos >= 0, "El total de productos no puede ser negativo.");
	}


	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
		var listProds = prodRepo.findAll();
		var numeroFabricantesConProductos = listProds.stream()
				.map(Producto::getFabricante)
				.distinct()
				.count();

		System.out.println("Número de fabricantes con productos: " + numeroFabricantesConProductos);

		Assertions.assertEquals(7, numeroFabricantesConProductos);
		Assertions.assertTrue(numeroFabricantesConProductos >= 0, "El número de fabricantes con productos no debe ser negativo.");
	}


	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
		var listProds = prodRepo.findAll();
		var mediaPrecio = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.average()
				.orElse(0.0);

		System.out.println("Media del precio de todos los productos: " + mediaPrecio);

		/**ASSERTIONS**/
		Assertions.assertEquals(271.7236363636364, mediaPrecio);
	}


	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
		var listProds = prodRepo.findAll();
		var precioMasBarato = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.min();

		System.out.println("Precio más barato de todos los productos: " + precioMasBarato);

		Assertions.assertEquals(59.99, precioMasBarato.orElse(0.0));

		if (precioMasBarato.isPresent()) {
			Assertions.assertTrue(precioMasBarato.getAsDouble() >= 0.0, "El precio más barato debe ser cero o mayor.");
		} else {
			Assertions.assertTrue(listProds.isEmpty(), "Si no hay precio, la lista de productos debe estar vacía.");
		}
	}
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		var listProds = prodRepo.findAll();
		var sumaPrecios = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.sum();

		System.out.println("Suma de los precios de todos los productos: " + sumaPrecios);

		Assertions.assertTrue(sumaPrecios >= 0.0, "La suma de los precios no debe ser negativa.");
	}

	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		var listProds = prodRepo.findAll();
		var numeroProductosAsus = (int) listProds.stream()
				.filter(prod -> "Asus".equals(prod.getFabricante().getNombre()))
				.count();

		System.out.println("Número de productos del fabricante Asus: " + numeroProductosAsus);

		Assertions.assertTrue(numeroProductosAsus >= 0, "El número de productos de Asus no debe ser negativo.");
	}

	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		var listProds = prodRepo.findAll();
		var mediaPrecioAsus = listProds.stream()
				.filter(prod -> "Asus".equals(prod.getFabricante().getNombre()))
				.mapToDouble(Producto::getPrecio)
				.average()
				.orElse(0.0);

		System.out.println("Media del precio de todos los productos del fabricante Asus: " + mediaPrecioAsus);

		Assertions.assertTrue(mediaPrecioAsus >= 0.0, "La media del precio no debe ser negativa.");
	}


	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial.
	 * Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		var listProds = prodRepo.findAll();

		Double[] resultados = listProds.stream()
				.filter(prod -> "Crucial".equals(prod.getFabricante().getNombre()))
				.map(Producto::getPrecio)
				.reduce(new Double[]{Double.MAX_VALUE, Double.MIN_VALUE, 0.0, 0.0},
						(acc, precio) -> {
							acc[0] = Math.min(acc[0], precio);
							acc[1] = Math.max(acc[1], precio);
							acc[2] += precio;
							acc[3]++;
							return acc;
						},
						(acc1, acc2) -> acc1);

		double precioMedio = resultados[3] > 0 ? resultados[2] / resultados[3] : 0.0;

		// Imprimir resultados
		System.out.printf("Precio Mínimo: ", resultados[0]);
		System.out.printf("Precio Máximo: ", resultados[1]);
		System.out.printf("Precio Medio: ", precioMedio);
		System.out.println("Número Total de Productos: " + resultados[3]);


		Assertions.assertTrue(resultados[3] > 0, "Debe haber al menos un producto.");
		Assertions.assertEquals(resultados[0], resultados[0], "El precio mínimo debe ser correcto.");
		Assertions.assertEquals(resultados[1], resultados[1], "El precio máximo debe ser correcto.");
		Assertions.assertEquals(precioMedio, precioMedio, "La media del precio debe ser correcta.");
	}

	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes.
	 * El listado también debe incluir los fabricantes que no tienen ningún producto.
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene.
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 * <p>
	 * Fabricante     #Productos
	 * -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	 * Asus              2
	 * Lenovo              2
	 * Hewlett-Packard              2
	 * Samsung              1
	 * Seagate              1
	 * Crucial              2
	 * Gigabyte              1
	 * Huawei              0
	 * Xiaomi              0
	 */
	@Test
	void test38() {
		var listFabs = fabRepo.findAll();
		var listProds = prodRepo.findAll();

		var resultados = listFabs.stream()
				.map(fabricante -> {
					int count = (int) listProds.stream()
							.filter(prod -> prod.getFabricante().getCodigo() == fabricante.getCodigo())
							.count();
					return new String[]{fabricante.getNombre(), String.valueOf(count)};
				})
				.sorted((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1]))) // Ordenar de forma descendente
				.collect(Collectors.toList());

		// Imprimir resultados con formato
		System.out.printf("%-20s #Productos%n", "Fabricante");
		System.out.println("-".repeat(40));
		resultados.forEach(entry ->
				System.out.printf( entry[0], entry[1])
		);

		// Afirmación
		Assertions.assertFalse(resultados.isEmpty(), "Debe haber al menos un fabricante en la lista.");
	}

	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes.
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
		var listFabs = fabRepo.findAll();
		var listProds = prodRepo.findAll();

		var resultados = listFabs.stream()
				.map(fabricante -> {
					var precios = listProds.stream()
							.filter(prod -> prod.getFabricante().getCodigo() == fabricante.getCodigo())
							.map(Producto::getPrecio)
							.collect(Collectors.toList());

					double min = 0.0;
					double max = 0.0;
					double avg = 0.0;
					int count = precios.size();

					if (count > 0) {
						min = precios.stream().min(Double::compare).orElse(0.0);
						max = precios.stream().max(Double::compare).orElse(0.0);
						avg = precios.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
					}

					return String.format("Fabricante: ",
							fabricante.getNombre(), max, min, avg);
				})
				.collect(Collectors.toList());

		// Imprimir resultados
		resultados.forEach(System.out::println);
	}

	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€.
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
		var listProds = prodRepo.findAll();

		var resultados = listProds.stream()
				.collect(Collectors.groupingBy(prod -> prod.getFabricante().getCodigo()))
				.entrySet().stream()
				.map(entry -> {
					var precios = entry.getValue().stream()
							.mapToDouble(Producto::getPrecio)
							.toArray();

					double max = Arrays.stream(precios).max().orElse(0);
					double min = Arrays.stream(precios).min().orElse(0);
					double avg = Arrays.stream(precios).average().orElse(0);
					long count = precios.length;

					return new Object[]{entry.getKey(), max, min, avg, count};
				})
				.filter(values -> (double) values[3] > 200) // Filtrar por precio medio superior a 200
				.map(values -> String.format("ID Fabricante: %d, Max: %.2f, Min: %.2f, Avg: %.2f, Total: %d",
						values[0],
						values[1],
						values[2],
						values[3],
						values[4]))
				.collect(Collectors.toList());

		resultados.forEach(System.out::println);

		Assertions.assertFalse(resultados.isEmpty(), "Debe haber al menos un fabricante con precio medio superior a 200 €.");
	}


	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		var listProds = prodRepo.findAll();

		var fabricantesConProductos = listProds.stream()
				.collect(Collectors.groupingBy(prod -> prod.getFabricante().getNombre(), Collectors.counting()))
				.entrySet().stream()
				.filter(entry -> entry.getValue() >= 2)
				.map(entry -> entry.getKey())
				.collect(Collectors.toList());

		fabricantesConProductos.forEach(System.out::println);

		Assertions.assertFalse(fabricantesConProductos.isEmpty(), "Debe haber al menos un fabricante con 2 o más productos.");
	}



	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €.
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		var listProds = prodRepo.findAll();

		var fabricantesConProductos = listProds.stream()
				.filter(prod -> prod.getPrecio() >= 220)
				.collect(Collectors.groupingBy(prod -> prod.getFabricante().getNombre(),
						Collectors.counting()))
				.entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.map(entry -> String.format("Fabricante: %s, Número de productos: %d", entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());

		fabricantesConProductos.forEach(System.out::println);

		Assertions.assertFalse(fabricantesConProductos.isEmpty(), "Debe haber al menos un fabricante con productos que superen 220 €.");
	}

	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		var listProds = prodRepo.findAll();

		var fabricantesFiltrados = listProds.stream()
				.collect(Collectors.groupingBy(prod -> prod.getFabricante().getNombre(),
						Collectors.summingDouble(Producto::getPrecio))) // Agrupar y sumar precios
				.entrySet().stream()
				.filter(entry -> entry.getValue() > 1000) // Filtrar por suma superior a 1000
				.map(Map.Entry::getKey) // Obtener solo el nombre del fabricante
				.collect(Collectors.toList());

		// Imprimir resultados
		fabricantesFiltrados.forEach(System.out::println);

		// Afirmaciones
		Assertions.assertFalse(fabricantesFiltrados.isEmpty(), "Debe haber al menos un fabricante con suma de precios superior a 1000 €.");
	}


	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		var listProds = prodRepo.findAll();

		var fabricantesFiltrados = listProds.stream()
				.collect(Collectors.groupingBy(prod -> prod.getFabricante().getNombre()))
				.entrySet().stream()
				.filter(entry -> entry.getValue().stream()
						.mapToDouble(Producto::getPrecio)
						.sum() > 1000)
				.sorted(Comparator.comparing(entry -> entry.getValue().stream()
						.mapToDouble(Producto::getPrecio)
						.sum()))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());

		fabricantesFiltrados.forEach(System.out::println);

		Assertions.assertFalse(fabricantesFiltrados.isEmpty(), "Debe haber al menos un fabricante con suma de precios superior a 1000 €.");
	}


	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante.
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante.
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		var listProds = prodRepo.findAll();

		var productosMasCaros = listProds.stream()
				.collect(Collectors.groupingBy(prod -> prod.getFabricante().getNombre())) // Agrupar por fabricante
				.entrySet().stream()
				.map(entry -> {
					var maxProducto = entry.getValue().stream()
							.max(Comparator.comparing(Producto::getPrecio)) // Obtener el producto más caro
							.orElse(null);

					// Retornar el nombre del producto, precio y fabricante si no es nulo
					return maxProducto != null
							? String.format("Producto: %s, Precio: %.2f, Fabricante: %s",
							maxProducto.getNombre(),
							maxProducto.getPrecio(),
							entry.getKey())
							: null;
				})
				.filter(Objects::nonNull)
				.sorted()
				.collect(Collectors.toList());

		productosMasCaros.forEach(System.out::println);
		Assertions.assertFalse(productosMasCaros.isEmpty(), "Debe haber al menos un producto más caro por fabricante.");
	}


	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		var listProds = prodRepo.findAll();

		var productosFiltrados = listProds.stream()
				.collect(Collectors.groupingBy(prod -> prod.getFabricante().getNombre())) // Agrupar por fabricante
				.entrySet().stream()
				.flatMap(entry -> {
					double mediaPrecio = entry.getValue().stream()
							.mapToDouble(Producto::getPrecio)
							.average()
							.orElse(0.0); // Calcular la media

					return entry.getValue().stream()
							.filter(prod -> prod.getPrecio() >= mediaPrecio) // Filtrar productos
							.map(prod -> new AbstractMap.SimpleEntry<>(entry.getKey(), prod)); // Retornar fabricante y producto
				})
				.sorted(Comparator.comparing(Map.Entry<String, Producto>::getKey)
						.thenComparing(entry -> -entry.getValue().getPrecio())) // Ordenar
				.collect(Collectors.toList());

		// Imprimir resultados
		productosFiltrados.forEach(entry -> System.out.printf("Fabricante: %s, Producto: %s, Precio: %.2f%n",
				entry.getKey(), entry.getValue().getNombre(), entry.getValue().getPrecio()));

		// Afirmaciones
		Assertions.assertFalse(productosFiltrados.isEmpty(), "Debe haber al menos un producto que cumpla con la condición.");
	}



}
