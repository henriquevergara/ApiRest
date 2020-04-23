package com.com.henrique.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.com.henrique.domain.exception.EntidadeNaoEncontradaException;
import com.com.henrique.domain.model.Restaurante;
import com.com.henrique.domain.repository.RestauranteRepository;
import com.com.henrique.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	public ResponseEntity<?> listar() {

		return ResponseEntity.ok().body(restauranteRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {

		if (restauranteRepository.findById(id).isPresent()) {
			return ResponseEntity.ok(restauranteRepository.findById(id).get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {

		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(cadastroRestaurante.salvar(restaurante));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable("restauranteId") Long id, @RequestBody Restaurante restaurante) {

		try {
			
			if (restauranteRepository.findById(id).isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(cadastroRestaurante.atualizar(restaurante, id));
			}
			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

}
