package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> list()
	{
		return categoriaRepository.findAll();
	}
	
	public Categoria create(Categoria categoria)
	{
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Long codigo, Categoria categoria)
	{
		Categoria categoriaSalva = findCategoria(codigo);
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		
		return categoriaRepository.save(categoriaSalva);
	}
	
	public void delete(Long codigo)
	{
		categoriaRepository.delete(codigo);
	}
	
	private Categoria findCategoria(Long codigo)
	{
		Categoria categoriaSalva = categoriaRepository.findOne(codigo);
		
		if(categoriaSalva == null)
		{
			throw new EmptyResultDataAccessException(1); 
		}
		
		return categoriaSalva;
				
	}
	
}

