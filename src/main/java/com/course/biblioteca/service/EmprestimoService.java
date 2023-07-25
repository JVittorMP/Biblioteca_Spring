package com.course.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.biblioteca.domain.Emprestimo;
import com.course.biblioteca.repository.EmprestimoRepository;

@Service
public class EmprestimoService {
	@Autowired
	private EmprestimoRepository emprestimoRepository;
	
	public List<Emprestimo> getAllEmprestimos(){
		List<Emprestimo> emprestimos = new ArrayList<>();
		emprestimoRepository.findAll().forEach(emprestimos::add);
		return emprestimos;
	}
	
	public Emprestimo getEmprestimo(Integer id) throws NoSuchElementException {
		return emprestimoRepository.findById(id).orElseThrow();
	}
	
	public void saveEmprestimo(Emprestimo emprestimo) {
		emprestimoRepository.save(emprestimo);
	}
	
	public void updateEmprestimo(Emprestimo emprestimo, Integer id) throws NoSuchElementException {
		emprestimoRepository.findById(id).orElseThrow();
		emprestimo.setId(id);
		emprestimoRepository.save(emprestimo);
	}
	
	public void deleteEmprestimo(Integer id) throws IllegalArgumentException {
		emprestimoRepository.deleteById(id);
	}
}
