package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name="autor")
    //@Column(unique = true)
    private String nombreAutor;
    private Integer nacimiento;
    private Integer muerte;

    /*
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro>libros;
     */

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombreAutor = datosAutor.nombreAutor();
        this.nacimiento = datosAutor.nacimiento();
        this.muerte = datosAutor.muerte();
    }

    @Override
    public String toString() {
        return "nombreAutor='" + nombreAutor + '\'' +
                ", nacimiento=" + nacimiento +
                ", muerte=" + muerte;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getMuerte() {
        return muerte;
    }

    public void setMuerte(Integer muerte) {
        this.muerte = muerte;
    }

    /*
    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

     */
}
