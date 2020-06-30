package com.com.henrique.domain.repository;

import com.com.henrique.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findByNome(String nome);

    List<Cozinha> findByNomeContaining(String nome);

}
