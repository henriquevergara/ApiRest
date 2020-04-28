package com.com.henrique.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.com.henrique.domain.exception.EntidadeEmUsoException;
import com.com.henrique.domain.exception.EntidadeNaoEncontradaException;
import com.com.henrique.domain.model.Cidade;
import com.com.henrique.domain.repository.CidadeRepository;
import com.com.henrique.domain.repository.EstadoRepository;

@Service
public class CadastrarCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();

		if (!estadoRepository.findById(estadoId).isPresent()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe cadastro de estado com codigo %d.", estadoId));
		}

		cidade.setEstado(estadoRepository.findById(estadoId).get());

		return cidadeRepository.save(cidade);
	}

	public Cidade atualizar(Cidade cidade, Long id) {

		Long estadoId = cidade.getEstado().getId();

		if (!estadoRepository.findById(estadoId).isPresent()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe cadastro de estado com codigo %d.", estadoId));
		}

		Cidade cidadeAtual = cidadeRepository.findById(id).get();
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		return cidadeRepository.save(cidadeAtual);

	}

	public void excluir(Long id) {

		try {

			cidadeRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de codigo %d nao pode ser removida, pois esta em uso.", id));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de cidade com codigo %d.", id));
		}
	}

}
