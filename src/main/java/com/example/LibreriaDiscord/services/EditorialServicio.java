/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.LibreriaDiscord.services;

import com.example.LibreriaDiscord.entity.Editorial;
import com.example.LibreriaDiscord.repository.EditorialRepositorio;
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
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio er;

    @Transactional
    public Editorial crear(String nombre) throws Exception {
        validate(nombre);

        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);
        return er.save(editorial);

    }

    @Transactional
    public Editorial modificar(String id, String nombre) throws Exception {

        validate(nombre);

        Optional<Editorial> objetoTraido = er.findById(id); //optional, trae un objeto o lista, ya sea cargado o nulo

        if (objetoTraido.isPresent()) {

            Editorial editorial = objetoTraido.get();
            editorial.setNombre(nombre);
            editorial.setAlta(editorial.getAlta());
            return er.save(editorial);

        } else {
            throw new Exception("No existe editorial con ese id");
        }
    }

    @Transactional
    public void eliminar(String id) throws Exception {
        Editorial editorial = er.buscarPorId(id);

        if (editorial.getNombre() != null) {
            er.delete(editorial);
            er.save(editorial);
        } else {
            throw new Exception("No existe una editorial con el valor solicitado");
        }
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        return er.findAll();
    }

    @Transactional(readOnly = true)
    public List<Editorial> buscarEditorialPorNombre(String nombre) {
        return er.buscarPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public Editorial buscarEditorialPorId(String id) {
        return er.buscarPorId(id);
    }

    @Transactional
    public Editorial darDeBaja(String id) throws Exception {

        Editorial editorial = er.getById(id);
        if (editorial == null) {
            editorial.setAlta(false);

            return er.save(editorial);
        } else {
            throw new Exception("No existe editorial con ese ID");
        }

    }

    public void validate(String nombre) throws Exception {
        Editorial editorial = new Editorial();
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío ni ser nulo");
        }
        if (editorial.getNombre().equals(buscarEditorialPorNombre(nombre))) {
            throw new Exception("El nombre de la editorial no puede repetirse");
        }//esta funcion valida si ya esta cargado el nombre que le queremos poner a nuestra nueva editorial

    }

}
