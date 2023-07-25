package com.course.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.biblioteca.domain.Livro;
import com.course.biblioteca.repository.LivroRepository;

@Service
public class LivroService {
	@Autowired
	private LivroRepository livroRepository;
	
	public List<Livro> getAllLivros(){
		List<Livro> livros = new ArrayList<>();
		livroRepository.findAll().forEach(livros::add);
		return livros;
	}
	
	public Livro getLivro(Integer id) throws NoSuchElementException {
		return livroRepository.findById(id).orElseThrow();
	}
	
	public List<Livro> getAvailableLivros(){
		List<Livro> livros = new ArrayList<>();
		livroRepository.findAvailableLivros().forEach(livros::add);
		return livros;
	}
	
	public void saveLivro(Livro livro) {
		livroRepository.save(livro);
	}
	
	public void updateLivro(Livro livro, Integer id) throws NoSuchElementException {
		livroRepository.findById(id).orElseThrow();
		livro.setId(id);
		livroRepository.save(livro);
	}
	
	public void deleteLivro(Integer id) throws IllegalArgumentException {
		livroRepository.deleteById(id);
	}
	
	public void updateStatus(Livro livro) {
		if(livro.getDisponivel())
			livro.setDisponivel(false);
		else
			livro.setDisponivel(true);
	}
}
