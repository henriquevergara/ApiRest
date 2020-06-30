package com.com.henrique.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

	//Quando serializar a Cozinha, a propriedade restaurantes dela não precisa exibir a cozinha.
	@OneToMany(mappedBy = "cozinha")
	List<Restaurante> restaurantes = new ArrayList<>();

}
