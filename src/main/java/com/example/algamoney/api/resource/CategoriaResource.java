package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;
import com.example.algamoney.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaService.list();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response)
	{
		Categoria categoriaSalva = categoriaService.create(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public Categoria edit(@PathVariable Long codigo)
	{
		return categoriaRepository.findOne(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo)
	{
		categoriaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> update(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria)
	{
		Categoria categoriaSalva = categoriaService.update(codigo, categoria);
		
		return ResponseEntity.ok(categoriaSalva);
	}
}
