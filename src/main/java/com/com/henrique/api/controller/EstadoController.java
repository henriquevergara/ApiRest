package com.com.henrique.api.controller;

import java.util.List;

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
import com.com.henrique.domain.model.Estado;
import com.com.henrique.domain.repository.EstadoRepository;
import com.com.henrique.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService estadoService;
	
	@GetMapping
	public List<Estado> listar(){
		
		return estadoRepository.findAll();
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id){
		if (estadoRepository.findById(id).isPresent()) {
			
			return ResponseEntity.ok(estadoRepository.findById(id).get());
			
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Estado estado){
		
		try {
			Estado estadoAtual = estadoService.atualizar(id, estado);
			return ResponseEntity.ok().body(estadoAtual);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Estado estado){

			return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.salvar(estado));
	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		try {
			estadoService.excluir(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	
}
