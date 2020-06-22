package com.com.henrique.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.com.henrique.domain.exception.EntidadeEmUsoException;
import com.com.henrique.domain.exception.EntidadeNaoEncontradaException;
import com.com.henrique.domain.model.Cidade;
import com.com.henrique.domain.model.Restaurante;
import com.com.henrique.domain.repository.CidadeRepository;
import com.com.henrique.domain.service.CadastrarCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

//	revisao de codigo
//
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastrarCidadeService cadastrarCidadeService;

	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(cidadeRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {

		if (cidadeRepository.findById(id).isPresent()) {
			return ResponseEntity.ok(cidadeRepository.findById(id).get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping
	public ResponseEntity<?> incluir(@RequestBody Cidade cidade) {

		try {

			return ResponseEntity.status(HttpStatus.CREATED).body(cadastrarCidadeService.salvar(cidade));

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable("cidadeId") Long id, @RequestBody Cidade cidade) {

		try {

			if (cidadeRepository.findById(id).isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(cadastrarCidadeService.atualizar(cidade, id));
			}
			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			cadastrarCidadeService.excluir(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
