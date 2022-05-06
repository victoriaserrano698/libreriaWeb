/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.LibreriaDiscord.services;

import com.example.LibreriaDiscord.entity.Autor;
import com.example.LibreriaDiscord.repository.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author WEB
 */
@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio ar;

    @Transactional
    public Autor crear(String nombre) throws Exception {

        validate(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);
        return ar.save(autor);

    }

    @Transactional
    public Autor modificar(String id, String nombre) throws Exception {

        validate(nombre);

        Optional<Autor> objetoTraido = ar.findById(id);

        if (objetoTraido.isPresent()) {
            Autor autor = objetoTraido.get();
            autor.setNombre(nombre);
            autor.setAlta(autor.getAlta());
            return ar.save(autor);

        } else {
            throw new Exception("No existe autor con ese id");
        }

    }

    @Transactional
    public void eliminar(String id) throws Exception {
        Autor autor = ar.buscarPorId(id);

        if (autor.getNombre() != null) {
            ar.delete(autor);
            ar.save(autor);

        } else {
            throw new Exception("No existe un autor con el valor solicitado");
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return ar.findAll();
    }

    @Transactional(readOnly = true)
    public List<Autor> buscarAutorPorNombre(String nombre) {
        return ar.buscarPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public Autor buscarAutorPorId(String id) {
        return ar.buscarPorId(id);
    }

    @Transactional
    public Autor darDeBaja(String id) throws Exception {

        Autor autor = ar.getById(id);
        if (autor == null) {
            autor.setAlta(false);

            return ar.save(autor);
        } else {
            throw new Exception("No existe autor con ese id");
        }

    }

    public void validate(String nombre) throws Exception {
        Autor autor = new Autor();
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (autor.getNombre().equals(buscarAutorPorNombre(nombre))) {
            throw new Exception("El nombre del autor no puede repetirse");
        }
    }
}
