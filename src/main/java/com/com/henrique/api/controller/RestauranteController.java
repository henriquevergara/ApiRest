package com.com.henrique.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import com.com.henrique.domain.exception.EntidadeNaoEncontradaException;
import com.com.henrique.domain.model.Restaurante;
import com.com.henrique.domain.repository.RestauranteRepository;
import com.com.henrique.domain.service.CadastroRestauranteService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){

		if (restauranteRepository.findById(restauranteId).isPresent()){
			Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).get();
			merge(campos,restauranteAtual);
			return atualizar(restauranteId, restauranteAtual);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {

		try {
			cadastroRestaurante.excluir(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}

	}

	@GetMapping("/por-nome")
	public List<Restaurante> buscaNomeId(String nome, BigDecimal freteInicial, BigDecimal freteFinal){
		return restauranteRepository.findCriteriaQuery(nome,freteInicial,freteFinal);
	}

	private void merge(@RequestBody Map<String, Object> campos, Restaurante restauranteDestino) {

		ObjectMapper objectMapper = new ObjectMapper();

		Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);

		campos.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade); // utilizando field da biblioteca reflect para pegar as mudanças em tempo de execução e atualizar o recurso;
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

			ReflectionUtils.setField(field,restauranteDestino, novoValor);

		});
	}
}
