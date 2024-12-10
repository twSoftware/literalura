package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    //@Query("SELECT a FROM Autor a WHERE a.nacimiento >= :anoBusqueda ORDER BY a.nacimiento ASC")
    //List<Autor>autorPorFecha(@Param("anoBusqueda") int anoBusqueda);


    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :anoBusqueda AND a.muerte >= :anoBusqueda")
    List<Autor>autorPorFecha(Integer anoBusqueda);


    /*
    @Query("SELECT l FROM Autor a JOIN a.libros l")
    List<Libro> buscarTodosLosLibros();
     */

}
