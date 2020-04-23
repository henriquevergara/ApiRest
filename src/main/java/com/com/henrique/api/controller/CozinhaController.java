package com.com.henrique.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.com.henrique.domain.model.Cozinha;
import com.com.henrique.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }) // atributo utilizado
																									// para setar os
																									// content
																									// negotiation da
																									// requisicao,
																									// endpoint retorna
																									// apenas em xml e
																									// json.
	public ResponseEntity<List<Cozinha>> listar() {
		// return
		// ResponseEntity.status(HttpStatus.OK).body(cozinhaRepository.findAll());
		return ResponseEntity.ok(cozinhaRepository.findAll());
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {

		if (cozinhaRepository.findById(id).isPresent()) {
			return ResponseEntity.ok(cozinhaRepository.findById(id).get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void incluir(@RequestBody Cozinha cozinha) {

		cozinhaRepository.save(cozinha);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {

		if (cozinhaRepository.findById(id).isPresent()) {

			Cozinha cozinhaAtual = cozinhaRepository.findById(id).get();

			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); // 3ª parâmetro serve para indicar atributos que
																	// devem ser ignorados na copia.

			return ResponseEntity.ok().body(cozinhaRepository.save(cozinhaAtual));

		} else {

			return ResponseEntity.notFound().build();

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {

		try {

			if (cozinhaRepository.findById(id).isPresent()) {

				Cozinha cozinha = cozinhaRepository.findById(id).get();
				cozinhaRepository.delete(cozinha);

				return ResponseEntity.noContent().build();

			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

}
