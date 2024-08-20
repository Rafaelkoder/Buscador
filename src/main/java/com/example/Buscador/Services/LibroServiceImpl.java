package com.example.Buscador.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Buscador.Modelos.Entidades.Libro;
import com.example.Buscador.Repositorios.LibroRespository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroServiceImpl implements ILibroService {

    @Autowired
    LibroRespository libroRespository;


    @Override
    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return (List<Libro>)libroRespository.findAll();
    }

    @Override
    @Transactional
    public Libro save(Libro libro) {
        return libroRespository.save(libro);
    }

    @Override
    @Transactional
    public void eliminarById(Long id) {
        libroRespository.delete(libroRespository.findById(id).get());
    }

    @Override
    @Transactional(readOnly = true)
	public Optional<Libro> findById(Long id) {
		return libroRespository.findById(id);
	}

    @Override
	public List<Libro> findByTitulo(String titulo) {
		return (List<Libro>)libroRespository.findByTitulo(titulo);
	}

	@Override
	public List<Libro> findByGenero(String genero) {
		return (List<Libro>)libroRespository.findByGenero(genero);
	}

	@Override
	public List<Libro> findByAuthor(String autor) {
		return (List<Libro>)libroRespository.findByAuthor(autor);
	}

	@Override
	public Libro findByISBN(String isbn) {
		return libroRespository.findByISBN(isbn);
	}
    
}
