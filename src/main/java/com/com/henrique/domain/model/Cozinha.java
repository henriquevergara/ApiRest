package com.com.henrique.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@JsonRootName used to indicate name to use for XML root-level wrapping
@Entity
public class Cozinha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @JsonProperty("titulo") alterar nome do atributo no JSON
	// @JsonIgnore usado para ocultar atributo do retorno
	@Column(nullable = false)
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
