package com.example.Buscador.Repositorios;

import com.example.Buscador.Modelos.Entidades.Libro;
import feign.Param;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface LibroRespository extends JpaRepository<Libro,Long> {
	
	@Query("SELECT L FROM Libro L WHERE L.title LIKE %:titulo%")
	List<Libro> findByTitulo(@Param("titulo") String titulo);
	
	@Query("SELECT L FROM Libro L WHERE L.isbn = :isbn")
	Libro findByISBN(@Param("isbn") String isbn);
	
	@Query("SELECT L FROM Libro L WHERE L.genero LIKE %:genero%")
	List<Libro> findByGenero(@Param("genero") String genero);
	
	@Query("SELECT L FROM Libro L WHERE L.author LIKE %:author%")
	List<Libro> findByAuthor(@Param("author") String author);
	
}