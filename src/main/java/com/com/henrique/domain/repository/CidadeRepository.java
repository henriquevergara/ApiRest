package com.com.henrique.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.com.henrique.domain.model.Cidade;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {

}
