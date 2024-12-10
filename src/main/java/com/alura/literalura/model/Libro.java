package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name="libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String autor;
    private String titulo;
    private String idioma;
    private Integer descargas;


    public Libro(){
    }

    public Libro(DatosLibro libro) {

        this.titulo = libro.titulo();
        this.autor = libro.datosAutores().get(0).nombreAutor();
        //this.idioma = Idioma.fromString(libro.idiomas().stream()
        //        .limit(1).collect(Collectors.joining()));
        this.idioma=libro.idiomas().get(0);
        this.descargas = libro.descargas();
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma=" + idioma +
                ", descargas=" + descargas +
                ", autor=" + autor +
                '}';
    }
}
