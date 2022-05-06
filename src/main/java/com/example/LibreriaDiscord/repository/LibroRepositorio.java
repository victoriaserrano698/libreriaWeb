/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.LibreriaDiscord.repository;

import com.example.LibreriaDiscord.entity.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public Libro buscarPorId(@Param("id") String id);

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro findByTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :autor")
    public List<Libro> findByAutor(@Param("autor") String autor);

    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :editorial")
    public List<Libro> findByEditorial(@Param("editorial") String Editorial);

}
