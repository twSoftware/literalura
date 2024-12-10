package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi=new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }


    public void muestraElMenu(){
        /*System.out.println("Ingresa el nombre del libro o autor: ");
        var busqueda = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + busqueda.replace(" ","+"));
        var datos = conversor.obtenerDatos(json, Resultados.class);
        System.out.println(datos);
         */

        var opcion = -1;
        while (opcion!=0){
            var menu = """
            *************************
            ---- MENU Literalura ----
            *************************
            --------------------------------------------
                         
            1 - Buscar libros por título.
            2 - Listar libros registrados.
            3 - Consultar autores y sus libros.
            4 - Consultar autores vivos en un determinado año.
            5 - Consultar libros por idioma. 
            ----------------------------------------------
            0 - SALIR 
            ----------------------------------------------
            Escribe el numero de la opción:
            """;

            try{
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();
            }catch (Exception e){
                System.out.println("Ingresa opción valida.");
            }

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    consultarLibros();
                    break;
                case 3:
                    consultarAutores();
                    break;
                case 4:
                    consultarAutoresPorAno();
                    break;
                case 5:
                    consultarLibrosIdioma();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Elije una opcion del 1 al 5.");
            }
        }


    }

    private Resultados getDatosLibro(){
        System.out.println("Escribe el titulo del libro que quieres buscar: ");
        var busqueda = teclado.nextLine().toLowerCase().replace(" ", "%20");
        var json = consumoApi.obtenerDatos(URL_BASE+"?search="+busqueda);
        Resultados datosLibro = conversor.obtenerDatos(json,Resultados.class);
        return datosLibro;
    }

    private void buscarLibroPorTitulo() {
        Resultados datosLibro = getDatosLibro();
        try {
            Libro libro = new Libro(datosLibro.resultados().get(0));
            Autor autor = new Autor(datosLibro.resultados().get(0).datosAutores().get(0));

            System.out.println(
                    """
                    ----Libro----
                        Título: %s
                        Autor: %s
                        Idioma: %s
                        Descargas: %s
                    
                    """.formatted(libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIdioma(),
                    libro.getDescargas().toString()));


            autorRepository.save(autor);
            libroRepository.save(libro);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("No se encontro el libro");
        }

    }

    private void consultarLibros() {
        libros = libroRepository.findAll();
        libros.stream().forEach(l -> {
            System.out.println("""   
                    ______LIBRO_________     
                        Título: %s
                        Autor: %s
                        Lenguaje: %s
                        Descargas: %s
                    ____________________
                    """.formatted(l.getTitulo(),
                    l.getAutor(),
                    l.getIdioma(),
                    l.getDescargas().toString()));
        });
        /*
        List<Libro>libros = autorRepository.buscarTodosLosLibros();
        libros.forEach(l-> System.out.println(
                "-------------- LIBRO \uD83D\uDCD9  -----------------" +
                        "\nTítulo: " + l.getTitulo() +
                        "\nAutor: " + l.getAutor() +
                        "\nIdioma: " + l.getIdioma() +
                        "\nNúmero de descargas: " + l.getDescargas() +
                        "\n----------------------------------------\n"
        ));

         */

    }
    private void consultarAutores() {
        autores = autorRepository.findAll();
        autores.stream().forEach(a -> {
            System.out.println(
                    """
                    ------Autor------    
                        Autor: %s
                        Año de nacimiento: %s
                        Año de muerte: %s
                    -----------------    
                    """.formatted(a.getNombreAutor(),a.getNacimiento().toString(),a.getMuerte().toString()));
        });
    }

    public void consultarAutoresPorAno()
    {
        System.out.println("Ingresa el año a partir del cual buscar:");
        Integer anoBusqueda = teclado.nextInt();
        teclado.nextLine();

        //List<Autor> autores = autorRepository.autorPorFecha(anoBusqueda);
        List<Autor> autores = autorRepository.autorPorFecha(anoBusqueda);

        autores.forEach( a -> {
            System.out.println(
                    """
                    ----Autor--------
                    Nombre: %s
                    Fecha de nacimiento: %s
                    Fecha de muerte: %s
                    -------------------
                    """.formatted(a.getNombreAutor(),a.getNacimiento().toString(),a.getMuerte().toString()));
        });
    }

    private void consultarLibrosIdioma()
    {
        System.out.println(
                """
                ----------------------------------------------------   
                    Selcciona el lenguaje que quieres consultar
                ----------------------------------------------------
                1 para Ingles
                2 para Español
                """);

        try {

            var opcion2 = teclado.nextInt();
            teclado.nextLine();

            switch (opcion2) {
                case 1:
                    libros = libroRepository.findByIdioma("en");
                    break;
                case 2:
                    libros = libroRepository.findByIdioma("es");
                    break;

                default:
                    System.out.println("Ingresa una de las opciones (en o es)");
            }

            libros.stream().forEach(l -> {
                System.out.println("""    
                        Titulo: %s
                        Author: %s
                        Lenguaje: %s
                        Descargas: %s
                    """.formatted(l.getTitulo(),
                        l.getAutor(),
                        l.getIdioma(),
                        l.getDescargas().toString()));
            });

        } catch (Exception e){
            System.out.println("Ingresa un valor valido");
        }
    }

}
