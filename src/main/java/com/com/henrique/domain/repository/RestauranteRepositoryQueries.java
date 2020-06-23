package com.com.henrique.domain.repository;

import com.com.henrique.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findComJPQL(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
