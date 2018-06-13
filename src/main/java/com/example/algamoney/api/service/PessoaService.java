package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa create(Pessoa pessoa)
	{
		return pessoaRepository.save(pessoa);
	}
	
	public Pessoa update(Long codigo, Pessoa pessoa)
	{
		Pessoa pessoaSalva = findPessoa(codigo);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		return pessoaRepository.save(pessoaSalva);
	}

	
	public void inativarPessoa(Long codigo, Boolean ativo) 
	{
		Pessoa pessoaSalva = findPessoa(codigo);
		
		pessoaSalva.setAtivo(ativo);	
		
		pessoaRepository.save(pessoaSalva);
	}
	
	public void delete(Long codigo)
	{	
		pessoaRepository.delete(codigo);		
	}
	
	private Pessoa findPessoa(Long codigo)
	{
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		
		if(pessoaSalva == null)
		{
			throw new EmptyResultDataAccessException(1); 
		}
		return pessoaSalva;
	}

	public List<Pessoa> list() 
	{
		return pessoaRepository.findAll();
	}

	public Pessoa edit(Long codigo) 
	{
		return pessoaRepository.findOne(codigo);
	}
}
