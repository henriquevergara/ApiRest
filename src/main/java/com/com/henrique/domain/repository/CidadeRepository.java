package com.com.henrique.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.com.henrique.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
