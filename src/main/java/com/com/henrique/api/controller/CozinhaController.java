package com.com.henrique.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.com.henrique.domain.exception.EntidadeEmUsoException;
import com.com.henrique.domain.exception.EntidadeNaoEncontradaException;
import com.com.henrique.domain.model.Cozinha;
import com.com.henrique.domain.repository.CozinhaRepository;
import com.com.henrique.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }) // atributo utilizado
																							// apenas em xml e		// json.
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

	@GetMapping
	public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {

		if (cozinhaRepository.findByNome(nome).isEmpty()){
			return ResponseEntity.notFound().build();
		}else{
			return ResponseEntity.ok(cozinhaRepository.findByNome(nome));
		}
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void incluir(@RequestBody Cozinha cozinha) {

		cadastroCozinha.salvar(cozinha);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {

		if (cozinhaRepository.findById(id).isPresent()) {

			Cozinha cozinhaAtual = cozinhaRepository.findById(id).get();

			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); // 3ª parâmetro serve para indicar atributos que
																	// devem ser ignorados na copia.

			return ResponseEntity.ok().body(cadastroCozinha.salvar(cozinhaAtual));

		} else {

			return ResponseEntity.notFound().build();

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {

		try {

			cadastroCozinha.excluir(id);

			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

}
