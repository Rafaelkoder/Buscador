package com.example.Buscador.Services;


import com.example.Buscador.Modelos.Entidades.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibroService {

	Optional<Libro> findById(Long id);
    List<Libro> findByTitulo(String titulo);
    List<Libro> findByGenero(String genero);
    List<Libro> findByAuthor(String author);
    Libro findByISBN(String isbn);
    List<Libro> findAll();
    Libro save (Libro libro);
    void eliminarById (Long id);

}
