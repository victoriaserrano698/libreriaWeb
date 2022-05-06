package com.example.LibreriaDiscord.services;

import com.example.LibreriaDiscord.entity.Autor;
import com.example.LibreriaDiscord.entity.Editorial;
import com.example.LibreriaDiscord.entity.Libro;
import com.example.LibreriaDiscord.repository.LibroRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio lr;

    @Autowired
    private AutorServicio as;
    
    @Autowired
    private EditorialServicio es;
    
    @Transactional
    public Libro crear(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception {
        validator(isbn, titulo, anio, ejemplares, autor, editorial);

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        return lr.save(libro);
    }

    @Transactional
    public Libro modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception {
        validator(isbn, titulo, anio, ejemplares, autor, editorial);

        Optional<Libro> objetoTraido = lr.findById(id);

        if (objetoTraido.isPresent()) {
            Libro libro = objetoTraido.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            return lr.save(libro);

        } else {
            throw new Exception("No existe un libro con ese id");
        }
    }

    @Transactional
    public void cambiarAlta(String id) throws Exception {
        Libro l = lr.getById(id);
        if (l == null) {
            throw new Exception("No existe un libro con ese id");
        }
        if (l.getAlta()) {
            l.setAlta(false);
        } else {
            l.setAlta(true);
        }
        lr.save(l);
    }
    
    public Libro buscarLibroPorId(String id){
        return lr.buscarPorId(id);
    }

    
    @Transactional(readOnly = true)
    public Libro buscarPorTitulo(String titulo) throws Exception {

        Libro l = lr.findByTitulo(titulo);
        if (l == null) {
            throw new Exception("No existe un libro con ese título");
        }
        return l;
    }

    public void validator(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception {

        if (isbn == null || isbn < 0) {
            throw new Exception("El isbn no puede ser nulo ni menor que cero");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("El título del libro no puede estar vacío ni ser nulo");
        }
        if (anio == null || anio <= 0) {
            throw new Exception("El año del libro no puede ser igual a cero ni ser nulo");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new Exception("La cantidad de ejemplares no puede ser nula ni igual a cero");
        }
        if (autor == null) {
            throw new Exception("El autor del libro no puede ser nulo");
        }
        if (editorial == null) {
            throw new Exception("La editorial del libro no puede ser nula");
        }
        as.validate(autor.getNombre());
        es.validate(editorial.getNombre());
    }

}
