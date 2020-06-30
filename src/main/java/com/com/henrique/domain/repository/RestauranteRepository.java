package com.com.henrique.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.com.henrique.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {


    //@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultaPorNome(String nome,@Param("id") Long cozinha);

    // Usage example of spring data
    //List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);

}
