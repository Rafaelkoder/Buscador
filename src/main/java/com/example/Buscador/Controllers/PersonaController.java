package com.example.Buscador.Controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Buscador.Modelos.Entidades.Libro;
import com.example.Buscador.Services.ILibroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/buscador")
public class PersonaController {

	 @Autowired
	    ILibroService iLibroService;

	    @GetMapping
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<List<Libro>> listar(){
	        return ResponseEntity.ok(iLibroService.findAll());
	    }
	    
	    @GetMapping("/genero")
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<?> detalleGenero(@RequestParam(value = "genero", defaultValue = "") String genero){
	            return ResponseEntity.ok(iLibroService.findByGenero(genero));
	    }
	    
	    @GetMapping("/autor")
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<?> detalleAutor(@RequestParam(value = "autor", defaultValue = "") String autor){
	            return ResponseEntity.ok(iLibroService.findByAuthor(autor));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<?> detalleId(@PathVariable Long id){
	        Optional<Libro> persona = iLibroService.findById(id);//service.porId(id);
	        if (persona.isPresent()) {
	            return ResponseEntity.ok(persona.get());
	        }
	        return ResponseEntity.notFound().build();
	    }
	    
	    @GetMapping("/titulo")
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<?> detalleTitulo(@RequestParam(value = "titulo", defaultValue = "") String titulo){
	    	return ResponseEntity.ok(iLibroService.findByTitulo(titulo));
	    }
	    
	    @GetMapping("/isbn")
	    public ResponseEntity<?> detalleISBN(@RequestParam(value = "isbn", defaultValue = "") String isbn){
	        Libro libro = iLibroService.findByISBN(isbn);
	        if (!libro.equals(null)) {
	            return ResponseEntity.ok(libro);
	        }
	        return ResponseEntity.notFound().build();
	    }
	    
	    @CrossOrigin(origins = "http://localhost:3000")
	    @PostMapping("/crearlibro")
	    public ResponseEntity<?> crear(@Valid @RequestBody Libro libro, BindingResult result) {
	        if (result.hasErrors()) {
	            return validar(result);
	        }
	        Libro libroDb = iLibroService.save(libro);
	        return ResponseEntity.status(HttpStatus.CREATED).body(libroDb);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<?> editar(@Valid @RequestBody Libro libro, BindingResult result, @PathVariable Long id) {
	        if (result.hasErrors()) {
	            return validar(result); 
	        }
	        Optional<Libro> o = iLibroService.findById(id);
	        if (o.isPresent()) {
	            Libro libroDB = o.get();
	            libroDB.setTitle(libro.getTitle());
	            libroDB.setAuthor(libro.getAuthor());
	            libroDB.setCover(libro.getCover());
	            libroDB.setGenero(libro.getGenero());
	            libroDB.setISBN(libro.getISBN());
	            libroDB.setSinopsis(libro.getSinopsis());
	            return ResponseEntity.status(HttpStatus.CREATED).body(iLibroService.save(libroDB));
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> eliminar(@PathVariable Long id) {
	        Optional<Libro> o = iLibroService.findById(id);
	        if (o.isPresent()) {
	            iLibroService.eliminarById(o.get().getId());
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.notFound().build();
	    }


	    private ResponseEntity<Map<String, String>> validar(BindingResult result) { 
	        Map<String, String> errores = new HashMap<>();
	        result.getFieldErrors().forEach(err -> {
	            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
	        });
	        return ResponseEntity.badRequest().body(errores);
	    }
}
