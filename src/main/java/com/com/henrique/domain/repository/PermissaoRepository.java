package com.com.henrique.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.com.henrique.domain.model.Permissao;

public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {

}
