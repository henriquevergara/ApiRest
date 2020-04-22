package com.com.henrique.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }) // atributo utilizado para setar os content negotiation da requisicao, endpoint retorna apenas em xml e json.
	public ResponseEntity<List<Cozinha>> listar(){
		//return ResponseEntity.status(HttpStatus.OK).body(cozinhaRepository.findAll());
		return ResponseEntity.ok(cozinhaRepository.findAll());
	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		return ResponseEntity.ok(cozinhaRepository.findById(id).get());
	}
	
	
}
