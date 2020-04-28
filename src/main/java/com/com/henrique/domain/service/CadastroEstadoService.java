package com.com.henrique.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.com.henrique.domain.exception.EntidadeEmUsoException;
import com.com.henrique.domain.exception.EntidadeNaoEncontradaException;
import com.com.henrique.domain.model.Estado;
import com.com.henrique.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado atualizar(Long id, Estado estado) {

		if (estadoRepository.findById(id).isPresent()) {
			Estado estadoAtual = estadoRepository.findById(id).get();
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			estadoRepository.save(estadoAtual);
			return estadoAtual;
		} else {
			throw new EntidadeNaoEncontradaException(String.format("Estado com id %d não encontrado.", id));
		}

	}

	public void excluir(Long id) {

		try {

			estadoRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de codigo %d nao pode ser removida, pois esta em uso.", id));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de estado com codigo %d.", id));
		}
	}

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

}
