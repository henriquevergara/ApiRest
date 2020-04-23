package com.com.henrique.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.com.henrique.domain.exception.EntidadeNaoEncontradaException;
import com.com.henrique.domain.model.Restaurante;
import com.com.henrique.domain.repository.CozinhaRepository;
import com.com.henrique.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId =  restaurante.getCozinha().getId();
		
		
		if (!cozinhaRepository.findById(cozinhaId).isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Nao existe cadastro de cozinha com codigo %d.", cozinhaId));
		}
		
		restaurante.setCozinha(cozinhaRepository.findById(cozinhaId).get());
		
		return restauranteRepository.save(restaurante);
	}
	
}
