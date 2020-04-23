package com.com.henrique.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.com.henrique.domain.exception.EntidadeEmUsoException;
import com.com.henrique.domain.exception.EntidadeNaoEncontradaException;
import com.com.henrique.domain.model.Cozinha;
import com.com.henrique.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	
	public void excluir(Long cozinhaId) {
		
		try {
			
			cozinhaRepository.deleteById(cozinhaId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha de codigo %d nao pode ser removida, pois esta em uso.", cozinhaId));
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Nao existe um cadastro de cozinha com codigo %d.", cozinhaId));
		}
		
	}
}
