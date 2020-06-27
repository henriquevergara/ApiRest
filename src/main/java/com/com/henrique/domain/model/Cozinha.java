package com.com.henrique.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@JsonRootName used to indicate name to use for XML root-level wrapping
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {


	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @JsonProperty("titulo") alterar nome do atributo no JSON
	// @JsonIgnore usado para ocultar atributo do retorno
	@Column(nullable = false)
	private String nome;

}
