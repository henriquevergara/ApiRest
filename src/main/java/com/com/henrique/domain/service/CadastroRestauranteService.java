package com.com.henrique.domain.service;

import org.springframework.beans.BeanUtils;
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

		Long cozinhaId = restaurante.getCozinha().getId();

		if (!cozinhaRepository.findById(cozinhaId).isPresent()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe cadastro de cozinha com codigo %d.", cozinhaId));
		}

		restaurante.setCozinha(cozinhaRepository.findById(cozinhaId).get());

		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(Restaurante restaurante, Long id) {

		Long cozinhaId = restaurante.getCozinha().getId();

		System.out.println(cozinhaRepository.findById(cozinhaId).isPresent());

		if (!cozinhaRepository.findById(cozinhaId).isPresent()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe cadastro de cozinha com codigo %d.", cozinhaId));
		}

		Restaurante restauranteAtual = restauranteRepository.findById(id).get();
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		return restauranteRepository.save(restauranteAtual);

	}
	
	public void excluir (Long id) {
		
		if (!restauranteRepository.findById(id).isPresent()) {
			
			throw new EntidadeNaoEncontradaException(String.format("Restaurante com id %d nao encontrado",id));
			
		}
		
		restauranteRepository.deleteById(id);
		
		
	}

}
